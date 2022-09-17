package kz.garage

import kz.garage.image.load.ImageLoader
import kz.garage.image.load.ImageLoaderFactory
import kz.garage.image.load.coil.CoilImageLoader
import kz.garage.locale.LocaleManager
import kz.garage.locale.base.LocaleManagerBaseApplication
import java.util.*

class SampleApplication : LocaleManagerBaseApplication(), ImageLoaderFactory {

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

    override fun getImageLoader(): ImageLoader =
        CoilImageLoader(
            context = this,
            allowHardware = true,
            crossfade = false,
            isLoggingEnabled = true
        )

}