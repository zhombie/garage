package kz.garage.activity.view

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes

inline fun <reified T : View> Activity.bind(@IdRes id: Int): Lazy<T> =
    lazy(LazyThreadSafetyMode.NONE) { requireNotNull(findViewById(id)) }
