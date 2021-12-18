package kz.garage.permission.request

import kz.garage.permission.request.status.PermissionStatus

// Inspired by: https://github.com/fondesa/kpermissions
interface PermissionRequest {
    fun setEventListener(listener: Listener?)

    fun checkStatus(): List<PermissionStatus>

    fun send()

    fun interface Listener {
        fun onPermissionsResult(result: List<PermissionStatus>)
    }
}