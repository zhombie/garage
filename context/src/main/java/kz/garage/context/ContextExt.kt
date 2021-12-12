package kz.garage.context

import android.content.Context
import androidx.core.app.ActivityManagerCompat
import kz.garage.context.system.service.activityManager

fun Context.isLowRamDevice(): Boolean =
    activityManager?.let { ActivityManagerCompat.isLowRamDevice(it) } == true