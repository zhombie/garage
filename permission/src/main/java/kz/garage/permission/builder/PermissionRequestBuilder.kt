package kz.garage.permission.builder

import kz.garage.permission.request.PermissionRequest
import kz.garage.permission.request.handler.factory.RequestHandlerFactory

interface PermissionRequestBuilder {
    fun setPermissions(permissions: Array<String>): PermissionRequestBuilder

    fun setRequestHandlerFactory(factory: RequestHandlerFactory): PermissionRequestBuilder

    fun build(): PermissionRequest
}