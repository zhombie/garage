package kz.garage.chat.ui.vh.base

import android.view.View
import kz.garage.chat.core.model.Message
import kz.garage.chat.core.model.asHTMLText
import kz.garage.chat.core.model.getDisplayCreatedAt
import kz.garage.chat.ui.ChatMessagesAdapter
import kz.garage.chat.ui.ContentSourceProvider
import kz.garage.chat.ui.R
import kz.garage.chat.ui.components.HTMLTextView
import kz.garage.chat.ui.components.MessageTimeView
import kz.garage.recyclerview.adapter.viewholder.view.bind

internal abstract class BaseAudioMessageViewHolder constructor(
    view: View,
    override val contentSourceProvider: ContentSourceProvider,
    override val callback: ChatMessagesAdapter.Callback? = null
) : BaseAudioPlayerViewHolder(view = view, contentSourceProvider = contentSourceProvider, callback = callback) {

    protected val textView by bind<HTMLTextView>(R.id.textView)
    protected val timeView by bind<MessageTimeView>(R.id.timeView)

    fun bind(message: Message) {
        bindAudio(message.contents)

        textView.bindText(message.asHTMLText)

        timeView.text = message.getDisplayCreatedAt()
    }

}