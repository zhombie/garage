package kz.garage.permission.activity

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import kz.garage.permission.request.status.PermissionStatus

// [BEGIN] isPermissionGranted()

fun Activity.isPermissionGranted(permission: String): Boolean =
    ActivityCompat
        .checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

fun Activity.isPermissionsGranted(permissions: Array<String>): Boolean =
    permissions.all { isPermissionGranted(it) }

fun Activity.isPermissionsGranted(permissions: Collection<String>): Boolean =
    permissions.all { isPermissionGranted(it) }

@JvmName("isPermissionsGrantedArgs")
fun Activity.isPermissionsGranted(vararg permissions: String): Boolean =
    permissions.all { isPermissionGranted(it) }

// [END] isPermissionGranted()

// [BEGIN] shouldShowRationale()

fun Activity.shouldShowRationale(permission: String) =
    ActivityCompat.shouldShowRequestPermissionRationale(this, permission)

fun Activity.shouldShowRationale(permissions: Array<String>) =
    permissions.all { shouldShowRationale(it) }

fun Activity.shouldShowRationale(permissions: Collection<String>) =
    permissions.all { shouldShowRationale(it) }

@JvmName("shouldShowRationaleArgs")
fun Activity.shouldShowRationale(vararg permissions: String) =
    permissions.all { shouldShowRationale(it) }

// [END] shouldShowRationale()

// [BEGIN] checkPermissionsStatus()

fun Activity.checkPermissionsStatus(
    permissions: Array<String>
): List<PermissionStatus> =
    permissions.map { permission -> process(permission) }

fun Activity.checkPermissionsStatus(
    permissions: Collection<String>
): List<PermissionStatus> =
    permissions.map { permission -> process(permission) }

@JvmName("checkPermissionsStatusArgs")
fun Activity.checkPermissionsStatus(
    vararg permissions: String
): List<PermissionStatus> =
    permissions.map { permission -> process(permission) }

// [END] checkPermissionsStatus()


private fun Activity.process(permission: String): PermissionStatus {
    return if (isPermissionGranted(permission)) {
        PermissionStatus.Granted(permission)
    } else {
        if (shouldShowRationale(permission)) {
            PermissionStatus.Denied.ShouldShowRationale(permission)
        } else {
            PermissionStatus.RequestRequired(permission)
        }
    }
}
