package kz.q19.utils.location.core

import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import java.lang.ref.WeakReference

abstract class LocationListenerWrapper constructor(
    private val locationListenerReference: WeakReference<LocationListener>
) : LocationListener {

    constructor(locationListener: LocationListener) : this(WeakReference(locationListener))

    override fun onLocationChanged(location: Location) {
        locationListenerReference.get()?.onLocationChanged(location)
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        @Suppress("DEPRECATION")
        locationListenerReference.get()?.onStatusChanged(provider, status, extras)
    }

    override fun onProviderEnabled(provider: String) {
        locationListenerReference.get()?.onProviderEnabled(provider)
    }

    override fun onProviderDisabled(provider: String) {
        locationListenerReference.get()?.onProviderDisabled(provider)
    }

}