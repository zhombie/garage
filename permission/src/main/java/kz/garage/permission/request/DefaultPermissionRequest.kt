package kz.garage.permission.request

import androidx.appcompat.app.AppCompatActivity
import kz.garage.permission.request.status.PermissionStatus
import kz.garage.permission.request.handler.RequestHandler
import kz.garage.permission.activity.checkPermissionsStatus

class DefaultPermissionRequest constructor(
    private val activity: AppCompatActivity,
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