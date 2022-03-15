package kz.garage.permission.context

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

// [BEGIN] isPermissionGranted()

fun Context.isPermissionGranted(permission: String): Boolean =
    ContextCompat
        .checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

fun Context.isPermissionsGranted(permissions: Array<String>): Boolean =
    permissions.all { isPermissionGranted(it) }

fun Context.isPermissionsGranted(permissions: Collection<String>): Boolean =
    permissions.all { isPermissionGranted(it) }

@JvmName("isPermissionsGrantedArgs")
fun Context.isPermissionsGranted(vararg permissions: String): Boolean =
    permissions.all { isPermissionGranted(it) }

// [END] isPermissionGranted()