package kz.garage.permission.request.status

sealed class PermissionStatus constructor(open val permission: String) {

    data class Granted constructor(
        override val permission: String
    ) : PermissionStatus(permission)

    sealed class Denied constructor(
        permission: String
    ) : PermissionStatus(permission) {

        data class Permanently constructor(
            override val permission: String
        ) : Denied(permission)

        data class ShouldShowRationale constructor(
            override val permission: String
        ) : Denied(permission)

    }

    data class RequestRequired constructor(
        override val permission: String
    ) : PermissionStatus(permission)

}