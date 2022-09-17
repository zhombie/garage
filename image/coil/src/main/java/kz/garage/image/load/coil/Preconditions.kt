package kz.garage.image.load.coil

internal fun <T> checkNotNull(reference: T?): T {
    if (reference == null) throw NullPointerException()
    return reference
}