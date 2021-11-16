package kz.zhombie.kotlin

fun Boolean.assertIsTrue(message: String = "Expected condition to be true") {
    if (!this) {
        throw AssertionError(message)
    }
}