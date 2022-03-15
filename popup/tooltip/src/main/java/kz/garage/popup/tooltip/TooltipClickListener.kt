package kz.garage.popup.tooltip

import android.view.View

fun interface TooltipClickListener {
    fun onClick(view: View, tooltip: Tooltip)
}