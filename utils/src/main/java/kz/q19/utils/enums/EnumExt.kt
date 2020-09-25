package kz.q19.utils.enums

inline fun <reified T : Enum<*>> findEnumBy(predicate: (T) -> Boolean): T? =
    T::class.java.enumConstants?.find(predicate)