package kz.garage.samples.location.gms

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.material.textview.MaterialTextView
import kz.garage.R
import kz.garage.activity.permission.isPermissionsGranted
import kz.garage.activity.toast.toast
import kz.garage.activity.view.bind
import kz.garage.kotlin.simpleName
import kz.garage.kotlin.toMillis
import kz.garage.location.core.isMocked
import kz.garage.location.gms.createLocationCallback

@SuppressLint("MissingPermission")
class GMSActivity : AppCompatActivity() {

    companion object {
        private val TAG = simpleName()
    }

    private val locationPermissions by lazy(LazyThreadSafetyMode.NONE) {
        arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    private val requestLocationPermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.all { it.value }) {
                checkLocationSettings()
            } else {
                checkLocationSettings()
            }
        }

    private val resolveResolutionRequired =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                checkLocationSettings()
            } else {
                checkLocationSettings()
            }
        }

    private val locationRequest by lazy(LazyThreadSafetyMode.NONE) {
        LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setFastestInterval(5L.toMillis())
            .setInterval(10L.toMillis())
            .setMaxWaitTime(20L.toMillis())
    }

    private var cancellationTokenSource: CancellationTokenSource? = null

    private val locationCallback by lazy(LazyThreadSafetyMode.NONE) {
        createLocationCallback(
            onLocationResult = { locationResult ->
                Log.d(TAG, "onLocationResult() -> $locationResult")

                onLocationReceived(locationResult.lastLocation)
            },
            onLocationAvailability = { locationAvailability ->
                Log.d(TAG, "onLocationAvailability() -> $locationAvailability")

                onLocationStatusReceived(locationAvailability.isLocationAvailable)

                if (isPermissionsGranted(locationPermissions)) {
                    cancellationTokenSource = CancellationTokenSource()

                    fusedLocationProviderClient?.getCurrentLocation(
                        locationRequest.priority,
                        requireNotNull(cancellationTokenSource).token
                    )?.addOnCompleteListener(this) {
                        if (it.isSuccessful && it.isComplete) {
                            onLocationReceived(it.result)
                        }
                    }
                } else {
                    fusedLocationProviderClient?.lastLocation
                        ?.addOnCompleteListener(this) {
                            if (it.isSuccessful && it.isComplete) {
                                onLocationReceived(it.result)
                            }
                        }
                }
            }
        )
    }

    private var fusedLocationProviderClient: FusedLocationProviderClient? = null

    private val textView by bind<MaterialTextView>(R.id.textView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gms)

        val apiAvailability = GoogleApiAvailability.getInstance()

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        apiAvailability
            .checkApiAvailability(requireNotNull(fusedLocationProviderClient))
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful && task.isComplete) {
                    Log.d(TAG, "checkApiAvailability() -> [SUCCESS] ${task.result}")

                    checkLocationSettings()
                } else {
                    Log.d(TAG, "checkApiAvailability() -> [FAILED] ${task.exception}")

                    apiAvailability.makeGooglePlayServicesAvailable(this)
                        .addOnCompleteListener(this) {
                            if (it.isSuccessful && it.isComplete) {
                                toast("There is no Google API")
                            } else {
                                val status = apiAvailability.isGooglePlayServicesAvailable(this)
                                if (apiAvailability.isUserResolvableError(status)) {
                                    val dialog = apiAvailability.getErrorDialog(this, status, 666)
                                    if (dialog == null) {
                                        toast("There is no Google API")
                                    } else {
                                        dialog.show()
                                    }
                                } else {
                                    toast("There is no Google API")
                                }
                            }
                        }
                }
            }
    }

    private fun checkLocationSettings() {
        LocationServices.getSettingsClient(this)
            .checkLocationSettings(
                LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest)
                    // Reference: http://stackoverflow.com/questions/29824408/google-play-services-locationservices-api-new-option-never
                    .setAlwaysShow(true)
                    .build()
            )
            .addOnCompleteListener(this) {
                if (it.isSuccessful && it.isComplete) {
                    Log.d(TAG, "checkLocationSettings() -> [SUCCESS] ${it.result.locationSettingsStates}")
                    requestLocationUpdates()
                } else {
                    Log.d(TAG, "checkLocationSettings() -> [FAILED] ${it.exception}")
                    tryToResolveException(it.exception)
                }
            }
    }

    private fun requestLocationUpdates() {
        if (isPermissionsGranted(locationPermissions)) {
            fusedLocationProviderClient?.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )?.addOnCompleteListener(this) {
                if (it.isSuccessful && it.isComplete) {
                    Log.d(TAG, "requestLocationUpdates() -> [SUCCESS] $it.result")
                } else {
                    Log.d(TAG, "requestLocationUpdates() -> [FAILED] ${it.exception}")
                }
            }
        } else {
            requestLocationPermissions.launch(locationPermissions)
        }
    }

    private fun tryToResolveException(exception: Exception?): Boolean {
        Log.d(TAG, "tryToResolveException() -> $exception")
        if (exception is ApiException) {
            when (exception.statusCode) {
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                    if (exception is ResolvableApiException) {
                        try {
                            resolveResolutionRequired.launch(
                                IntentSenderRequest.Builder(exception.resolution)
                                    .build()
                            )
                            return true
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
        checkLocationSettings()
        return false
    }

    private fun onLocationStatusReceived(isAvailable: Boolean) {
        textView.text = "Location Status: $isAvailable"
    }

    private fun onLocationReceived(location: Location) {
        if (location.isMocked()) {
            Log.e(TAG, "onLocationReceived() -> mock: $location")
        }

        textView.text = location.toString()
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "onDestroy()")

        cancellationTokenSource?.cancel()
        cancellationTokenSource = null

        fusedLocationProviderClient?.removeLocationUpdates(locationCallback)
        fusedLocationProviderClient = null

        requestLocationPermissions.unregister()
    }

}