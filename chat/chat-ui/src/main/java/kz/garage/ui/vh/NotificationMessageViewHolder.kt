package kz.gov.mia.sos.widget.ui.presentation.common.chat.adapter.vh

import android.view.View
import com.google.android.material.textview.MaterialTextView
import kz.garage.chat.core.model.Notification
import kz.garage.chat.core.model.getDisplayCreatedAt
import kz.gov.mia.sos.widget.R
import kz.gov.mia.sos.widget.ui.component.chat.MessageTimeView
import kz.gov.mia.sos.widget.ui.presentation.common.chat.adapter.vh.base.BaseViewHolder

internal class NotificationMessageViewHolder constructor(view: View) : BaseViewHolder(view) {

    companion object : LayoutResourceProvider() {
        override fun getLayoutId(): Int = R.layout.sos_widget_cell_notification_message
    }

    private val textView by bind<MaterialTextView>(R.id.textView)
    private val timeView by bind<MessageTimeView>(R.id.timeView)

    fun bind(notification: Notification) {
        textView.text = notification.body

        timeView.text = notification.getDisplayCreatedAt()
    }

}