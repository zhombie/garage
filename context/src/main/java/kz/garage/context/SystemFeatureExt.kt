package kz.garage.context

import android.content.Context
import android.content.pm.PackageManager

fun Context.hasMicrophone(): Boolean =
    packageManager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE)
