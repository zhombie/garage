package kz.garage.location.core.provider

import android.location.LocationManager
import android.os.Build

enum class LocationProvider constructor(val value: String) {
    FUSED(
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            LocationManager.FUSED_PROVIDER
        } else {
            "fused"
        }
    ),
    GPS(LocationManager.GPS_PROVIDER),
    NETWORK(LocationManager.NETWORK_PROVIDER),
    PASSIVE(LocationManager.PASSIVE_PROVIDER);

    companion object {
        fun from(value: String?): LocationProvider? =
            when (value) {
                FUSED.value -> FUSED
                GPS.value -> GPS
                NETWORK.value -> NETWORK
                PASSIVE.value -> PASSIVE
                else -> null
            }
    }

    fun get(): String = value

    override fun toString(): String = value
}