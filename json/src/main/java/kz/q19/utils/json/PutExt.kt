package kz.q19.utils.json

import org.json.JSONObject

fun JSONObject.putIfValueNotNull(key: String, any: Any?): JSONObject? {
    if (any is String?) {
        if (any.isNullOrBlank()) return null
    } else {
        if (any == null) return null
    }
    return put(key, any)
}