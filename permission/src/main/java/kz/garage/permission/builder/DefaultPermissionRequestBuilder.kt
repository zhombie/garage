package kz.garage.permission.builder

import android.app.Activity
import kz.garage.permission.request.DefaultPermissionRequest
import kz.garage.permission.request.PermissionRequest
import kz.garage.permission.request.handler.factory.RequestHandlerFactory

class DefaultPermissionRequestBuilder internal constructor(
    private val activity: Activity
) : AbstractPermissionRequestBuilder() {

    override fun createRequest(
        permissions: Array<String>,
        factory: RequestHandlerFactory
    ): PermissionRequest {
        val handler = factory.provideHandler()
        return DefaultPermissionRequest(activity, permissions, handler)
    }

}