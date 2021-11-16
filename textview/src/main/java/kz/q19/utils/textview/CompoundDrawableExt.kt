package kz.q19.utils.textview

import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.annotation.DrawableRes

enum class CompoundDrawableAlignment {
    LEFT,
    TOP,
    RIGHT,
    BOTTOM
}


fun TextView.getCompoundDrawableOnLeft(): Drawable? =
    getCompoundDrawable(CompoundDrawableAlignment.LEFT)

fun TextView.getCompoundDrawableOnTop(): Drawable? =
    getCompoundDrawable(CompoundDrawableAlignment.TOP)

fun TextView.getCompoundDrawableOnRight(): Drawable? =
    getCompoundDrawable(CompoundDrawableAlignment.RIGHT)

fun TextView.getCompoundDrawableOnBottom(): Drawable? =
    getCompoundDrawable(CompoundDrawableAlignment.BOTTOM)

fun TextView.getCompoundDrawable(
    alignment: CompoundDrawableAlignment
): Drawable? {
    if (compoundDrawables.isNullOrEmpty()) return null
    val index = when (alignment) {
        CompoundDrawableAlignment.LEFT -> 0
        CompoundDrawableAlignment.TOP -> 1
        CompoundDrawableAlignment.RIGHT -> 2
        CompoundDrawableAlignment.BOTTOM -> 3
    }
    if (index >= compoundDrawables.size) return null
    return compoundDrawables[index]
}


fun TextView.showCompoundDrawableOnLeft(@DrawableRes drawableRes: Int, padding: Int? = null) =
    showCompoundDrawable(drawableRes, CompoundDrawableAlignment.LEFT, padding)

fun TextView.showCompoundDrawableOnTop(@DrawableRes drawableRes: Int, padding: Int? = null) =
    showCompoundDrawable(drawableRes, CompoundDrawableAlignment.TOP, padding)

fun TextView.showCompoundDrawableOnRight(@DrawableRes drawableRes: Int, padding: Int? = null) =
    showCompoundDrawable(drawableRes, CompoundDrawableAlignment.RIGHT, padding)

fun TextView.showCompoundDrawableOnBottom(@DrawableRes drawableRes: Int, padding: Int? = null) =
    showCompoundDrawable(drawableRes, CompoundDrawableAlignment.BOTTOM, padding)

fun TextView.showCompoundDrawable(
    @DrawableRes drawableRes: Int,
    alignment: CompoundDrawableAlignment,
    padding: Int? = null
) {
    when (alignment) {
        CompoundDrawableAlignment.LEFT ->
            setCompoundDrawablesWithIntrinsicBounds(drawableRes, 0, 0, 0)
        CompoundDrawableAlignment.TOP ->
            setCompoundDrawablesWithIntrinsicBounds(0, drawableRes, 0, 0)
        CompoundDrawableAlignment.RIGHT ->
            setCompoundDrawablesWithIntrinsicBounds(0, 0, drawableRes, 0)
        CompoundDrawableAlignment.BOTTOM ->
            setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, drawableRes)
    }
    if (padding != null) {
        compoundDrawablePadding = padding
    }
}


fun TextView.showCompoundDrawableOnLeft(drawable: Drawable?, padding: Int? = null) =
    showCompoundDrawable(drawable, CompoundDrawableAlignment.LEFT, padding)

fun TextView.showCompoundDrawableOnTop(drawable: Drawable?, padding: Int? = null) =
    showCompoundDrawable(drawable, CompoundDrawableAlignment.TOP, padding)

fun TextView.showCompoundDrawableOnRight(drawable: Drawable?, padding: Int? = null) =
    showCompoundDrawable(drawable, CompoundDrawableAlignment.RIGHT, padding)

fun TextView.showCompoundDrawableOnBottom(drawable: Drawable?, padding: Int? = null) =
    showCompoundDrawable(drawable, CompoundDrawableAlignment.BOTTOM, padding)

fun TextView.showCompoundDrawable(
    drawable: Drawable?,
    alignment: CompoundDrawableAlignment,
    padding: Int? = null
) {
    if (drawable == null) return
    when (alignment) {
        CompoundDrawableAlignment.LEFT ->
            setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
        CompoundDrawableAlignment.TOP ->
            setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null)
        CompoundDrawableAlignment.RIGHT ->
            setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
        CompoundDrawableAlignment.BOTTOM ->
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawable)
    }
    if (padding != null) {
        compoundDrawablePadding = padding
    }
}


fun TextView.removeCompoundDrawables() {
    setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
    if (compoundDrawablePadding > 0) {
        compoundDrawablePadding = 0
    }
}