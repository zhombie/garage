package kz.garage.locale

import android.content.Context
import android.content.SharedPreferences
import java.util.*

/**
 * This class provides persistence
 */
internal class LocalePersistor constructor(context: Context) {

    companion object {
        private const val NAME_SHARED_PREFERENCES = "LocaleManager.LocalePersistence"

        private const val KEY_LANGUAGE = "language"
        private const val KEY_COUNTRY = "country"
        private const val KEY_VARIANT = "variant"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(NAME_SHARED_PREFERENCES, Context.MODE_PRIVATE)

    fun load(): Locale? {
        var locale: Locale? = null
        val language = sharedPreferences.getString(KEY_LANGUAGE, "")
        if (!language.isNullOrEmpty()) {
            locale = Locale(
                language,
                sharedPreferences.getString(KEY_COUNTRY, "") ?: "",
                sharedPreferences.getString(KEY_VARIANT, "") ?: ""
            )
        }
        return locale
    }

    fun save(locale: Locale) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_LANGUAGE, locale.language)
        editor.putString(KEY_COUNTRY, locale.country)
        editor.putString(KEY_VARIANT, locale.variant)
        editor.apply()
    }

    fun clear() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

}