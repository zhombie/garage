package kz.gov.mia.sos.widget.ui.presentation.common.chat.adapter.vh.base

import android.text.format.Formatter
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.garage.chat.core.model.Message
import kz.garage.chat.core.model.asHTMLText
import kz.garage.chat.core.model.getDisplayCreatedAt
import kz.garage.chat.core.model.reply_markup.InlineReplyMarkup
import kz.garage.kotlin.simpleNameOf
import kz.garage.multimedia.store.model.Audio
import kz.garage.multimedia.store.model.Document
import kz.garage.multimedia.store.model.Image
import kz.garage.multimedia.store.model.Video
import kz.gov.mia.sos.widget.R
import kz.gov.mia.sos.widget.api.image.load.SOSWidgetImageLoader
import kz.gov.mia.sos.widget.core.image.load.load
import kz.gov.mia.sos.widget.core.logging.Logger
import kz.gov.mia.sos.widget.core.multimedia.DownloadAssistant
import kz.gov.mia.sos.widget.core.multimedia.source.sourceUri
import kz.gov.mia.sos.widget.ui.component.HTMLTextView
import kz.gov.mia.sos.widget.ui.component.chat.SOSWidgetMessageContentView
import kz.gov.mia.sos.widget.ui.component.chat.MessageImageView
import kz.gov.mia.sos.widget.ui.component.chat.MessageTimeView
import kz.gov.mia.sos.widget.ui.model.DownloadState
import kz.gov.mia.sos.widget.ui.model.asButton
import kz.gov.mia.sos.widget.ui.model.asUIKeyboard
import kz.garage.ui.ChatMessagesAdapter
import kz.gov.mia.sos.widget.ui.presentation.common.keyboard.InlineKeyboardAdapter
import kz.gov.mia.sos.widget.ui.presentation.common.keyboard.InlineKeyboardAdapterItemDecoration
import kz.qbox.sdk.domain.model.content.representation

