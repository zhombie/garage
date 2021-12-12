package kz.garage.location.core

import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import java.lang.ref.WeakReference

abstract class AbstractLocationListener constructor(
    private val locationListenerReference: WeakReference<LocationListener>
) : LocationListener {

    constructor(locationListener: LocationListener) : this(WeakReference(locationListener))

    override fun onLocationChanged(location: Location) {
        super.onLocationChanged(location)
        locationListenerReference.get()?.onLocationChanged(location)
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        @Suppress("DEPRECATION")
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