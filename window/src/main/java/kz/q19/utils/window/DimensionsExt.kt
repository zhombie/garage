package kz.q19.utils.window

import android.content.res.Resources

fun Int.dp2Px(): Float = toFloat().dp2Px()

fun Float.dp2Px(): Float = this * Resources.getSystem().displayMetrics.density

fun Int.px2Dp(): Float = toFloat().px2Dp()

fun Float.px2Dp(): Float = this / Resources.getSystem().displayMetrics.density
