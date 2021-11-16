package kz.zhombie.activity

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes
import kz.zhombie.view.lazyUnsynchronized

fun <T : View> Activity.bind(@IdRes id: Int): Lazy<T> =
    lazyUnsynchronized { requireNotNull(findViewById(id)) }
