package kz.garage.window

import android.app.Activity
import android.util.Size
import androidx.window.layout.WindowMetricsCalculator

fun Activity.computeCurrentWindowSize(): Size {
    val windowMetrics = WindowMetricsCalculator.getOrCreate()
        .computeCurrentWindowMetrics(this)
    return Size(windowMetrics.bounds.width(), windowMetrics.bounds.height())
}

fun Activity.computeMaximumWindowSize(): Size {
    val windowMetrics = WindowMetricsCalculator.getOrCreate()
        .computeMaximumWindowMetrics(this)
    return Size(windowMetrics.bounds.width(), windowMetrics.bounds.height())
}