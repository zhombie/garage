package kz.garage.context.location.manager

import android.content.Context
import androidx.core.location.LocationManagerCompat
import kz.garage.context.system.service.locationManager

fun Context.isLocationEnabled(): Boolean =
    locationManager?.let { LocationManagerCompat.isLocationEnabled(it) } == true
