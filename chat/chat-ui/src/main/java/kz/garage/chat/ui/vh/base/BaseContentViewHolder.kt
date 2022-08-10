package kz.garage.chat.ui.vh.base

import android.text.format.Formatter
import android.view.View
import kz.garage.chat.ui.ChatMessagesAdapter
import kz.garage.chat.ui.ContentSourceProvider
import kz.garage.chat.ui.R
import kz.garage.chat.ui.components.ChatUiMessageContentView
import kz.garage.multimedia.store.ext.representation
import kz.garage.multimedia.store.model.Content
import kz.garage.multimedia.store.model.Document
import kz.garage.recyclerview.adapter.viewholder.view.bind
import java.io.File

internal abstract class BaseContentViewHolder constructor(
    view: View,
    override val contentSourceProvider: ContentSourceProvider,
    override val callback: ChatMessagesAdapter.Callback? = null
) : BaseViewHolder(view = view, contentSourceProvider = contentSourceProvider, callback = callback) {

    protected val contentView by bind<ChatUiMessageContentView>(R.id.contentView)

    protected fun bindDocument(content: Content?) = bindDocuments(listOfNotNull(content))

    protected fun bindDocuments(contents: List<Content>?) {
        val document = if (contents.isNullOrEmpty()) null else contents.first()
        if (document is Document) {
            with(contentView) {
                progress = 0

                title = document.label

                var file: File? = null
                if (document.publicFile?.exists() == true) {
                    file = requireNotNull(document.publicFile?.requireFile())

                    description = context.getString(R.string.chat_ui_open_file)

                    setIconImageResource(R.drawable.chat_ui_ic_document_white)
                } else {
//                    file = DownloadAssistant.getDownloadableFile(context, document)

//                    if (file.exists()) {
//                        description = context.getString(R.string.chat_ui_open_file)
//
//                        setIconImageResource(R.drawable.chat_ui_ic_document_white)
//                    } else {
                        description = context.getString(R.string.chat_ui_file_download)

                        setIconImageResource(R.drawable.chat_ui_ic_download_white)
//                    }
                }

                val subtitleBuilder = mutableListOf(
                    context.getString(document.representation)
                )

                val size = document.properties?.size ?: file?.length() ?: 0L
                if (size < 0L) {
                    // Ignored
                } else {
                    subtitleBuilder.add(Formatter.formatShortFileSize(context, size))
                }

                subtitle = subtitleBuilder.joinToString(" • ")

                setOnClickListener {
                    callback?.onDocumentClicked(document, absoluteAdapterPosition)
                }

                toggleVisibility(true)
            }
        } else {
            contentView.toggleVisibility(false)
        }
    }

//    fun setDocumentDownloadState(state: DownloadState?) {
//        when (state) {
//            is DownloadState.Pending -> {
//                contentView.progress = state.progress.roundToInt()
//
//                val file = DownloadAssistant.getDownloadableFile(context, state.content)
//
//                val subtitle = mutableListOf(
//                    context.getString(state.content.representation)
//                )
//
//                val size = state.content.properties?.size ?: file.length()
//                if (size < 0L) {
//                    // Ignored
//                } else {
//                    subtitle.add(Formatter.formatShortFileSize(context, size))
//                }
//
//                contentView.subtitle = subtitle.joinToString(" • ")
//
//                contentView.description =
//                    context.getString(R.string.chat_ui_file_pending_download)
//            }
//            is DownloadState.Cancelled -> {
//                contentView.progress = state.progress.roundToInt()
//
//                val file = DownloadAssistant.getDownloadableFile(context, state.content)
//
//                val subtitle = mutableListOf(
//                    context.getString(state.content.representation)
//                )
//
//                val size = state.content.properties?.size ?: file.length()
//                if (size < 0L) {
//                    // Ignored
//                } else {
//                    subtitle.add(Formatter.formatShortFileSize(context, size))
//                }
//
//                contentView.subtitle = subtitle.joinToString(" • ")
//
//                contentView.description =
//                    context.getString(R.string.chat_ui_file_download_cancelled)
//            }
//            is DownloadState.Completed -> {
//                if (contentView.progress > 0) {
//                    contentView.progress = 0
//                }
//
//                val file = DownloadAssistant.getDownloadableFile(context, state.content)
//
//                if (file.exists()) {
//                    contentView.setIconImageResource(R.drawable.chat_ui_ic_document_white)
//
//                    contentView.description = context.getString(R.string.chat_ui_open_file)
//                } else {
//                    contentView.setIconImageResource(R.drawable.chat_ui_ic_download_white)
//
//                    contentView.description = context.getString(R.string.chat_ui_file_download)
//                }
//
//                val subtitle = mutableListOf(
//                    context.getString(state.content.representation)
//                )
//
//                val size = state.content.properties?.size ?: file.length()
//                if (size < 0L) {
//                    // Ignored
//                } else {
//                    subtitle.add(Formatter.formatShortFileSize(context, size))
//                }
//
//                contentView.subtitle = subtitle.joinToString(" • ")
//            }
//            else -> {
//                if (contentView.progress > 0) {
//                    contentView.progress = 0
//                }
//
//                contentView.description = context.getString(R.string.chat_ui_error_unknown)
//            }
//        }
//    }

}