package kz.garage.location.core

import android.location.LocationListener
import android.os.Bundle

interface LocationListenerCompat : LocationListener {
    @Deprecated("Deprecated in Java")
    override fun onStatusChanged(provider: String, status: Int, extras: Bundle?) {}
    override fun onProviderEnabled(provider: String) {}
    override fun onProviderDisabled(provider: String) {}
}
