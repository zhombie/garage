package kz.garage.chat.ui.components

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.URLSpan
import android.text.util.Linkify
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.textview.MaterialTextView
import kz.garage.chat.ui.R

internal class HTMLTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.textViewStyle,
    defStyleRes: Int = 0
) : MaterialTextView(context, attrs, defStyleAttr, defStyleRes) {

    private val colorStateList: ColorStateList by lazy {
        ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_pressed),
                intArrayOf(android.R.attr.state_selected),
                intArrayOf(android.R.attr.state_enabled)
            ),
            intArrayOf(
                ContextCompat.getColor(context, R.color.chat_ui_light_blue),
                ContextCompat.getColor(context, R.color.chat_ui_light_blue),
                ContextCompat.getColor(context, R.color.chat_ui_light_blue)
            )
        )
    }

    init {
        highlightColor = Color.TRANSPARENT

        setLinkTextColor(colorStateList)
    }

    fun enableAutoLinkMask() {
        autoLinkMask = Linkify.ALL
    }

    fun enableLinkMovementMethod() {
        movementMethod = LinkMovementMethod.getInstance()
    }

    fun setHtmlText(spanned: Spanned?, listener: (view: View, url: String) -> Unit): Boolean {
        if (spanned == null) return false
        val builder = SpannableStringBuilder(spanned)
        builder.getSpans(0, spanned.length, URLSpan::class.java).forEach { span ->
            builder.setLinkClickable(span, listener)
        }
        text = builder
        return true
    }

    private fun SpannableStringBuilder.setLinkClickable(
        urlSpan: URLSpan?,
        listener: (view: View, url: String) -> Unit
    ): Boolean {
        if (urlSpan == null) return false
        if (urlSpan.url == null) return false
        setSpan(
            object : ClickableSpan() {
                override fun onClick(view: View) {
                    listener.invoke(view, urlSpan.url)
                }
            },
            getSpanStart(urlSpan),
            getSpanEnd(urlSpan),
            getSpanFlags(urlSpan)
        )
        removeSpan(urlSpan)
        return true
    }

}