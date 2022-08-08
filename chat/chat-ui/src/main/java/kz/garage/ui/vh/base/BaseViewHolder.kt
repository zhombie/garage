package kz.gov.mia.sos.widget.ui.presentation.common.chat.adapter.vh.base

import android.content.Context
import android.content.res.Resources
import android.text.Spanned
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kz.gov.mia.sos.widget.ui.component.HTMLTextView
import kz.garage.ui.ChatMessagesAdapter

internal abstract class BaseViewHolder constructor(
    view: View,
    open val callback: ChatMessagesAdapter.Callback? = null
) : RecyclerView.ViewHolder(view) {

    abstract class LayoutResourceProvider {
        abstract fun getLayoutId(): Int
    }

    val context: Context
        get() = itemView.context

    val resources: Resources
        get() = context.resources

    fun HTMLTextView.bindText(htmlText: Spanned?) {
        if (htmlText.isNullOrBlank()) {
            text = null

            toggleVisibility(false)
        } else {
            setHtmlText(htmlText) { _, url ->
                callback?.onUrlInTextClicked(url)
            }

            setOnLongClickListener {
                callback?.onMessageLongClicked(htmlText.toString())
                true
            }

            enableAutoLinkMask()
            enableLinkMovementMethod()

            toggleVisibility(true)
        }
    }

    fun View.toggleVisibility(visible: Boolean) {
        if (visible) {
            if (!isVisible) {
                isVisible = true
            }
        } else {
            if (isVisible) {
                isVisible = false
            }
        }
    }

}