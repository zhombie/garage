package kz.garage.popup.tooltip

import android.view.View
import kz.garage.popup.tooltip.Tooltip

fun interface TooltipClickListener {
    fun onClick(view: View, tooltip: Tooltip)
}