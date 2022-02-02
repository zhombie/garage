package kz.garage.locale.matcher

import kz.garage.locale.utils.LocaleMatcher
import java.util.*

/**
 * An algorithm that match the Locales the same language in common
 */
class LanguageMatchingAlgorithm : MatchingAlgorithm {

    companion object {
        private fun findMatchingLocale(localeToMatch: Locale, candidates: List<Locale>): Locale? {
            var matchingLocale: Locale? = null
            for (candidate in candidates) {
                val matchLevel = LocaleMatcher.match(localeToMatch, candidate)
                if (matchLevel != LocaleMatcher.MatchLevel.NO_MATCH) {
                    matchingLocale = candidate
                    break
                }
            }
            return matchingLocale
        }
    }

    override fun findDefaultMatch(
        supportedLocales: List<Locale>,
        systemLocales: List<Locale>
    ): MatchingLocales? {
        var matchingPair: MatchingLocales? = null
        for (systemLocale in systemLocales) {
            val matchingSupportedLocale = findMatchingLocale(systemLocale, supportedLocales)
            if (matchingSupportedLocale != null) {
                matchingPair = MatchingLocales(matchingSupportedLocale, systemLocale)
                break
            }
        }
        return matchingPair
    }

    override fun findMatch(supportedLocale: Locale, systemLocales: List<Locale>): MatchingLocales? {
        val matchingSystemLocale = findMatchingLocale(supportedLocale, systemLocales)
        return if (matchingSystemLocale != null) {
            MatchingLocales(supportedLocale, matchingSystemLocale)
        } else null
    }

}