// TODO: Try to reduce boilerplate
internal abstract class BaseRichContentMessageViewHolder constructor(
    view: View,
    override val callback: ChatMessagesAdapter.Callback? = null
) : BaseAudioPlayerViewHolder(view = view, callback = callback) {

    companion object {
        private val TAG = simpleNameOf<BaseRichContentMessageViewHolder>()
    }

    protected val imageView by bind<MessageImageView>(R.id.imageView)
    protected val contentView by bind<SOSWidgetMessageContentView>(R.id.contentView)
    protected val textView by bind<HTMLTextView>(R.id.textView)
    protected val timeView by bind<MessageTimeView>(R.id.timeView)
    protected val recyclerView by bind<RecyclerView>(R.id.recyclerView)

    protected var inlineKeyboardAdapter: InlineKeyboardAdapter? = null

    protected var layoutManager: GridLayoutManager? = null

    protected val inlineKeyboardAdapterItemDecoration by lazy {
        InlineKeyboardAdapterItemDecoration()
    }

    fun bind(message: Message) {
        imageView.toggleVisibility(false)

        contentView.toggleVisibility(false)

        audioPlayerView.toggleVisibility(false)

        when (val content = if (message.contents.isNullOrEmpty()) null else message.contents?.first()) {
            is Image -> {
                val uri = content.sourceUri

                if (uri == null) {
                    imageView.setImageDrawable(null)

                    imageView.toggleVisibility(false)
                } else {
                    imageView.load(uri) {
                        setCrossfade(true)
                        setSize(SOSWidgetImageLoader.Request.Size.Inherit)
                    }

                    imageView.setOnClickListener {
                        callback?.onImageClicked(imageView, content)
                    }

                    imageView.toggleVisibility(true)
                }
            }
            is Video -> {
                val uri = content.sourceUri
                if (uri == null) {
                    imageView.setImageDrawable(null)
                    imageView.toggleVisibility(false)
                } else {
                    imageView.load(uri) {
                        setCrossfade(true)
                        setSize(SOSWidgetImageLoader.Request.Size.Inherit)
                    }

                    imageView.setOnClickListener {
                        callback?.onVideoClicked(imageView, content)
                    }

                    imageView.toggleVisibility(true)
                }
            }
            is Audio -> {
                bindAudio(content)
            }
            is Document -> {
                with(contentView) {
                    progress = 0

                    title = content.label

                    val file = DownloadAssistant.getDownloadableFile(context, content)

                    if (file.exists()) {
                        description = context.getString(R.string.sos_widget_open_file)

                        setIconImageResource(R.drawable.sos_widget_ic_document_white)
                    } else {
                        description = context.getString(R.string.sos_widget_file_download)

                        setIconImageResource(R.drawable.sos_widget_ic_download_white)
                    }

                    val subtitleBuilder = mutableListOf(
                        context.getString(content.representation)
                    )

                    val size = content.properties?.size ?: file.length()
                    if (size < 0L) {
                        // Ignored
                    } else {
                        subtitleBuilder.add(Formatter.formatShortFileSize(context, size))
                    }

                    subtitle = subtitleBuilder.joinToString(" • ")

                    setOnClickListener {
                        callback?.onDocumentClicked(content, absoluteAdapterPosition)
                    }

                    toggleVisibility(true)
                }
            }
            else -> {
                imageView.setImageDrawable(null)
                imageView.toggleVisibility(false)

                contentView.toggleVisibility(false)

                audioPlayerView.toggleVisibility(false)
            }
        }

        val replyMarkup = message.replyMarkup
        if (replyMarkup is InlineReplyMarkup) {
            if (inlineKeyboardAdapter == null) {
                inlineKeyboardAdapter = InlineKeyboardAdapter { inlineButton ->
                    Logger.debug(TAG, "asUIKeyboard() -> inlineButton: $inlineButton")

                    callback?.onInlineButtonClicked(
                        message = message,
                        button = inlineButton.asButton() ?: return@InlineKeyboardAdapter,
                        itemPosition = absoluteAdapterPosition
                    )
                }
            }

            if (recyclerView.adapter == null) {
                recyclerView.isNestedScrollingEnabled = false
                recyclerView.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
                recyclerView.addItemDecoration(inlineKeyboardAdapterItemDecoration)
                recyclerView.adapter = inlineKeyboardAdapter
            }

            if (layoutManager == null) {
                val columnsCount = replyMarkup.getColumnsCount()

                layoutManager = GridLayoutManager(
                    context,
                    columnsCount,
                    GridLayoutManager.VERTICAL,
                    false
                )

                layoutManager?.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        val itemCount = layoutManager?.itemCount ?: 0
                        return if (columnsCount > 0 && itemCount % columnsCount > 0 && position == itemCount - 1) {
                            columnsCount
                        } else {
                            1
                        }
                    }
                }

                recyclerView.layoutManager = layoutManager
            }

            inlineKeyboardAdapter?.setData(replyMarkup.asUIKeyboard().flatten())
        }

        textView.bindText(message.asHTMLText)

        timeView.text = message.getDisplayCreatedAt()
    }

    fun setDocumentDownloadState(state: DownloadState?) {
        when (state) {
            is DownloadState.Pending -> {
                contentView.progress = state.progress.roundToInt()

                val file = DownloadAssistant.getDownloadableFile(context, state.content)

                val subtitle = mutableListOf(
                    context.getString(state.content.representation)
                )

                val size = state.content.properties?.size ?: file.length()
                if (size < 0L) {
                    // Ignored
                } else {
                    subtitle.add(Formatter.formatShortFileSize(context, size))
                }

                contentView.subtitle = subtitle.joinToString(" • ")

                contentView.description = context.getString(R.string.sos_widget_file_pending_download)
            }
            is DownloadState.Cancelled -> {
                contentView.progress = state.progress.roundToInt()

                val file = DownloadAssistant.getDownloadableFile(context, state.content)

                val subtitle = mutableListOf(
                    context.getString(state.content.representation)
                )

                val size = state.content.properties?.size ?: file.length()
                if (size < 0L) {
                    // Ignored
                } else {
                    subtitle.add(Formatter.formatShortFileSize(context, size))
                }

                contentView.subtitle = subtitle.joinToString(" • ")

                contentView.description = context.getString(R.string.sos_widget_file_download_cancelled)
            }
            is DownloadState.Completed -> {
                if (contentView.progress > 0) {
                    contentView.progress = 0
                }

                val file = DownloadAssistant.getDownloadableFile(context, state.content)

                if (file.exists()) {
                    contentView.setIconImageResource(R.drawable.sos_widget_ic_document_white)

                    contentView.description = context.getString(R.string.sos_widget_open_file)
                } else {
                    contentView.setIconImageResource(R.drawable.sos_widget_ic_download_white)

                    contentView.description = context.getString(R.string.sos_widget_file_download)
                }

                val subtitle = mutableListOf(
                    context.getString(state.content.representation)
                )

                val size = state.content.properties?.size ?: file.length()
                if (size < 0L) {
                    // Ignored
                } else {
                    subtitle.add(Formatter.formatShortFileSize(context, size))
                }

                contentView.subtitle = subtitle.joinToString(" • ")
            }
            else -> {
                if (contentView.progress > 0) {
                    contentView.progress = 0
                }

                contentView.description = context.getString(R.string.sos_widget_error_unknown)
            }
        }
    }

}