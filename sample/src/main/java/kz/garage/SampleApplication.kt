package kz.garage

import kz.garage.chat.ui.imageloader.ChatUiImageLoader
import kz.garage.locale.LocaleManager
import kz.garage.locale.base.LocaleManagerBaseApplication
import kz.garage.samples.chat.coil.CoilImageLoader
import java.util.*

class SampleApplication : LocaleManagerBaseApplication(), ChatUiImageLoader.Factory {

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

    override fun getImageLoader(): ChatUiImageLoader {
        return CoilImageLoader(this, false)
    }

}