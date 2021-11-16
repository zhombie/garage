package kz.zhombie.drawable

import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.DrawableCompat

fun Drawable.withTint(@ColorInt tint: Int): Drawable {
    val drawable = DrawableCompat.wrap(this).mutate()
    DrawableCompat.setTint(drawable, tint)
    return drawable
}