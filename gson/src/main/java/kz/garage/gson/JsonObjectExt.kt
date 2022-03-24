package kz.garage.gson

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject

fun JsonObject.getOrNull(memberName: String): JsonElement? =
    if (has(memberName)) get(memberName) else null

fun JsonObject.getBooleanOrNull(memberName: String): Boolean? =
    getOrNull(memberName)?.asBooleanOrNull()

fun JsonObject.getIntOrNull(memberName: String): Int? =
    getOrNull(memberName)?.asIntOrNull()

fun JsonObject.getDoubleOrNull(memberName: String): Double? =
    getOrNull(memberName)?.asDoubleOrNull()

fun JsonObject.getFloatOrNull(memberName: String): Float? =
    getOrNull(memberName)?.asFloatOrNull()

fun JsonObject.getLongOrNull(memberName: String): Long? =
    getOrNull(memberName)?.asLongOrNull()

fun JsonObject.getStringOrNull(memberName: String): String? =
    getOrNull(memberName)?.asStringOrNull()

fun JsonObject.getJsonObjectOrNull(memberName: String): JsonObject? =
    getOrNull(memberName)?.asJsonObjectOrNull()

fun JsonObject.getJsonArrayOrNull(memberName: String): JsonArray? =
    getOrNull(memberName)?.asJsonArrayOrNull()
