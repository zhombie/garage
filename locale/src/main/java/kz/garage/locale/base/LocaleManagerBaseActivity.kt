package kz.garage.locale.base

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.LocaleManagerAppCompatDelegate
import kz.garage.locale.LocaleManager
import kz.garage.locale.utils.ActivityRecreationHelper
import java.util.*

/**
 * Base [android.app.Activity] class to inherit from with all needed configuration
 */
abstract class LocaleManagerBaseActivity : AppCompatActivity() {

    private var delegate: LocaleManagerAppCompatDelegate? = null

    override fun getDelegate(): AppCompatDelegate {
        if (delegate == null) {
            delegate = LocaleManagerAppCompatDelegate(super.getDelegate())
        }
        return requireNotNull(delegate)
    }

    override fun onResume() {
        super.onResume()
        ActivityRecreationHelper.onResume(this)
    }

    override fun onDestroy() {
        ActivityRecreationHelper.onDestroy(this)
        super.onDestroy()
    }

    protected fun getLocale(): Locale =
        LocaleManager.getLocale() ?: Locale.getDefault()

    protected fun setLocale(locale: Locale) {
        LocaleManager.setLocale(locale)
        onSetLocale(locale)
        ActivityRecreationHelper.recreate(this, true)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun onSetLocale(locale: Locale) {
    }

}