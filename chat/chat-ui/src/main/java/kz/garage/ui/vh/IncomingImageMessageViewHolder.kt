package kz.gov.mia.sos.widget.ui.presentation.common.chat.adapter.vh

import android.view.View
import kz.gov.mia.sos.widget.R
import kz.garage.ui.ChatMessagesAdapter
import kz.gov.mia.sos.widget.ui.presentation.common.chat.adapter.vh.base.BaseImageMessageViewHolder

internal class IncomingImageMessageViewHolder constructor(
    view: View,
    override val callback: ChatMessagesAdapter.Callback? = null
) : BaseImageMessageViewHolder(view, callback) {

    companion object : LayoutResourceProvider() {
        override fun getLayoutId(): Int = R.layout.sos_widget_cell_incoming_image_message
    }

}