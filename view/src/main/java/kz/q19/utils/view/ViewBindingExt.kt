package kz.q19.utils.view

import android.view.View
import androidx.annotation.IdRes

fun <T> lazyUnsynchronized(initializer: () -> T): Lazy<T> =
    lazy(LazyThreadSafetyMode.NONE, initializer)

fun <T : View> View.bind(@IdRes id: Int): Lazy<T> =
    lazyUnsynchronized { requireNotNull(findViewById(id)) }
