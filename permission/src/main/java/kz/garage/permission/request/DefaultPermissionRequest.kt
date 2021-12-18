package kz.garage.permission.request

import android.app.Activity
import kz.garage.permission.activity.checkPermissionsStatus
import kz.garage.permission.request.handler.RequestHandler
import kz.garage.permission.request.status.PermissionStatus

class DefaultPermissionRequest constructor(
    private val activity: Activity,
    private val permissions: Array<String>,
    private val handler: RequestHandler
) : AbstractPermissionRequest(), RequestHandler.Listener {

    init {
        handler.attachListener(this)
    }

    override fun checkStatus(): List<PermissionStatus> =
        activity.checkPermissionsStatus(permissions)

    override fun send() {
        handler.handlePermissions(permissions)
    }

    override fun onPermissionsResult(result: List<PermissionStatus>) {
        eventListener?.onPermissionsResult(result)
    }

}