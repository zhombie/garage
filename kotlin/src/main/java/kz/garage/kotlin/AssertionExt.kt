package kz.garage.kotlin

fun Boolean.assertIsTrue(message: String = "Expected condition to be true") {
    if (!this) {
        throw AssertionError(message)
    }
}