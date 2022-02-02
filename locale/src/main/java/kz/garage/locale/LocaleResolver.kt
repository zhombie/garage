package kz.garage.locale

import kz.garage.locale.matcher.MatchingAlgorithm
import kz.garage.locale.matcher.MatchingLocales
import java.util.*

/**
 * Class that uses a [MatchingAlgorithm] and a [LocalePreference] to resolve a Locale to be set
 */
internal class LocaleResolver constructor(
    private val supportedLocales: List<Locale>,
    private val systemLocales: List<Locale>,
    private val matchingAlgorithm: MatchingAlgorithm,
    private val preference: LocalePreference
) {

    fun resolveDefault(): DefaultResolvedLocalePair {
        val matchingPair = matchingAlgorithm.findDefaultMatch(supportedLocales, systemLocales)
        return if (matchingPair != null) {
            DefaultResolvedLocalePair(matchingPair.supportedLocale, matchingPair.getPreferredLocale(preference))
        } else {
            DefaultResolvedLocalePair(supportedLocales[0], supportedLocales[0])
        }
    }

    @Throws(UnsupportedLocaleException::class)
    fun resolve(supportedLocale: Locale): Locale {
        if (!supportedLocales.contains(supportedLocale)) {
            throw UnsupportedLocaleException(message = "The Locale you are trying to load is not in the supported list provided on library initialization")
        }
        var matchingPair: MatchingLocales? = null
        if (preference == LocalePreference.PREFER_SYSTEM_LOCALE) {
            matchingPair = matchingAlgorithm.findMatch(supportedLocale, systemLocales)
        }
        return matchingPair?.getPreferredLocale(preference) ?: supportedLocale
    }

}