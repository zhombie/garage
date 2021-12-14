package kz.garage.popup.tooltip

import android.graphics.Bitmap
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.Spanned
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.AnimRes
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

class TooltipBuilder constructor(private val tooltip: Tooltip) {

    fun show(duration: Long = 0): Tooltip {
        tooltip.overlay.addView(
            tooltip.tooltipView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return tooltip.show(duration)
    }

    fun drawableTop(@DrawableRes top: Int): TooltipBuilder {
        tooltip.getTextView()
            .setCompoundDrawablesWithIntrinsicBounds(0, top, 0, 0)
        return this
    }

    fun drawableBottom(@DrawableRes bottom: Int): TooltipBuilder {
        tooltip.getTextView()
            .setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, bottom)
        return this
    }

    fun text(text: String): TooltipBuilder {
        tooltip.getTextView().text = text
        return this
    }

    fun text(text: Spanned): TooltipBuilder {
        tooltip.getTextView().text = text
        return this
    }

    fun text(@StringRes text: Int): TooltipBuilder {
        tooltip.getTextView().setText(text)
        return this
    }

    fun textColor(@ColorInt textColor: Int): TooltipBuilder {
        tooltip.getTextView().setTextColor(textColor)
        return this
    }

    fun textTypeFace(typeface: Typeface): TooltipBuilder {
        tooltip.getTextView().typeface = typeface
        return this
    }

    fun textSize(unit: Int, textSize: Float): TooltipBuilder {
        tooltip.getTextView().setTextSize(unit, textSize)
        return this
    }

    fun textSize(textSize: Float): TooltipBuilder {
        tooltip.getTextView().textSize = textSize
        return this
    }

    fun textGravity(textGravity: Int): TooltipBuilder {
        tooltip.getTextView().gravity = textGravity
        return this
    }

    fun iconStart(@DrawableRes iconRes: Int): TooltipBuilder {
        val imageView = tooltip.getStartImageView()
        imageView.setImageResource(iconRes)
        imageView.visibility = View.VISIBLE
        return this
    }

    fun iconStart(icon: Drawable): TooltipBuilder {
        val imageView = tooltip.getStartImageView()
        imageView.setImageDrawable(icon)
        imageView.visibility = View.VISIBLE
        return this
    }

    fun iconStart(icon: Bitmap): TooltipBuilder {
        val imageView = tooltip.getStartImageView()
        imageView.setImageBitmap(icon)
        imageView.visibility = View.VISIBLE
        return this
    }

    fun iconStartMargin(left: Int, top: Int, right: Int, bottom: Int): TooltipBuilder {
        val imageView = tooltip.getStartImageView()
        val layoutParams = imageView.layoutParams
        if (layoutParams is FrameLayout.LayoutParams) {
            layoutParams.setMargins(left, top, right, bottom)
            imageView.layoutParams = layoutParams
        }
        return this
    }

    fun iconStartSize(h: Int, w: Int): TooltipBuilder {
        tooltip.getStartImageView().let {
            val layoutParams = it.layoutParams
            layoutParams.height = h
            layoutParams.width = w
            it.layoutParams = layoutParams
        }
        return this
    }

    fun lineHeight(lineSpacingExtra: Float, lineSpacingMultiplier: Float): TooltipBuilder {
        tooltip.getTextView().setLineSpacing(lineSpacingExtra, lineSpacingMultiplier)
        return this
    }

    fun iconEnd(@DrawableRes iconRes: Int): TooltipBuilder {
        val imageView = tooltip.getEndImageView()
        imageView.setImageResource(iconRes)
        imageView.visibility = View.VISIBLE
        return this
    }

    fun iconEnd(icon: Drawable): TooltipBuilder {
        tooltip.getEndImageView().let {
            it.setImageDrawable(icon)
            it.visibility = View.VISIBLE
        }
        return this
    }

