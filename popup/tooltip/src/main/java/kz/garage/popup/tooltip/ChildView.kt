package kz.garage.popup.tooltip

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class ChildView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val textView: TextView
    private val iconStart: ImageView
    private val iconEnd: ImageView

    init {
        textView = TextView(context, attrs, defStyleAttr)
        textView.layoutParams = LayoutParams(0, LayoutParams.WRAP_CONTENT, 1F)

        iconStart = ImageView(context, attrs, defStyleAttr)
        iconStart.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)

        iconEnd = ImageView(context, attrs, defStyleAttr)
        iconEnd.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
    }

    fun getTextView(): TextView = textView

    fun getStartImageView(): ImageView = iconStart

    fun getEndImageView(): ImageView = iconEnd

    fun attach() {
        removeParent(iconStart)
        removeParent(iconEnd)
        removeParent(textView)

        if (iconStart.drawable != null) {
            val startIconLayoutParams = iconStart.layoutParams
            if (startIconLayoutParams is LayoutParams) {
                startIconLayoutParams.marginEnd = context.resources.getDimensionPixelSize(R.dimen.popup_tooltip_icon_text_margin)
                startIconLayoutParams.gravity = Gravity.CENTER
                addView(iconStart, startIconLayoutParams)
            }
        }

        val textLayoutParams = textView.layoutParams
        if (textLayoutParams is LayoutParams) {
            textLayoutParams.gravity = Gravity.CENTER
            addView(textView, textLayoutParams)
        }

        if (iconEnd.drawable != null) {
            val endIconLayoutParams = iconEnd.layoutParams
            if (endIconLayoutParams is LayoutParams) {
                endIconLayoutParams.marginStart =
                    context.resources.getDimensionPixelSize(R.dimen.popup_tooltip_icon_text_margin)
                endIconLayoutParams.gravity = Gravity.CENTER
                addView(iconEnd, endIconLayoutParams)
            }
        }
    }

    private fun removeParent(view: View) {
        view.parent?.let {
            if (it is ViewGroup) {
                it.removeView(view)
            }
        }
    }

}