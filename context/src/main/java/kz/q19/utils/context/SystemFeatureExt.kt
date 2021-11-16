package kz.q19.utils.context

import android.content.Context
import android.content.pm.PackageManager

fun Context.hasMicrophone(): Boolean =
    packageManager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE)
