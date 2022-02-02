package kz.garage.locale.matcher

import java.util.*

/**
 * Matching algorithm that is used by the library
 */
interface MatchingAlgorithm {
    /**
     * Method that implements the algorithm to find two matching Locales between a list of supported and system Locales.
     *
     * @param supportedLocales a list of your app supported locales
     * @param systemLocales    a list of the configured  locales in system preferences
     * @return a [MatchingLocales] containing the pair of matching locales. If no match is found null is returned
     */
    fun findDefaultMatch(supportedLocales: List<Locale>, systemLocales: List<Locale>): MatchingLocales?

    /**
     * Method that implements the algorithm to find a matching Locale between the supported Locale and system Locales.
     *
     * @param supportedLocale one of your app supported locales
     * @param systemLocales   a list of the configured  locales in system preferences
     * @return a [MatchingLocales] containing the pair of matching locales. If no match is found null is returned
     */
    fun findMatch(supportedLocale: Locale, systemLocales: List<Locale>): MatchingLocales?
}