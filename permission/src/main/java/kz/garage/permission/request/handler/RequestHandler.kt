package kz.garage.permission.request.handler

import kz.garage.permission.request.status.PermissionStatus

interface RequestHandler {
    fun attachListener(permissions: Array<String>, listener: Listener)

    fun handlePermissions(permissions: Array<String>)

    fun interface Listener {
        fun onPermissionsResult(result: List<PermissionStatus>)
    }
}