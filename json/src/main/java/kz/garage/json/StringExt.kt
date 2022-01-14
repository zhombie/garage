package kz.garage.json

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * Usage:
 * val json = "{\"key\": \"value\"}".jsonObject  // {"key": "value"}
 * val jsonAgain = json?.toString() // "{"key": "value"}"
 * val stringFromJson = json?.getString("key") // "value"
 */
inline val String.asJSONObject: JSONObject?
    get() = try {
        JSONObject(this)
    } catch (e: JSONException) {
        null
    }

inline val String.asJSONArray: JSONArray?
    get() = try {
        JSONArray(this)
    } catch (e: JSONException) {
        null
    }