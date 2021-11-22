package kz.q19.utils.location.core

import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import java.lang.ref.WeakReference

abstract class LocationCallbackWrapper constructor(
    private val locationCallbackReference: WeakReference<LocationCallback>
) : LocationCallback() {

    constructor(locationCallback: LocationCallback) : this(WeakReference(locationCallback))

    override fun onLocationResult(locationResult: LocationResult?) {
        if (locationResult == null) return
        locationCallbackReference.get()?.onLocationResult(locationResult)
    }

    override fun onLocationAvailability(locationAvailability: LocationAvailability?) {
        if (locationAvailability == null) return
        locationCallbackReference.get()?.onLocationAvailability(locationAvailability)
    }

}