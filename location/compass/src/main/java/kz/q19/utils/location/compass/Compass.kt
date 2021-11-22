package kz.q19.utils.location.compass

import android.content.Context
import android.hardware.Sensor
import android.hardware.Sensor.*
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.util.Log
import android.view.Display
import android.view.Surface.*
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.lang.ref.WeakReference
import kotlin.math.abs

class Compass private constructor(
    private val contextReference: WeakReference<Context>
) : SensorEventListener {

    companion object {
        private val TAG = Compass::class.java.simpleName

        private const val ROTATION_VECTOR_SMOOTHING_FACTOR = 1F
        private const val GEOMAGNETIC_SMOOTHING_FACTOR = 1F
        private const val GRAVITY_SMOOTHING_FACTOR = 0.3F

        fun newInstance(context: Context): Compass? {
            val compass = Compass(WeakReference(context))
            return if (compass.hasRequiredSensors()) {
                compass
            } else {
                null
            }
        }
    }

    private val context: Context?
        get() = contextReference.get()

    private val sensorManager: SensorManager?
    private val rotationVectorSensor: Sensor?
    private val magneticFieldSensor: Sensor?
    private val accelerometerSensor: Sensor?

    private var useRotationVectorSensor = false

    private var rotationVector = FloatArray(5)
    private var geomagnetic = FloatArray(3)
    private var gravity = FloatArray(3)

    private var azimuthSensibility: Float = 0F
    private var lastAzimuthDegrees: Float = 0F

    private val angle: MutableLiveData<Float> = MutableLiveData()
    fun getAngle(): LiveData<Float> = angle

    init {
        val context = context ?: throw IllegalStateException("Context is null!")
        sensorManager = ContextCompat.getSystemService(context, SensorManager::class.java)
        rotationVectorSensor = sensorManager?.getDefaultSensor(TYPE_ROTATION_VECTOR)
        magneticFieldSensor = sensorManager?.getDefaultSensor(TYPE_MAGNETIC_FIELD)
        accelerometerSensor = sensorManager?.getDefaultSensor(TYPE_ACCELEROMETER)
    }

    // Check that the device has the required sensors
    private fun hasRequiredSensors(): Boolean {
        return if (rotationVectorSensor != null) {
            Log.d(TAG, "Sensor.TYPE_ROTATION_VECTOR found")
            true
        } else if (magneticFieldSensor != null && accelerometerSensor != null) {
            Log.d(TAG, "Sensor.TYPE_MAGNETIC_FIELD and Sensor.TYPE_ACCELEROMETER found")
            true
        } else {
            Log.d(TAG, "The device does not have the required sensors")
            false
        }
    }

    fun start(azimuthSensibility: Float = 1F) {
        this.azimuthSensibility = azimuthSensibility
        if (rotationVectorSensor != null) {
            sensorManager?.registerListener(
                this,
                rotationVectorSensor,
                SensorManager.SENSOR_DELAY_UI
            )
        }
        if (magneticFieldSensor != null) {
            sensorManager?.registerListener(
                this,
                magneticFieldSensor,
                SensorManager.SENSOR_DELAY_UI
            )
        }
        if (accelerometerSensor != null) {
            sensorManager?.registerListener(
                this,
                accelerometerSensor,
                SensorManager.SENSOR_DELAY_UI
            )
        }
    }

    fun stop() {
        azimuthSensibility = 0f
        sensorManager?.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        makeCalculations(event ?: return)
    }

    private fun makeCalculations(event: SensorEvent) = synchronized(this) {
        // Get the orientation array with Sensor.TYPE_ROTATION_VECTOR if possible (more precise),
        // otherwise with Sensor.TYPE_MAGNETIC_FIELD and Sensor.TYPE_ACCELEROMETER combined
        val orientation = FloatArray(3)
        when {
            event.sensor.type == TYPE_ROTATION_VECTOR -> {
                // Only use rotation vector sensor if it is working on this device
                if (!useRotationVectorSensor) {
                    Log.d(TAG, "Using Sensor.TYPE_ROTATION_VECTOR (more precise compass data)")
                    useRotationVectorSensor = true
                }
                // Smooth values
                rotationVector = exponentialSmoothing(
                    event.values,
                    rotationVector,
                    ROTATION_VECTOR_SMOOTHING_FACTOR
                )
                // Calculate the rotation matrix
                val rotationMatrix = FloatArray(9)
                SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)
                // Calculate the orientation
                SensorManager.getOrientation(rotationMatrix, orientation)
            }
            !useRotationVectorSensor && (event.sensor.type == TYPE_MAGNETIC_FIELD || event.sensor.type == TYPE_ACCELEROMETER) -> {
                if (event.sensor.type == TYPE_MAGNETIC_FIELD) {
                    geomagnetic = exponentialSmoothing(
                        event.values,
                        geomagnetic,
                        GEOMAGNETIC_SMOOTHING_FACTOR
                    )
                }
                if (event.sensor.type == TYPE_ACCELEROMETER) {
                    gravity = exponentialSmoothing(event.values, gravity, GRAVITY_SMOOTHING_FACTOR)
                }
                // Calculate the rotation and inclination matrix
                val rotationMatrix = FloatArray(9)
                val inclinationMatrix = FloatArray(9)
                SensorManager.getRotationMatrix(
                    rotationMatrix,
                    inclinationMatrix,
                    gravity,
                    geomagnetic
                )
                // Calculate the orientation
                SensorManager.getOrientation(rotationMatrix, orientation)
            }
            else -> {
                return
            }
        }

        // Calculate azimuth, pitch and roll values from the orientation[] array
        // Correct values depending on the screen rotation
        val screenRotation = context?.getDisplayCompat()?.rotation
        var azimuthDegrees: Float = Math.toDegrees(orientation[0].toDouble()).toFloat()
        if (screenRotation == ROTATION_0) {
            val rollDegrees = Math.toDegrees(orientation[2].toDouble())
            if (rollDegrees >= 90 || rollDegrees <= -90) {
                azimuthDegrees += 180f
            }
        } else if (screenRotation == ROTATION_90) {
            azimuthDegrees += 90f
        } else if (screenRotation == ROTATION_180) {
            azimuthDegrees += 180f
            val rollDegrees = (-Math.toDegrees(orientation[2].toDouble()))
            if (rollDegrees >= 90 || rollDegrees <= -90) {
                azimuthDegrees += 180f
            }
        } else if (screenRotation == ROTATION_270) {
            azimuthDegrees += 270f
        }

        // Force azimuth value between 0° and 360°.
        azimuthDegrees = (azimuthDegrees + 360) % 360

        // Notify the compass listener if needed
        if (abs(azimuthDegrees - lastAzimuthDegrees) >= azimuthSensibility || lastAzimuthDegrees == 0f) {
            lastAzimuthDegrees = azimuthDegrees
            angle.postValue(azimuthDegrees)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    private fun exponentialSmoothing(
        newValue: FloatArray,
        lastValue: FloatArray?,
        alpha: Float
    ): FloatArray {
        val output = FloatArray(newValue.size)
        if (lastValue == null) {
            return newValue
        }
        for (i in newValue.indices) {
            output[i] = lastValue[i] + alpha * (newValue[i] - lastValue[i])
        }
        return output
    }

    private fun Context.getDisplayCompat(): Display? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            display
        } else {
            @Suppress("DEPRECATION")
            ContextCompat.getSystemService(this, WindowManager::class.java)?.defaultDisplay
        }
    }

}