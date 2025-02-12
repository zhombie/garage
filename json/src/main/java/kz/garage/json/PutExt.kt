package kz.garage.json

import org.json.JSONObject

fun JSONObject.putIfValueNotNull(key: String, any: Any?): JSONObject? {
    if (any is String) {
        if (any.isBlank()) return null
    } else {
        if (any == null) return null
    }
    return put(key, any)
}