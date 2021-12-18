package kz.garage.permission.activity

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kz.garage.permission.request.status.PermissionStatus

fun AppCompatActivity.isPermissionGranted(permission: String): Boolean =
    ActivityCompat
        .checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

@JvmName("isPermissionsGrantedArray")
fun AppCompatActivity.isPermissionsGranted(permissions: Array<String>): Boolean =
    isPermissionsGranted(*permissions)

@JvmName("isPermissionsGrantedArgs")
fun AppCompatActivity.isPermissionsGranted(vararg permissions: String): Boolean =
    permissions.all { isPermissionGranted(it) }

fun AppCompatActivity.shouldShowRationale(permission: String) =
    ActivityCompat.shouldShowRequestPermissionRationale(this, permission)

@JvmName("shouldShowRationaleArray")
fun AppCompatActivity.shouldShowRationale(permissions: Array<String>) =
    shouldShowRationale(*permissions)

@JvmName("shouldShowRationaleArgs")
fun AppCompatActivity.shouldShowRationale(vararg permissions: String) =
    permissions.all { shouldShowRationale(it) }

@JvmName("checkPermissionsStatusArray")
fun AppCompatActivity.checkPermissionsStatus(
    permissions: Array<String>
): List<PermissionStatus> = checkPermissionsStatus(*permissions)

@JvmName("checkPermissionsStatusArgs")
fun AppCompatActivity.checkPermissionsStatus(
    vararg permissions: String
): List<PermissionStatus> {
    return permissions.map { permission ->
        if (isPermissionGranted(permission)) {
            return@map PermissionStatus.Granted(permission)
        }
        if (shouldShowRationale(permission)) {
            PermissionStatus.Denied.ShouldShowRationale(permission)
        } else {
            PermissionStatus.RequestRequired(permission)
        }
    }
}
