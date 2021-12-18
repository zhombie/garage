package kz.garage.permission.builder

import kz.garage.permission.request.PermissionRequest
import kz.garage.permission.request.handler.factory.RequestHandlerFactory

abstract class AbstractPermissionRequestBuilder : PermissionRequestBuilder {
    private var permissions: Array<String>? = null

    private var factory: RequestHandlerFactory? = null

    override fun setPermissions(permissions: Array<String>): PermissionRequestBuilder {
        this.permissions = permissions
        return this
    }

    override fun setRequestHandlerFactory(factory: RequestHandlerFactory): PermissionRequestBuilder {
        this.factory = factory
        return this
    }

    override fun build(): PermissionRequest {
        val permissions = permissions
            ?: throw IllegalArgumentException("The permissions names are necessary")

        val factory = factory
            ?: throw IllegalArgumentException("A handler factory is necessary to request the permissions")

        return createRequest(permissions, factory)
    }

    protected abstract fun createRequest(
        permissions: Array<String>,
        factory: RequestHandlerFactory
    ): PermissionRequest
}