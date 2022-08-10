package kz.garage.chat.ui.vh.base

import android.view.View
import kz.garage.chat.core.model.Message
import kz.garage.chat.core.model.asHTMLText
import kz.garage.chat.core.model.getDisplayCreatedAt
import kz.garage.chat.ui.ChatMessagesAdapter
import kz.garage.chat.ui.ContentSourceProvider
import kz.garage.chat.ui.R
import kz.garage.chat.ui.components.HTMLTextView
import kz.garage.chat.ui.components.MessageImageView
import kz.garage.chat.ui.components.MessageTimeView
import kz.garage.chat.utils.DateUtils
import kz.garage.multimedia.store.model.Video
import kz.garage.recyclerview.adapter.viewholder.view.bind

internal abstract class BaseVideoMessageViewHolder constructor(
    view: View,
    override val contentSourceProvider: ContentSourceProvider,
    override val callback: ChatMessagesAdapter.Callback? = null
) : BaseViewHolder(view = view, contentSourceProvider = contentSourceProvider, callback = callback) {

    protected val imageView by bind<MessageImageView>(R.id.imageView)
    protected val durationView by bind<MessageTimeView>(R.id.durationView)
    protected val textView by bind<HTMLTextView>(R.id.textView)
    protected val timeView by bind<MessageTimeView>(R.id.timeView)

    fun bind(message: Message) {
        val video = if (message.contents.isNullOrEmpty()) null else message.contents?.first()
        if (video is Video) {
            val uri = contentSourceProvider.provide(video)
            if (uri == null) {
                imageView.setImageDrawable(null)
                imageView.toggleVisibility(false)
            } else {
//                imageView.load(uri) {
//                    setCrossfade(true)
//                    setSize(SOSWidgetImageLoader.Request.Size.Inherit)
//                }

                imageView.setOnClickListener {
                    callback?.onVideoClicked(imageView, video)
                }

                imageView.toggleVisibility(true)
            }

            val duration = video.duration
            if (duration == null) {
                durationView.text = null
                durationView.toggleVisibility(false)
            } else {
                durationView.text = DateUtils.formatToDigitalClock(duration)
                durationView.toggleVisibility(true)
            }
        } else {
            imageView.setImageDrawable(null)
            imageView.toggleVisibility(false)

            durationView.text = null
            durationView.toggleVisibility(false)
        }

        textView.bindText(message.asHTMLText)

        timeView.text = message.getDisplayCreatedAt()
    }

}