package kz.garage.permission.fragment

import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kz.garage.permission.request.status.PermissionStatus

fun Fragment.isPermissionGranted(permission: String): Boolean =
    ContextCompat
        .checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED

@JvmName("isPermissionsGrantedArray")
fun Fragment.isPermissionsGranted(permissions: Array<String>): Boolean =
    isPermissionsGranted(*permissions)

@JvmName("isPermissionsGrantedArgs")
fun Fragment.isPermissionsGranted(vararg permissions: String): Boolean =
    permissions.all { isPermissionGranted(it) }

fun Fragment.shouldShowRationale(permission: String) =
    shouldShowRequestPermissionRationale(permission)

@JvmName("shouldShowRationaleArray")
fun Fragment.shouldShowRationale(permissions: Array<String>) =
    permissions.all { shouldShowRationale(it) }

@JvmName("shouldShowRationaleArgs")
fun Fragment.shouldShowRationale(vararg permissions: String) =
    permissions.all { shouldShowRationale(it) }

@JvmName("checkPermissionsStatusArray")
fun Fragment.checkPermissionsStatus(
    permissions: Array<String>
): List<PermissionStatus> = checkPermissionsStatus(*permissions)

@JvmName("checkPermissionsStatusArgs")
fun Fragment.checkPermissionsStatus(
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