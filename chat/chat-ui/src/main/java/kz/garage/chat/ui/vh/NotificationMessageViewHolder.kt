package kz.garage.chat.ui.vh

import android.view.View
import com.google.android.material.textview.MaterialTextView
import kz.garage.chat.core.model.Notification
import kz.garage.chat.core.model.getDisplayCreatedAt
import kz.garage.chat.ui.ContentSourceProvider
import kz.garage.chat.ui.R
import kz.garage.chat.ui.components.MessageTimeView
import kz.garage.recyclerview.adapter.viewholder.view.bind
import kz.garage.chat.ui.vh.base.BaseViewHolder

internal class NotificationMessageViewHolder constructor(view: View, override val contentSourceProvider: ContentSourceProvider) : BaseViewHolder(view, contentSourceProvider) {

    companion object : LayoutResourceProvider() {
        override fun getLayoutId(): Int = R.layout.chat_ui_cell_notification_message
    }

    private val textView by bind<MaterialTextView>(R.id.textView)
    private val timeView by bind<MessageTimeView>(R.id.timeView)

    fun bind(notification: Notification) {
        textView.text = notification.body

        timeView.text = notification.getDisplayCreatedAt()
    }

}