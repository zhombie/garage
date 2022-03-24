package kz.garage.chat.model

fun Entity.getBody(): String? {
    if (this is Message) return body
    if (this is Notification) return body
    return null
}