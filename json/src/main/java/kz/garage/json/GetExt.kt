package kz.garage.json

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * Usage
 * val firstName = jsonObject.getStringOrNull("first_name")
 * val lastName = jsonObject.getStringOrNull("last_name")
 */
fun JSONObject.getIntOrNull(name: String): Int? =
    try {
        if (isNull(name)) null else getInt(name)
    } catch (e: JSONException) {
        getStringOrNull(name)?.toIntOrNull()
    }

fun JSONObject.getDoubleOrNull(name: String): Double? =
    try {
        if (isNull(name)) null else getDouble(name)
    } catch (e: JSONException) {
        getStringOrNull(name)?.toDoubleOrNull()
    }

fun JSONObject.getLongOrNull(name: String): Long? =
    try {
        if (isNull(name)) null else getLong(name)
    } catch (e: JSONException) {
        try {
            getStringOrNull(name)?.toLongOrNull()
        } catch (e: IllegalArgumentException) {
            null
        }
    }

fun JSONObject.getStringOrNull(name: String): String? =
    try {
        if (isNull(name)) null else getString(name)
    } catch (e: JSONException) {
        null
    }

fun JSONObject.getBooleanOrNull(name: String): Boolean? =
    try {
        if (isNull(name)) null else getBoolean(name)
    } catch (e: JSONException) {
        null
    }

fun JSONObject.getJSONObjectOrNull(name: String): JSONObject? =
    try {
        if (isNull(name)) null else getJSONObject(name)
    } catch (e: JSONException) {
        null
    }

fun JSONObject.getJSONArrayOrNull(name: String): JSONArray? =
    try {
        if (isNull(name)) null else getJSONArray(name)
    } catch (e: JSONException) {
        null
    }

fun JSONObject.getJSONArrayOrEmpty(name: String): JSONArray =
    try {
        if (isNull(name)) JSONArray() else getJSONArray(name)
    } catch (e: JSONException) {
        JSONArray()
    }

inline fun <reified T> JSONObject.getAsList(name: String): List<T> =
    try {
        if (isNull(name)) {
            emptyList()
        } else {
            optJSONArray(name)?.toMutableList() ?: emptyList()
        }
    } catch (e: JSONException) {
        emptyList()
    }

inline fun <reified T> JSONObject.getAsMutableList(name: String): MutableList<T> =
    try {
        if (isNull(name)) {
            mutableListOf()
        } else {
            optJSONArray(name)?.toMutableList() ?: mutableListOf()
        }
    } catch (e: JSONException) {
        mutableListOf()
    }
