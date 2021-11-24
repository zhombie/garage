package kz.q19.utils.recyclerview

import android.content.Context
import android.content.res.Resources
import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder<T> constructor(
    view: View
) : RecyclerView.ViewHolder(view) {

    val context: Context
        get() = itemView.context

    val resources: Resources
        get() = itemView.context.resources

    open fun onBind(item: T, position: Int) {}

}