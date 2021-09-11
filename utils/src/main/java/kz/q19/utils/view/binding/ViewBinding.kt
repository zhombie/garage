package kz.q19.utils.view.binding

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

fun <T> lazyUnsynchronized(initializer: () -> T): Lazy<T> =
    lazy(LazyThreadSafetyMode.NONE, initializer)

fun <T : View> Activity.bind(@IdRes id: Int): Lazy<T> =
    lazyUnsynchronized { findViewById(id) }

fun <T : View> View.bind(@IdRes id: Int): Lazy<T> =
    lazyUnsynchronized { findViewById(id) }

fun <T : View> RecyclerView.ViewHolder.bind(@IdRes id: Int): Lazy<T> =
    lazyUnsynchronized { itemView.findViewById(id) }