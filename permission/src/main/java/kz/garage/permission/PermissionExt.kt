package kz.garage.permission

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import kz.garage.permission.builder.DefaultPermissionRequestBuilder
import kz.garage.permission.builder.PermissionRequestBuilder
import kz.garage.permission.request.PermissionRequest
import kz.garage.permission.request.handler.factory.RequestHandlerFactoryImpl
import kz.garage.permission.request.status.PermissionStatus

// [BEGIN] permissionRequest()

fun FragmentActivity.permissionRequest(permissions: Array<String>) =
    permissionRequestBuilder(permissions).build()

fun FragmentActivity.permissionRequest(permissions: Collection<String>) =
    permissionRequestBuilder(permissions.toTypedArray()).build()

@JvmName("permissionRequestArgs")
fun FragmentActivity.permissionRequest(vararg permissions: String) =
    permissionRequestBuilder(arrayOf(*permissions)).build()

// [END] permissionRequest()

// [BEGIN] permissionRequestBuilder()

fun FragmentActivity.permissionRequestBuilder(
    permissions: Array<String>
): PermissionRequestBuilder {
    val factory = RequestHandlerFactoryImpl(fragmentManager = supportFragmentManager)
    return DefaultPermissionRequestBuilder(this)
        .setPermissions(permissions)
        .setRequestHandlerFactory(factory)
}

fun FragmentActivity.permissionRequestBuilder(
    permissions: Collection<String>
): PermissionRequestBuilder =
    permissionRequestBuilder(permissions.toTypedArray())

@JvmName("permissionRequestBuilderArgs")
fun FragmentActivity.permissionRequestBuilder(
    vararg permissions: String
): PermissionRequestBuilder =
    permissionRequestBuilder(arrayOf(*permissions))

// [END] permissionRequestBuilder()

// [BEGIN] permissionRequest()

fun Fragment.permissionRequest(permissions: Array<String>) =
    permissionRequestBuilder(permissions).build()

fun Fragment.permissionRequest(permissions: Collection<String>) =
    permissionRequestBuilder(permissions.toTypedArray()).build()

@JvmName("permissionRequestArgs")
fun Fragment.permissionRequest(vararg permissions: String) =
    permissionRequestBuilder(arrayOf(*permissions)).build()

// [END] permissionRequest()

// [BEGIN] permissionRequestBuilder()

@Throws(IllegalStateException::class)
fun Fragment.permissionRequestBuilder(
    permissions: Array<String>
): PermissionRequestBuilder =
    requireActivity().permissionRequestBuilder(permissions)

@Throws(IllegalStateException::class)
fun Fragment.permissionRequestBuilder(
    permissions: Collection<String>
): PermissionRequestBuilder =
    requireActivity().permissionRequestBuilder(permissions)

@Throws(IllegalStateException::class)
@JvmName("permissionRequestBuilderArgs")
fun Fragment.permissionRequestBuilder(
    vararg permissions: String
): PermissionRequestBuilder =
    requireActivity().permissionRequestBuilder(*permissions)

// [END] permissionRequestBuilder()


inline fun PermissionRequest.send(
    crossinline callback: (List<PermissionStatus>) -> Unit
) {
    setEventListener { result ->
        callback.invoke(result)
        setEventListener(null)
    }
    send()
}