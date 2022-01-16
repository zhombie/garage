package kz.garage.location.core.provider

import android.location.LocationManager

fun LocationManager.isProviderEnabled(locationProvider: LocationProvider): Boolean =
    isProviderEnabled(locationProvider.get())