    fun iconEnd(icon: Bitmap): TooltipBuilder {
        tooltip.getEndImageView().let {
            it.setImageBitmap(icon)
            it.visibility = View.VISIBLE
        }
        return this
    }

    fun iconEndMargin(left: Int, top: Int, right: Int, bottom: Int): TooltipBuilder {
        tooltip.getEndImageView().let {
            val layoutParams = it.layoutParams
            if (layoutParams is FrameLayout.LayoutParams) {
                layoutParams.setMargins(left, top, right, bottom)
                it.layoutParams = layoutParams
            }
        }
        return this
    }

    fun iconEndSize(h: Int, w: Int): TooltipBuilder {
        tooltip.getEndImageView().let {
            val layoutParams = it.layoutParams
            layoutParams.height = h
            layoutParams.width = w
            it.layoutParams = layoutParams
        }
        return this
    }

    fun color(@ColorInt color: Int): TooltipBuilder {
        tooltip.tooltipView.setColor(color)
        return this
    }

    fun padding(top: Int, right: Int, bottom: Int, left: Int): TooltipBuilder {
        tooltip.tooltipView.paddingTop = top
        tooltip.tooltipView.paddingBottom = bottom
        tooltip.tooltipView.paddingStart = left
        tooltip.tooltipView.paddingEnd = right
        return this
    }

    fun position(position: Position): TooltipBuilder {
        tooltip.tooltipView.position = position
        return this
    }

    fun corner(corner: Int): TooltipBuilder {
        tooltip.tooltipView.corner = corner
        return this
    }

    fun clickToHide(clickToHide: Boolean): TooltipBuilder {
        tooltip.clickToHide = clickToHide
        return this
    }

    fun distanceWithView(distance: Int): TooltipBuilder {
        tooltip.tooltipView.distanceWithView = distance
        return this
    }

    fun border(color: Int, width: Float): TooltipBuilder {
        val borderPaint = tooltip.tooltipView.borderPaint
        borderPaint.color = color
        borderPaint.strokeWidth = width
        return this
    }

    fun displayListener(listener: DisplayListener): TooltipBuilder {
        tooltip.displayListener = listener
        return this
    }

    fun tooltipClickListener(listener: TooltipClickListener): TooltipBuilder {
        tooltip.tooltipClickListener = listener
        return this
    }

    fun refViewClickListener(listener: TooltipClickListener): TooltipBuilder {
        tooltip.refViewClickListener = listener
        return this
    }

    fun overlay(@ColorInt color: Int, listener: TooltipClickListener? = null): TooltipBuilder {
        tooltip.overlay.setBackgroundColor(color)
        tooltip.initTargetClone()
        listener?.let { tooltip.setOverlayListener(listener) }
        return this
    }

    /**
     * Set [r] to 0 for disableShadow
     */
    fun shadow(r: Float, @ColorInt color: Int = 0xffaaaaaa.toInt()): TooltipBuilder {
        tooltip.tooltipView.setShadow(r, color)
        return this
    }

    fun shadowPadding(padding: Float): TooltipBuilder {
        tooltip.tooltipView.shadowPadding = padding
        return this
    }

    fun animation(@AnimRes animIn: Int, @AnimRes animOut: Int = animIn): TooltipBuilder {
        tooltip.animIn = animIn
        tooltip.animOut = animOut
        return this
    }

    fun minWidth(minWidth: Int): TooltipBuilder {
        tooltip.tooltipView.minWidth = minWidth
        return this
    }

    fun minHeight(minHeight: Int): TooltipBuilder {
        tooltip.tooltipView.minHeight = minHeight
        return this
    }

    fun arrowSize(h: Int, w: Int): TooltipBuilder {
        tooltip.tooltipView.arrowHeight = h.toFloat()
        tooltip.tooltipView.arrowWidth = w.toFloat()
        return this
    }

    fun borderMargin(margin: Int): TooltipBuilder {
        tooltip.tooltipView.leftMargin = margin
        return this
    }

}