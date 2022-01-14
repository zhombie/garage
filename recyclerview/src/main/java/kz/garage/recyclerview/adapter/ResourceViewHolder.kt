package kz.garage.recyclerview.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

abstract class ResourceViewHolder constructor(
    view: View
) : RecyclerView.ViewHolder(view) {

    protected val context: Context
        get() = itemView.context

    protected val resources: Resources
        get() = itemView.context.resources

    protected fun getString(@StringRes resId: Int): String =
        context.getString(resId)

    protected fun getString(
        @StringRes resId: Int,
        vararg formatArgs: Any?
    ): String =
        if (formatArgs.isNullOrEmpty()) {
            context.getString(resId)
        } else {
            context.getString(resId, *formatArgs)
        }

    protected fun getColorCompat(@ColorRes resId: Int): Int =
        ContextCompat.getColor(context, resId)

    protected fun getColorStateListCompat(@ColorRes resId: Int): ColorStateList? =
        ContextCompat.getColorStateList(context, resId)

}