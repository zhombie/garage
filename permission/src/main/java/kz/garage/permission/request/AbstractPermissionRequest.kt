package kz.garage.permission.request

abstract class AbstractPermissionRequest : PermissionRequest {

    protected var eventListener: PermissionRequest.Listener? = null
        private set

    override fun setEventListener(listener: PermissionRequest.Listener?) {
        this.eventListener = listener
    }

}