package kz.garage.kotlin

inline fun <reified T> simpleNameOf(): String =
    T::class.java.simpleName
