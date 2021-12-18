package kz.garage.popup.tooltip

import android.view.View

inline fun View.setupTooltip(
    builder: TooltipBuilder.() -> Unit
): TooltipBuilder {
    return Tooltip.on(this)
        .apply(builder)
}