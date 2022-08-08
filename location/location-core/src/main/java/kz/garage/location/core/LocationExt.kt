package kz.garage.location.core

import android.location.Location
import android.os.Build

@Suppress("DEPRECATION")
fun Location.isMocked(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        isMock
    } else {
        isFromMockProvider
    }
}