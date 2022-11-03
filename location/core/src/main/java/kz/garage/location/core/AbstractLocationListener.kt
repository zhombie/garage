package kz.garage.location.core

import android.location.Location
import android.os.Bundle
import java.lang.ref.WeakReference

class AbstractLocationListener internal constructor(
    private val locationListenerReference: WeakReference<LocationListenerCompat>
) : LocationListenerCompat {

    constructor(locationListener: LocationListenerCompat) : this(WeakReference(locationListener))

    override fun onLocationChanged(location: Location) {
        locationListenerReference.get()?.onLocationChanged(location)
    }

    @Deprecated("Deprecated in Java")
    override fun onStatusChanged(provider: String, status: Int, extras: Bundle?) {
        super.onStatusChanged(provider, status, extras)
        locationListenerReference.get()?.onStatusChanged(provider, status, extras)
    }

    override fun onProviderEnabled(provider: String) {
        super.onProviderEnabled(provider)
        locationListenerReference.get()?.onProviderEnabled(provider)
    }

    override fun onProviderDisabled(provider: String) {
        super.onProviderDisabled(provider)
        locationListenerReference.get()?.onProviderDisabled(provider)
    }

}