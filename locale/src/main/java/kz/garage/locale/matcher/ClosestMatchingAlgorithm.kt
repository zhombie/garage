package kz.garage.locale.matcher

import kz.garage.locale.utils.LocaleMatcher
import java.util.*

/**
 * An algorithm that matches the Locales with most attributes in common
 */
class ClosestMatchingAlgorithm : MatchingAlgorithm {

    override fun findDefaultMatch(
        supportedLocales: List<Locale>,
        systemLocales: List<Locale>
    ): MatchingLocales? {
        var bestMatchingLocalePair: MatchingLocales? = null
        var languageAndCountryMatchingLocalePair: MatchingLocales? = null
        var languageMatchingLocalePair: MatchingLocales? = null
        for (systemLocale in systemLocales) {
            for (supportedLocale in supportedLocales) {
                val match = LocaleMatcher.match(systemLocale, supportedLocale)
                if (match == LocaleMatcher.MatchLevel.COMPLETE_MATCH) {
                    bestMatchingLocalePair = MatchingLocales(supportedLocale, systemLocale)
                    break
                } else if (match == LocaleMatcher.MatchLevel.LANGUAGE_AND_COUNTRY_MATCH && languageAndCountryMatchingLocalePair == null) {
                    languageAndCountryMatchingLocalePair = MatchingLocales(supportedLocale, systemLocale)
                } else if (match == LocaleMatcher.MatchLevel.LANGUAGE_MATCH && languageMatchingLocalePair == null) {
                    languageMatchingLocalePair = MatchingLocales(supportedLocale, systemLocale)
                }
            }
            if (bestMatchingLocalePair != null) break
        }
        return bestMatchingLocalePair ?: (languageAndCountryMatchingLocalePair ?: languageMatchingLocalePair)
    }

    override fun findMatch(supportedLocale: Locale, systemLocales: List<Locale>): MatchingLocales? {
        var bestMatchingLocalePair: MatchingLocales? = null
        var languageAndCountryMatchingLocalePair: MatchingLocales? = null
        var languageMatchingLocalePair: MatchingLocales? = null
        for (systemLocale in systemLocales) {
            val match = LocaleMatcher.match(systemLocale, supportedLocale)
            if (match == LocaleMatcher.MatchLevel.COMPLETE_MATCH) {
                bestMatchingLocalePair = MatchingLocales(supportedLocale, systemLocale)
                break
            } else if (match == LocaleMatcher.MatchLevel.LANGUAGE_AND_COUNTRY_MATCH && languageAndCountryMatchingLocalePair == null) {
                languageAndCountryMatchingLocalePair =
                    MatchingLocales(supportedLocale, systemLocale)
            } else if (match == LocaleMatcher.MatchLevel.LANGUAGE_MATCH && languageMatchingLocalePair == null) {
                languageMatchingLocalePair = MatchingLocales(supportedLocale, systemLocale)
            }
        }
        return bestMatchingLocalePair ?: (languageAndCountryMatchingLocalePair ?: languageMatchingLocalePair)
    }

}