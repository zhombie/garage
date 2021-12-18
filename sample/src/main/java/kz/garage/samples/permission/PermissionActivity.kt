package kz.garage.samples.permission

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kz.garage.R
import kz.garage.activity.toast.toast
import kz.garage.activity.view.bind
import kz.garage.kotlin.simpleNameOf
import kz.garage.permission.permissionRequest
import kz.garage.permission.request.status.isAllGranted
import kz.garage.permission.request.status.isAllPermanentlyDenied
import kz.garage.permission.send

class PermissionActivity : AppCompatActivity() {

    companion object {
        private val TAG = simpleNameOf<PermissionActivity>()
    }

    private val requestPermissionsButton by bind<MaterialButton>(R.id.requestPermissionsButton)
    private val launchSettingsButton by bind<MaterialButton>(R.id.launchSettingsButton)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        requestPermissionsButton.setOnClickListener {
            permissionRequest(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ).send { statuses ->
                Log.d(TAG, "statuses: ${statuses.joinToString()}")

                when {
                    statuses.isAllGranted() -> {
                        toast("All permissions granted")
                    }
                    statuses.isAllPermanentlyDenied() -> {
                        MaterialAlertDialogBuilder(this)
                            .setTitle("Permissions denied")
                            .setMessage("Please, grant permissions, because the app could not work without them")
                            .setPositiveButton("Settings") { _, _ ->
                                launchSettings()
                            }
                            .setNegativeButton(android.R.string.cancel, null)
                            .show()
                    }
                }
            }
        }

        launchSettingsButton.setOnClickListener {
            launchSettings()
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