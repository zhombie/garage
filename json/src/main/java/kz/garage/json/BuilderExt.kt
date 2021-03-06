package kz.garage.json

import org.json.JSONObject

inline fun jsonObject(lambda: JSONObject.() -> Unit): JSONObject {
    val jsonObject = JSONObject()
    try {
        jsonObject.apply(lambda)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return jsonObject
}
