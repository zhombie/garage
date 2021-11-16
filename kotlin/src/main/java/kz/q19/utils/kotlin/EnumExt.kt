package kz.q19.utils.kotlin

inline fun <reified T : Enum<*>> findEnumBy(predicate: (T) -> Boolean): T? =
    T::class.java.enumConstants?.find(predicate)