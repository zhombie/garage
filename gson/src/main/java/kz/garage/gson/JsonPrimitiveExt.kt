package kz.garage.gson

import com.google.gson.JsonPrimitive

fun JsonPrimitive.isBooleanOrNull(): JsonPrimitive? =
    if (isBoolean) this else null

fun JsonPrimitive.isNumberOrNull(): JsonPrimitive? =
    if (isNumber) this else null

fun JsonPrimitive.isStringOrNull(): JsonPrimitive? =
    if (isString) this else null


fun JsonPrimitive.asBooleanOrNull(): Boolean? = isBooleanOrNull()?.asBoolean

fun JsonPrimitive.asIntOrNull(): Int? = isNumberOrNull()?.asInt

fun JsonPrimitive.asDoubleOrNull(): Double? = isNumberOrNull()?.asDouble

fun JsonPrimitive.asFloatOrNull(): Float? = isNumberOrNull()?.asFloat

fun JsonPrimitive.asLongOrNull(): Long? = isNumberOrNull()?.asLong

fun JsonPrimitive.asStringOrNull(): String? = isStringOrNull()?.asString
