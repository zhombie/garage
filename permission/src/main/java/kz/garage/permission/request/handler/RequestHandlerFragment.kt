package kz.garage.permission.request.handler

import android.content.Context
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import kz.garage.permission.fragment.checkPermissionsStatus
import kz.garage.permission.fragment.isPermissionGranted
import kz.garage.permission.fragment.shouldShowRationale
import kz.garage.permission.request.status.PermissionStatus
import kz.garage.permission.request.status.isAllGranted

class RequestHandlerFragment : Fragment(), RequestHandler {

    companion object {
        private val TAG = RequestHandlerFragment::class.java.simpleName

        private const val KEY_PENDING_PERMISSIONS = "kz.garage.permission.pending_permissions"
    }

    private var handlePendingPermissions: (() -> Unit)? = null
    private var pendingPermissions: Array<out String>? = null

    private var listener: RequestHandler.Listener? = null

    private val requestPermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),
        ::onPermissionsResult
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)

        handlePendingPermissions?.invoke()
        handlePendingPermissions = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (pendingPermissions == null) {
            pendingPermissions = savedInstanceState?.getStringArray(KEY_PENDING_PERMISSIONS)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        if (!pendingPermissions.isNullOrEmpty()) {
            outState.putStringArray(KEY_PENDING_PERMISSIONS, pendingPermissions)
        }
    }

    override fun attachListener(listener: RequestHandler.Listener) {
        this.listener = listener
    }

    override fun handlePermissions(permissions: Array<String>) {
        if (isAdded) {
            handlePermissionsWhenAdded(permissions)
        } else {
            handlePendingPermissions = { handlePermissionsWhenAdded(permissions) }
        }
    }

    private fun handlePermissionsWhenAdded(permissions: Array<String>) {
        val currentStatuses = checkPermissionsStatus(permissions)
        if (currentStatuses.isAllGranted()) {
            dispatch(currentStatuses)
        } else {
            // The Fragment can process only one request at the same time
            if (pendingPermissions == null) {
                requestPermissions(permissions)
            }
        }
    }

    private fun requestPermissions(permissions: Array<String>) {
        pendingPermissions = permissions
        requestPermissions.launch(permissions)
    }

    private fun onPermissionsResult(result: Map<String, Boolean>) {
        val pendingPermissions = pendingPermissions
        this.pendingPermissions = null
        if (pendingPermissions == null) {
            dispatch(
                result.map {
                    val permission = it.key
                    when {
                        isPermissionGranted(permission) ->
                            PermissionStatus.Granted(permission)
                        shouldShowRationale(permission) ->
                            PermissionStatus.Denied.ShouldShowRationale(permission)
                        else ->
                            PermissionStatus.Denied.Permanently(permission)
                    }
                }
            )
        } else {
            dispatch(
                pendingPermissions.map { permission ->
                    when {
                        result.getOrElse(permission) { isPermissionGranted(permission) } ->
                            PermissionStatus.Granted(permission)
                        shouldShowRationale(permission) ->
                            PermissionStatus.Denied.ShouldShowRationale(permission)
                        else ->
                            PermissionStatus.Denied.Permanently(permission)
                    }
                }
            )
        }
    }

    private fun dispatch(statuses: List<PermissionStatus>) {
        listener?.onPermissionsResult(statuses)
    }

}