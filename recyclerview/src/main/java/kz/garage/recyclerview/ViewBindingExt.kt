package kz.garage.recyclerview

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import kz.garage.view.lazyUnsynchronized

fun <T : View> RecyclerView.ViewHolder.bind(@IdRes id: Int): Lazy<T> =
    lazyUnsynchronized { requireNotNull(itemView.findViewById(id)) }
