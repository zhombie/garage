package kz.garage

import kz.garage.locale.LocaleManager
import kz.garage.locale.base.LocaleManagerBaseApplication
import java.util.*

class SampleApplication : LocaleManagerBaseApplication() {

    override fun initializeLocaleManager() {
        LocaleManager.initialize(
            context = this,
            supportedLocales = listOf(
                Locale.ENGLISH,
                Locale("ru"),
                Locale("kk")
            )
        )
    }

}