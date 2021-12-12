package kz.garage.recyclerview.adapter.viewholder.view

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

fun <T : View> RecyclerView.ViewHolder.bind(@IdRes id: Int): Lazy<T> =
    lazy(LazyThreadSafetyMode.NONE) { requireNotNull(itemView.findViewById(id)) }
