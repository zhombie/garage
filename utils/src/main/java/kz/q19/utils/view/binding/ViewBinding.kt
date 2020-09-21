package kz.q19.utils.view.binding

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

fun <T> lazyUnsynchronized(initializer: () -> T): Lazy<T> =
    lazy(LazyThreadSafetyMode.NONE, initializer)

fun <T : View> Activity.bind(@IdRes res: Int): Lazy<T> =
    lazyUnsynchronized { findViewById<T>(res) }

fun <T : View> View.bind(@IdRes res: Int): Lazy<T> =
    lazyUnsynchronized { findViewById<T>(res) }

fun <T : View> RecyclerView.ViewHolder.bind(@IdRes idRes: Int): Lazy<T> =
    lazyUnsynchronized { itemView.findViewById<T>(idRes) }