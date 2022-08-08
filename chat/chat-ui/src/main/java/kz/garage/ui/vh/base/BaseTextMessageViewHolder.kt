package kz.gov.mia.sos.widget.ui.presentation.common.chat.adapter.vh.base

import android.view.View
import kz.garage.chat.core.model.Message
import kz.garage.chat.core.model.asHTMLText
import kz.garage.chat.core.model.getDisplayCreatedAt
import kz.gov.mia.sos.widget.R
import kz.gov.mia.sos.widget.ui.component.HTMLTextView
import kz.gov.mia.sos.widget.ui.component.chat.MessageTimeView
import kz.garage.ui.ChatMessagesAdapter

internal abstract class BaseTextMessageViewHolder constructor(
    view: View,
    override val callback: ChatMessagesAdapter.Callback? = null
) : BaseViewHolder(view = view, callback = callback) {

    protected val textView by bind<HTMLTextView>(R.id.textView)
    protected val timeView by bind<MessageTimeView>(R.id.timeView)

    fun bind(message: Message) {
        textView.bindText(message.asHTMLText)

        timeView.text = message.getDisplayCreatedAt()
    }

}