package kz.q19.utils.assert

/**
 * Helper method which throws an exception when an assertion has failed
 */
fun assertIsTrue(condition: Boolean) {
    if (!condition) {
        throw AssertionError("Expected condition to be true")
    }
}