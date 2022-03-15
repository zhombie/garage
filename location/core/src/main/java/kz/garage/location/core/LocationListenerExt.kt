package kz.garage.location.core

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.os.Bundle

@Suppress("unused")
inline fun Context.createLocationListener(
    crossinline onLocationChanged: (location: Location) -> Unit = {},
    crossinline onStatusChanged: (provider: String?, status: Int, extras: Bundle?) -> Unit = { _, _, _ -> },
    crossinline onProviderEnabled: (provider: String) -> Unit = {},
    crossinline onProviderDisabled: (location: String) -> Unit = {}
): AbstractLocationListener {
    return AbstractLocationListener(object : LocationListener {
        override fun onLocationChanged(location: Location) {
            onLocationChanged.invoke(location)
        }

        @Suppress("DEPRECATION")
        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            super.onStatusChanged(provider, status, extras)
            onStatusChanged.invoke(provider, status, extras)
        }

        override fun onProviderEnabled(provider: String) {
            super.onProviderEnabled(provider)
            onProviderEnabled.invoke(provider)
        }

        override fun onProviderDisabled(provider: String) {
            super.onProviderDisabled(provider)
            onProviderDisabled.invoke(provider)
        }
    })
}