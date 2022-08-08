package kz.gov.mia.sos.widget.ui.presentation.common.chat.adapter.vh

import android.view.View
import kz.gov.mia.sos.widget.R
import kz.garage.ui.ChatMessagesAdapter
import kz.gov.mia.sos.widget.ui.presentation.common.chat.adapter.vh.base.BaseVideoMessageViewHolder

internal class OutgoingVideoMessageViewHolder constructor(
    view: View,
    override val callback: ChatMessagesAdapter.Callback? = null
) : BaseVideoMessageViewHolder(view, callback) {

    companion object : LayoutResourceProvider() {
        override fun getLayoutId(): Int = R.layout.sos_widget_cell_outgoing_video_message
    }

}