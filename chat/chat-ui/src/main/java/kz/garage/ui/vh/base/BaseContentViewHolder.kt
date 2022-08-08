package kz.gov.mia.sos.widget.ui.presentation.common.chat.adapter.vh.base

import android.text.format.Formatter
import android.view.View
import kz.garage.multimedia.store.model.Content
import kz.garage.multimedia.store.model.Document
import kz.garage.recyclerview.adapter.viewholder.view.bind
import kz.gov.mia.sos.widget.R
import kz.gov.mia.sos.widget.core.multimedia.DownloadAssistant
import kz.gov.mia.sos.widget.ui.component.chat.SOSWidgetMessageContentView
import kz.gov.mia.sos.widget.ui.model.DownloadState
import kz.garage.ui.ChatMessagesAdapter
import kz.qbox.sdk.domain.model.content.representation
import java.io.File

internal abstract class BaseContentViewHolder constructor(
    view: View,
    override val callback: ChatMessagesAdapter.Callback? = null
) : BaseViewHolder(view = view, callback = callback) {

    protected val contentView by bind<SOSWidgetMessageContentView>(R.id.contentView)

    protected fun bindDocument(content: Content?) = bindDocuments(listOfNotNull(content))

    protected fun bindDocuments(contents: List<Content>?) {
        val document = if (contents.isNullOrEmpty()) null else contents.first()
        if (document is Document) {
            with(contentView) {
                progress = 0

                title = document.label

                val file: File
                if (document.publicFile?.exists() == true) {
                    file = requireNotNull(document.publicFile?.requireFile())

                    description = context.getString(R.string.sos_widget_open_file)

                    setIconImageResource(R.drawable.sos_widget_ic_document_white)
                } else {
                    file = DownloadAssistant.getDownloadableFile(context, document)

                    if (file.exists()) {
                        description = context.getString(R.string.sos_widget_open_file)

                        setIconImageResource(R.drawable.sos_widget_ic_document_white)
                    } else {
                        description = context.getString(R.string.sos_widget_file_download)

                        setIconImageResource(R.drawable.sos_widget_ic_download_white)
                    }
                }

                val subtitleBuilder = mutableListOf(
                    context.getString(document.representation)
                )

                val size = document.properties?.size ?: file.length()
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

                contentView.description =
                    context.getString(R.string.sos_widget_file_pending_download)
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

                contentView.description =
                    context.getString(R.string.sos_widget_file_download_cancelled)
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