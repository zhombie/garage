package kz.q19.utils.kotlin

fun Boolean.assertIsTrue(message: String = "Expected condition to be true") {
    if (!this) {
        throw AssertionError(message)
    }
}