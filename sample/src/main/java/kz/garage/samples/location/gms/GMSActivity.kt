package kz.garage.samples.location.gms

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.material.textview.MaterialTextView
import kz.garage.R
import kz.garage.activity.bind
import kz.garage.activity.toast
import kz.garage.location.core.isMocked
import kz.garage.location.gms.AbstractLocationCallback
import java.util.concurrent.TimeUnit

class GMSActivity : AppCompatActivity() {

    companion object {
        private val TAG = GMSActivity::class.java.simpleName

        fun newIntent(context: Context): Intent =
            Intent(context, GMSActivity::class.java)
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
            .setFastestInterval(TimeUnit.SECONDS.toMillis(5))
            .setInterval(TimeUnit.SECONDS.toMillis(10))
            .setMaxWaitTime(TimeUnit.SECONDS.toMillis(20))
    }

    private var cancellationTokenSource: CancellationTokenSource? = null

    private val locationCallback by lazy(LazyThreadSafetyMode.NONE) {
        object : AbstractLocationCallback(object : LocationCallback() {

            @SuppressLint("MissingPermission")
            override fun onLocationAvailability(locationAvailability: LocationAvailability) {
                super.onLocationAvailability(locationAvailability)

                Log.d(TAG, "onLocationAvailability() -> $locationAvailability")

                if (locationPermissions.all {
                        ActivityCompat.checkSelfPermission(this@GMSActivity, it) == PackageManager.PERMISSION_GRANTED
                }) {
                    cancellationTokenSource = CancellationTokenSource()
                    fusedLocationProviderClient?.getCurrentLocation(
                        locationRequest.priority,
                        requireNotNull(cancellationTokenSource).token
                    )?.addOnCompleteListener {
                        if (it.isSuccessful) {
                            onLocationReceived(it.result)
                        }
                    }
                } else {
                    fusedLocationProviderClient?.lastLocation
                        ?.addOnCompleteListener {
                            if (it.isSuccessful) {
                                onLocationReceived(it.result)
                            }
                        }
                }
            }

            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)

                Log.d(TAG, "onLocationResult() -> $locationResult")

                onLocationReceived(locationResult.lastLocation)
            }

            private fun onLocationReceived(location: Location) {
                if (location.isMocked()) {
                    Log.e(TAG, "onLocationReceived() -> mock: $location")
                }

                textView.text = location.toString()
            }
        }) {}
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
            .addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(TAG, "checkApiAvailability() -> [SUCCESS] ${it.result}")
                checkLocationSettings()
            } else {
                Log.d(TAG, "checkApiAvailability() -> [FAILED] ${it.exception}")
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

    private fun checkLocationSettings() {
        LocationServices.getSettingsClient(this)
            .checkLocationSettings(
                LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest)
                    // Reference: http://stackoverflow.com/questions/29824408/google-play-services-locationservices-api-new-option-never
                    .setAlwaysShow(true)
                    .build()
            )
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d(TAG, "checkLocationSettings() -> [SUCCESS] ${it.result.locationSettingsStates}")
                    requestLocationUpdates()
                } else {
                    Log.d(TAG, "checkLocationSettings() -> [FAILED] ${it.exception}")
                    tryToResolveException(it.exception)
                }
            }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocationUpdates() {
        if (locationPermissions.all {
                ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }) {
            fusedLocationProviderClient?.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )?.addOnCompleteListener {
                if (it.isSuccessful) {
                    val result = it.result
                    Log.d(TAG, "requestLocationUpdates() -> [SUCCESS] $result")
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