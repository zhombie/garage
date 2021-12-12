package kz.garage.location.gms

import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import java.lang.ref.WeakReference

abstract class AbstractLocationCallback constructor(
    private val locationCallbackReference: WeakReference<LocationCallback>
) : LocationCallback() {

    constructor(locationCallback: LocationCallback) : this(WeakReference(locationCallback))

    override fun onLocationResult(locationResult: LocationResult) {
        super.onLocationResult(locationResult)
        locationCallbackReference.get()?.onLocationResult(locationResult)
    }

    override fun onLocationAvailability(locationAvailability: LocationAvailability) {
        super.onLocationAvailability(locationAvailability)
        locationCallbackReference.get()?.onLocationAvailability(locationAvailability)
    }

}