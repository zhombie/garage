package kz.q19.utils.recyclerview

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import kz.q19.utils.view.lazyUnsynchronized

fun <T : View> RecyclerView.ViewHolder.bind(@IdRes id: Int): Lazy<T> =
    lazyUnsynchronized { requireNotNull(itemView.findViewById(id)) }
