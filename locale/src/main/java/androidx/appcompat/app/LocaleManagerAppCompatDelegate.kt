package androidx.appcompat.app

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.AttributeSet
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.ActionMode
import androidx.appcompat.widget.Toolbar
import kz.garage.locale.LocaleManager

class LocaleManagerAppCompatDelegate constructor(
    private val delegate: AppCompatDelegate
) : AppCompatDelegate() {

    override fun attachBaseContext2(context: Context): Context {
        val baseContext2 = LocaleManager.configureBaseContext(context)
        return super.attachBaseContext2(baseContext2)
    }

    override fun getSupportActionBar(): ActionBar? {
        return delegate.supportActionBar
    }

    override fun setSupportActionBar(toolbar: Toolbar?) {
        delegate.setSupportActionBar(toolbar)
    }

    override fun getMenuInflater(): MenuInflater? {
        return delegate.menuInflater
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        delegate.onCreate(savedInstanceState)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        delegate.onPostCreate(savedInstanceState)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        delegate.onConfigurationChanged(newConfig)
    }

    override fun onStart() {
        delegate.onStart()
    }

    override fun onStop() {
        delegate.onStop()
    }

    override fun onPostResume() {
        delegate.onPostResume()
    }

    override fun <T : View?> findViewById(id: Int): T? {
        return delegate.findViewById(id)
    }

    override fun setContentView(v: View?) {
        delegate.setContentView(v)
    }

    override fun setContentView(resId: Int) {
        delegate.setContentView(resId)
    }

    override fun setContentView(v: View?, lp: ViewGroup.LayoutParams?) {
        delegate.setContentView(v, lp)
    }

    override fun addContentView(v: View?, lp: ViewGroup.LayoutParams?) {
        delegate.addContentView(v, lp)
    }

    override fun setTitle(title: CharSequence?) {
        delegate.setTitle(title)
    }

    override fun invalidateOptionsMenu() {
        delegate.invalidateOptionsMenu()
    }

    override fun onDestroy() {
        delegate.onDestroy()
    }

    override fun getDrawerToggleDelegate(): ActionBarDrawerToggle.Delegate? {
        return delegate.drawerToggleDelegate
    }

    override fun requestWindowFeature(featureId: Int): Boolean {
        return delegate.requestWindowFeature(featureId)
    }

    override fun hasWindowFeature(featureId: Int): Boolean {
        return delegate.hasWindowFeature(featureId)
    }

    override fun startSupportActionMode(callback: ActionMode.Callback): ActionMode? {
        return delegate.startSupportActionMode(callback)
    }

    override fun installViewFactory() {
        delegate.installViewFactory()
    }

    override fun createView(
        parent: View?,
        name: String?,
        context: Context,
        attrs: AttributeSet
    ): View {
        return delegate.createView(parent, name, context, attrs)
    }

    override fun setHandleNativeActionModesEnabled(enabled: Boolean) {
        delegate.isHandleNativeActionModesEnabled = enabled
    }

    override fun isHandleNativeActionModesEnabled(): Boolean {
        return delegate.isHandleNativeActionModesEnabled
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        delegate.onSaveInstanceState(outState)
    }

    override fun applyDayNight(): Boolean {
        return delegate.applyDayNight()
    }

    override fun setLocalNightMode(mode: Int) {
        delegate.localNightMode = mode
    }

}