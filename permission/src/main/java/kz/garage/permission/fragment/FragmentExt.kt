package kz.garage.permission.fragment

import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kz.garage.permission.request.status.PermissionStatus

// [BEGIN] isPermissionGranted()

fun Fragment.isPermissionGranted(permission: String): Boolean =
    ContextCompat
        .checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED

fun Fragment.isPermissionsGranted(permissions: Array<String>): Boolean =
    permissions.all { isPermissionGranted(it) }

fun Fragment.isPermissionsGranted(permissions: Collection<String>): Boolean =
    permissions.all { isPermissionGranted(it) }

@JvmName("isPermissionsGrantedArgs")
fun Fragment.isPermissionsGranted(vararg permissions: String): Boolean =
    permissions.all { isPermissionGranted(it) }

// [END] isPermissionGranted()

// [BEGIN] shouldShowRationale()

fun Fragment.shouldShowRationale(permission: String) =
    shouldShowRequestPermissionRationale(permission)

fun Fragment.shouldShowRationale(permissions: Array<String>) =
    permissions.all { shouldShowRationale(it) }

fun Fragment.shouldShowRationale(permissions: Collection<String>) =
    permissions.all { shouldShowRationale(it) }

@JvmName("shouldShowRationaleArgs")
fun Fragment.shouldShowRationale(vararg permissions: String) =
    permissions.all { shouldShowRationale(it) }

// [END] shouldShowRationale()

// [BEGIN] checkPermissionsStatus()

fun Fragment.checkPermissionsStatus(
    permissions: Array<String>
): List<PermissionStatus> =
    permissions.map { permission -> process(permission) }

fun Fragment.checkPermissionsStatus(
    permissions: Collection<String>
): List<PermissionStatus> =
    permissions.map { permission -> process(permission) }

@JvmName("checkPermissionsStatusArgs")
fun Fragment.checkPermissionsStatus(
    vararg permissions: String
): List<PermissionStatus> =
    permissions.map { permission -> process(permission) }

// [END] checkPermissionsStatus()


private fun Fragment.process(permission: String): PermissionStatus {
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