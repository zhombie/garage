package kz.garage.fragment.permission

import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.isPermissionGranted(permission: String): Boolean =
    ContextCompat
        .checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED

fun Fragment.isPermissionsGranted(permissions: Array<String>): Boolean =
    permissions.all { isPermissionGranted(it) }
