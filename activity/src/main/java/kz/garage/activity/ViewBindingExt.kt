package kz.garage.activity

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes

fun <T : View> Activity.bind(@IdRes id: Int): Lazy<T> =
    lazy(LazyThreadSafetyMode.NONE) { requireNotNull(findViewById(id)) }
