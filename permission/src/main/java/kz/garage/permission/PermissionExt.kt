package kz.garage.permission

import androidx.appcompat.app.AppCompatActivity
import kz.garage.permission.builder.DefaultPermissionRequestBuilder
import kz.garage.permission.request.PermissionRequest
import kz.garage.permission.request.handler.factory.RequestHandlerFactoryImpl
import kz.garage.permission.request.status.PermissionStatus

fun AppCompatActivity.permissionRequestBuilder(
    vararg permissions: String
): PermissionRequest {
    val factory = RequestHandlerFactoryImpl(fragmentManager = supportFragmentManager)
    return DefaultPermissionRequestBuilder(this)
        .setPermissions(arrayOf(*permissions))
        .setRequestHandlerFactory(factory)
        .build()
}

inline fun PermissionRequest.send(
    crossinline callback: (List<PermissionStatus>) -> Unit
) {
    setEventListener { result ->
        setEventListener(null)
        callback.invoke(result)
    }
    send()
}