package kz.garage.recyclerview.adapter.viewholder

import android.view.View
import kz.garage.recyclerview.adapter.ResourceViewHolder

abstract class BaseViewHolder<T> constructor(
    view: View
) : ResourceViewHolder(view) {

    open fun onBind(item: T, position: Int) {}

    open fun onUnbind() {}

}