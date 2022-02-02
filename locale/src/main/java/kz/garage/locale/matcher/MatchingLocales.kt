package kz.garage.locale.matcher

import kz.garage.locale.LocalePreference
import java.util.*

/**
 * This class represents a pair of matching locales between a supported and a system Locale
 */
class MatchingLocales constructor(
    val supportedLocale: Locale,
    val systemLocale: Locale
) {

    fun getPreferredLocale(preference: LocalePreference): Locale {
        return if (preference == LocalePreference.PREFER_SUPPORTED_LOCALE) supportedLocale
        else systemLocale
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is MatchingLocales) return false
        return supportedLocale == other.supportedLocale && systemLocale == other.systemLocale
    }

    override fun hashCode(): Int {
        var result = supportedLocale.hashCode()
        result = 31 * result + systemLocale.hashCode()
        return result
    }

    override fun toString(): String {
        return "$supportedLocale, $systemLocale"
    }

}