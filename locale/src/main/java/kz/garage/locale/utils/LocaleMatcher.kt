package kz.garage.locale.utils

import java.util.*

/**
 * Helper class useful to determine the level of matching of two Locales
 */
object LocaleMatcher {

    /**
     * Enum representing the level of matching of two Locales.
     */
    enum class MatchLevel {
        NO_MATCH, LANGUAGE_MATCH, LANGUAGE_AND_COUNTRY_MATCH, COMPLETE_MATCH
    }

    /**
     * Method to determine the level of matching of two Locales.
     * @param l1
     * @param l2
     * @return
     */
    fun match(l1: Locale, l2: Locale): MatchLevel {
        var matchLevel: MatchLevel = MatchLevel.NO_MATCH
        if (l1 == l2) {
            matchLevel = MatchLevel.COMPLETE_MATCH
        } else if (l1.language == l2.language && l1.country == l2.country) {
            return MatchLevel.LANGUAGE_AND_COUNTRY_MATCH
        } else if (l1.language == l2.language) {
            return MatchLevel.LANGUAGE_MATCH
        }
        return matchLevel
    }

}