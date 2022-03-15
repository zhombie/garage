package kz.garage.locale

import android.content.Context
import kz.garage.locale.matcher.LanguageMatchingAlgorithm
import kz.garage.locale.matcher.MatchingAlgorithm
import kz.garage.locale.utils.SystemLocaleRetriever.retrieve
import java.util.*

object LocaleManager {

    private var delegate: LocaleManagerDelegate? = null

    /**
     * Initialize the [LocaleManager], this method needs to be called before calling any other method.
     *
     *
     * If this method was never invoked before it sets a Locale from the supported list if a language match is found with the system Locales,
     * if no match is found the first Locale in the list will be set.
     *
     *
     * If this method was invoked before it will load a Locale previously set.
     *
     *
     * If this method is invoked when the library is already initialized the new settings will be applied from now on.
     *
     * @param context
     * @param supportedLocales  a list of your app supported Locales
     * @param matchingAlgorithm used to find a match between supported and system Locales
     * @param preference        used to indicate what Locale is preferred to load in case of a match
     * @throws IllegalStateException if the LocaleManager is already initialized
     */
    @JvmOverloads
    fun initialize(
        context: Context,
        supportedLocales: List<Locale>,
        matchingAlgorithm: MatchingAlgorithm = LanguageMatchingAlgorithm(),
        preference: LocalePreference = LocalePreference.PREFER_SUPPORTED_LOCALE
    ) {
        delegate = LocaleManagerDelegate(
            persistor = LocalePersistor(context = context),
            resolver = LocaleResolver(
                supportedLocales = supportedLocales,
                systemLocales = retrieve(),
                matchingAlgorithm = matchingAlgorithm,
                preference = preference
            ),
            appLocaleManager = AppLocaleManager(context)
        )
        delegate?.initialize()
    }

    private fun getDelegate(): LocaleManagerDelegate {
        return checkNotNull(delegate) {
            "LocaleManager is not initialized. Please first call LocaleManager.initialize"
        }
    }

    /**
     * Clears any Locale set and resolve and load a new default one.
     * This method can be useful if the app implements new supported Locales and it is needed to reload the default one in case there is a best match.
     */
    fun resetLocale() {
        getDelegate().resetLocale()
    }

    /**
     * Gets the supported Locale that has been used to set the app Locale.
     *
     * @return
     */
    fun getLocale(): Locale? {
        return getDelegate().locale
    }

    /**
     * Sets a new default app Locale that will be resolved from the one provided.
     *
     * @param supportedLocale a supported Locale that will be used to resolve the Locale to set.
     */
    fun setLocale(supportedLocale: Locale) {
        getDelegate().locale = supportedLocale
    }

    /**
     * This method should be used inside the [android.app.Activity.attachBaseContext].
     * The returned Context should be used as argument for the super method call.
     *
     * @param context
     * @return the resulting context that should be provided to the super method call.
     */
    fun configureBaseContext(context: Context): Context {
        return getDelegate().configureBaseContext(context)
    }

    /**
     * This method should be called from [android.app.Application.onConfigurationChanged]
     */
    fun onConfigurationChanged() {
        getDelegate().onConfigurationChanged()
    }

}