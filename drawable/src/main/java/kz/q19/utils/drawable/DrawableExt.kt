package kz.q19.utils.drawable

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.DrawableCompat

fun Drawable.withTint(@ColorInt tint: Int): Drawable {
    val drawable = DrawableCompat.wrap(this).mutate()
    DrawableCompat.setTint(drawable, tint)
    return drawable
}

fun Drawable.withTintList(tint: ColorStateList): Drawable {
    val drawable = DrawableCompat.wrap(this).mutate()
    DrawableCompat.setTintList(drawable, tint)
    return drawable
}