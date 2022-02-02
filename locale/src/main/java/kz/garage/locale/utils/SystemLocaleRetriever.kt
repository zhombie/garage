package kz.garage.locale.utils

import android.os.Build
import android.os.LocaleList
import androidx.annotation.RequiresApi
import java.util.*

/**
 * A class useful to retrieve the system configured Locales.
 */
object SystemLocaleRetriever {

    @JvmStatic
    fun retrieve(): List<Locale> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mapToListOfLocales(LocaleList.getDefault())
        } else {
            listOf(Locale.getDefault())
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private fun mapToListOfLocales(localeList: LocaleList): List<Locale> {
        val locales = ArrayList<Locale>()
        for (i in 0 until localeList.size()) {
            locales.add(localeList[i])
        }
        return locales
    }

}