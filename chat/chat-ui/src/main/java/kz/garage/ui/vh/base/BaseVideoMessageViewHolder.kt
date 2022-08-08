package kz.gov.mia.sos.widget.ui.presentation.common.chat.adapter.vh.base

import android.view.View
import kz.garage.chat.core.model.Message
import kz.garage.chat.core.model.asHTMLText
import kz.garage.chat.core.model.getDisplayCreatedAt
import kz.garage.multimedia.store.model.Video
import kz.gov.mia.sos.widget.R
import kz.gov.mia.sos.widget.api.image.load.SOSWidgetImageLoader
import kz.gov.mia.sos.widget.core.image.load.load
import kz.gov.mia.sos.widget.core.multimedia.source.sourceUri
import kz.gov.mia.sos.widget.ui.component.HTMLTextView
import kz.gov.mia.sos.widget.ui.component.chat.MessageImageView
import kz.gov.mia.sos.widget.ui.component.chat.MessageTimeView
import kz.garage.ui.ChatMessagesAdapter
import kz.gov.mia.sos.widget.utils.DateUtils

internal abstract class BaseVideoMessageViewHolder constructor(
    view: View,
    override val callback: ChatMessagesAdapter.Callback? = null
) : BaseViewHolder(view = view, callback = callback) {

    protected val imageView by bind<MessageImageView>(R.id.imageView)
    protected val durationView by bind<MessageTimeView>(R.id.durationView)
    protected val textView by bind<HTMLTextView>(R.id.textView)
    protected val timeView by bind<MessageTimeView>(R.id.timeView)

    fun bind(message: Message) {
        val video = if (message.contents.isNullOrEmpty()) null else message.contents?.first()
        if (video is Video) {
            val uri = video.sourceUri
            if (uri == null) {
                imageView.setImageDrawable(null)
                imageView.toggleVisibility(false)
            } else {
                imageView.load(uri) {
                    setCrossfade(true)
                    setSize(SOSWidgetImageLoader.Request.Size.Inherit)
                }

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