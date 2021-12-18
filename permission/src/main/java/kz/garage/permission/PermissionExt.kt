package kz.garage.permission

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import kz.garage.permission.builder.DefaultPermissionRequestBuilder
import kz.garage.permission.builder.PermissionRequestBuilder
import kz.garage.permission.request.PermissionRequest
import kz.garage.permission.request.handler.factory.RequestHandlerFactoryImpl
import kz.garage.permission.request.status.PermissionStatus

@JvmName("permissionRequestArray")
fun FragmentActivity.permissionRequest(permissions: Array<String>) =
    permissionRequestBuilder(permissions).build()

@JvmName("permissionRequestArgs")
fun FragmentActivity.permissionRequest(vararg permissions: String) =
    permissionRequestBuilder(arrayOf(*permissions)).build()

@JvmName("permissionRequestBuilderArray")
fun FragmentActivity.permissionRequestBuilder(
    permissions: Array<String>
): PermissionRequestBuilder {
    val factory = RequestHandlerFactoryImpl(fragmentManager = supportFragmentManager)
    return DefaultPermissionRequestBuilder(this)
        .setPermissions(permissions)
        .setRequestHandlerFactory(factory)
}

@JvmName("permissionRequestBuilderArgs")
fun FragmentActivity.permissionRequestBuilder(
    vararg permissions: String
): PermissionRequestBuilder =
    permissionRequestBuilder(arrayOf(*permissions))


@JvmName("permissionRequestArray")
fun Fragment.permissionRequest(permissions: Array<String>) =
    permissionRequestBuilder(permissions).build()

@JvmName("permissionRequestArgs")
fun Fragment.permissionRequest(vararg permissions: String) =
    permissionRequestBuilder(arrayOf(*permissions)).build()

@JvmName("permissionRequestBuilderArray")
fun Fragment.permissionRequestBuilder(
    permissions: Array<String>
): PermissionRequestBuilder =
    requireActivity().permissionRequestBuilder(permissions)

@JvmName("permissionRequestBuilderArgs")
fun Fragment.permissionRequestBuilder(
    vararg permissions: String
): PermissionRequestBuilder =
    requireActivity().permissionRequestBuilder(arrayOf(*permissions))


inline fun PermissionRequest.send(
    crossinline callback: (List<PermissionStatus>) -> Unit
) {
    setEventListener { result ->
        setEventListener(null)
        callback.invoke(result)
    }
    send()
}