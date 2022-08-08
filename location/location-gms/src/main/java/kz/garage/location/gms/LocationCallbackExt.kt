package kz.garage.location.gms

import android.content.Context
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult

@Suppress("unused")
inline fun Context.createLocationCallback(
    crossinline onLocationResult: (locationResult: LocationResult) -> Unit = {},
    crossinline onLocationAvailability: (locationAvailability: LocationAvailability) -> Unit = {}
): AbstractLocationCallback {
    return AbstractLocationCallback(object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            onLocationResult.invoke(locationResult)
        }

        override fun onLocationAvailability(locationAvailability: LocationAvailability) {
            super.onLocationAvailability(locationAvailability)
            onLocationAvailability.invoke(locationAvailability)
        }
    })
}