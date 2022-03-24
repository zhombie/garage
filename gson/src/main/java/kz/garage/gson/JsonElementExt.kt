package kz.garage.gson

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive

fun JsonElement.asJsonPrimitiveOrNull(): JsonPrimitive? =
    if (isJsonPrimitive) asJsonPrimitive else null

fun JsonElement.asJsonObjectOrNull(): JsonObject? =
    if (isJsonObject) asJsonObject else null

fun JsonElement.asJsonArrayOrNull(): JsonArray? =
    if (isJsonArray) asJsonArray else null


fun JsonElement.asBooleanOrNull(): Boolean? = asJsonPrimitiveOrNull()?.asBooleanOrNull()

fun JsonElement.asIntOrNull(): Int? = asJsonPrimitiveOrNull()?.asIntOrNull()

fun JsonElement.asDoubleOrNull(): Double? = asJsonPrimitiveOrNull()?.asDoubleOrNull()

fun JsonElement.asFloatOrNull(): Float? = asJsonPrimitiveOrNull()?.asFloatOrNull()

fun JsonElement.asLongOrNull(): Long? = asJsonPrimitiveOrNull()?.asLongOrNull()

fun JsonElement.asStringOrNull(): String? = asJsonPrimitiveOrNull()?.asStringOrNull()
