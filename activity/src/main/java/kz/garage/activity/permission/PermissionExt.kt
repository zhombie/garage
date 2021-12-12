package kz.garage.activity.permission

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

fun Activity.isPermissionGranted(permission: String): Boolean =
    ActivityCompat
        .checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

fun Activity.isPermissionsGranted(permissions: Array<String>): Boolean =
    permissions.all { isPermissionGranted(it) }

fun Activity.shouldShowPermissionRationale(permission: String) =
    ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
