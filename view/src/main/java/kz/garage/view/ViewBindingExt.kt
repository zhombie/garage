package kz.garage.view

import android.view.View
import androidx.annotation.IdRes

fun <T : View> View.bind(@IdRes id: Int): Lazy<T> =
    lazy(LazyThreadSafetyMode.NONE) { requireNotNull(findViewById(id)) }
