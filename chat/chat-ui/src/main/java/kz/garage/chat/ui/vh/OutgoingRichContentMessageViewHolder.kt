package kz.gov.mia.sos.widget.ui.presentation.common.chat.adapter.vh

import android.view.View
import kz.garage.chat.ui.ChatMessagesAdapter
import kz.garage.chat.ui.ContentSourceProvider
import kz.garage.chat.ui.R
import kz.garage.chat.ui.vh.base.BaseRichContentMessageViewHolder

internal class OutgoingRichContentMessageViewHolder constructor(
    view: View,
    override val contentSourceProvider: ContentSourceProvider,
    override val callback: ChatMessagesAdapter.Callback? = null
) : BaseRichContentMessageViewHolder(view,contentSourceProvider, callback) {

    companion object : LayoutResourceProvider() {
        override fun getLayoutId(): Int = R.layout.chat_ui_cell_outgoing_rich_content_message
    }

}