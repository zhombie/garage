package kz.garage.recyclerview

import android.content.Context
import android.content.res.Resources
import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder<T> constructor(
    view: View
) : RecyclerView.ViewHolder(view) {

    protected val context: Context
        get() = itemView.context

    protected val resources: Resources
        get() = itemView.context.resources

    open fun onBind(item: T, position: Int) {}

    open fun onUnbind() {}

}