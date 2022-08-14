package kz.garage.chat.ui.vh

import android.view.View
import kz.garage.chat.ui.ChatMessagesAdapter
import kz.garage.chat.ui.ContentSourceProvider
import kz.garage.chat.ui.R
import kz.garage.chat.ui.imageloader.ChatUiImageLoader
import kz.garage.chat.ui.vh.base.BaseImageMessageViewHolder

internal class OutgoingImageMessageViewHolder constructor(
    view: View,
    override val imageLoader: ChatUiImageLoader,
    override val contentSourceProvider: ContentSourceProvider,
    override val callback: ChatMessagesAdapter.Callback? = null
) : BaseImageMessageViewHolder(view, imageLoader, contentSourceProvider, callback) {

    companion object : LayoutResourceProvider() {
        override fun getLayoutId(): Int = R.layout.chat_ui_cell_outgoing_image_message
    }

}