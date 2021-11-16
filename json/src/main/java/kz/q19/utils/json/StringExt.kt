package kz.q19.utils.json

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


/**
 * Usage:
 * val json = "{\"key\": \"value\"}".jsonObject  // {"key": "value"}
 * val jsonAgain = json?.toString() // "{"key": "value"}"
 * val stringFromJson = json?.getString("key") // "value"
 */
val String.asJSONObject: JSONObject?
    get() = try {
        JSONObject(this)
    } catch (e: JSONException) {
        null
    }

val String.asJSONArray: JSONArray?
    get() = try {
        JSONArray(this)
    } catch (e: JSONException) {
        null
    }