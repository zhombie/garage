package kz.garage.locale

import java.util.*

/**
 * A class representing a pair of Locales resolved by the [LocaleResolver.resolveDefault]
 */
internal class DefaultResolvedLocalePair(
    val supportedLocale: Locale,
    val resolvedLocale: Locale
)