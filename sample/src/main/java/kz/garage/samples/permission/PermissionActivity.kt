package kz.garage.samples.permission

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kz.garage.R
import kz.garage.activity.toast.toast
import kz.garage.activity.view.bind
import kz.garage.kotlin.simpleNameOf
import kz.garage.permission.permissionRequestBuilder
import kz.garage.permission.request.PermissionRequest
import kz.garage.permission.request.status.PermissionStatus
import kz.garage.permission.request.status.isAllGranted
import kz.garage.permission.request.status.isAnyPermanentlyDenied

class PermissionActivity : AppCompatActivity(), PermissionRequest.Listener {

    companion object {
        private val TAG = simpleNameOf<PermissionActivity>()
    }

    private val requestPermissionsButton by bind<MaterialButton>(R.id.requestPermissionsButton)
    private val launchSettingsButton by bind<MaterialButton>(R.id.launchSettingsButton)

    private val permissionRequest by lazy {
        permissionRequestBuilder(
            Manifest.permission.CAMERA,
            Manifest.permission.SEND_SMS
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        permissionRequest.setEventListener(this)

        requestPermissionsButton.setOnClickListener {
            permissionRequest.send()
        }

        launchSettingsButton.setOnClickListener {
            launchSettings()
        }
    }

    /**
     * [PermissionRequest.Listener] implementation
     */

    override fun onPermissionsResult(result: List<PermissionStatus>) {
        when {
            result.isAllGranted() ->
                toast("All permissions granted")
            result.isAnyPermanentlyDenied() -> {
                val deniedPermissions = result.filterIsInstance<PermissionStatus.Denied.Permanently>()
                    .joinToString(", ") { it.permission }
                MaterialAlertDialogBuilder(this)
                    .setTitle("Permissions denied")
                    .setMessage("Please, grant permissions to ${deniedPermissions}, because the app could not work without them")
                    .setPositiveButton("Settings") { _, _ ->
                        launchSettings()
                    }
                    .setNegativeButton(android.R.string.cancel, null)
                    .show()
            }
            else ->
                toast("Something else")
        }
    }

    private fun launchSettings() {
        try {
            startActivity(createAppSettingsIntent())
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    private fun Context.createAppSettingsIntent(): Intent = Intent().apply {
        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        data = Uri.fromParts("package", packageName, null)
    }

}