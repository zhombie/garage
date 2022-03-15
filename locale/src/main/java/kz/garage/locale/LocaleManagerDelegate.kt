/*
 * Copyright (c)  2017  Francisco José Montiel Navarro.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kz.garage.locale

import android.content.Context
import java.util.*

/**
 * A delegate instance class that is used by the public LocaleManager class.
 */
internal class LocaleManagerDelegate constructor(
    private val persistor: LocalePersistor,
    private val resolver: LocaleResolver,
    private val appLocaleManager: AppLocaleManager
) {

    private var currentLocale: Locale = Locale.getDefault()

    fun initialize() {
        var persistedLocale = persistor.load()
        if (persistedLocale != null) {
            try {
                currentLocale = resolver.resolve(persistedLocale)
            } catch (e: UnsupportedLocaleException) {
                persistedLocale = null
            }
        }
        if (persistedLocale == null) {
            val defaultLocalePair = resolver.resolveDefault()
            currentLocale = defaultLocalePair.resolvedLocale
            persistor.save(defaultLocalePair.supportedLocale)
        }
        appLocaleManager.change(currentLocale)
    }

    fun resetLocale() {
        persistor.clear()
        initialize()
    }

    var locale: Locale?
        get() = persistor.load()
        set(value) {
            try {
                if (value == null) return
                currentLocale = resolver.resolve(value)
                persistor.save(value)
                appLocaleManager.change(currentLocale)
            } catch (e: UnsupportedLocaleException) {
                throw IllegalArgumentException(e)
            }
        }

    fun configureBaseContext(context: Context): Context {
        return appLocaleManager.configureBaseContext(context, currentLocale)
    }

    fun onConfigurationChanged() {
        appLocaleManager.change(currentLocale)
    }

}