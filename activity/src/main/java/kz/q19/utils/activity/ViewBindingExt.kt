package kz.q19.utils.activity

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes
import kz.q19.utils.view.lazyUnsynchronized

fun <T : View> Activity.bind(@IdRes id: Int): Lazy<T> =
    lazyUnsynchronized { requireNotNull(findViewById(id)) }
