package kz.garage.ui

import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kz.garage.chat.core.model.Entity
import kz.garage.chat.core.model.Message
import kz.garage.chat.core.model.Notification
import kz.garage.chat.core.model.reply_markup.button.Button
import kz.garage.multimedia.store.model.Audio
import kz.garage.multimedia.store.model.Document
import kz.garage.multimedia.store.model.Image
import kz.garage.multimedia.store.model.Video
import kz.gov.mia.sos.widget.core.error.ViewHolderViewTypeException
import kz.gov.mia.sos.widget.ui.model.DownloadState
import kz.gov.mia.sos.widget.ui.presentation.common.chat.adapter.vh.*
import kz.gov.mia.sos.widget.ui.presentation.common.chat.adapter.vh.base.BaseAudioPlayerViewHolder
import kz.gov.mia.sos.widget.ui.presentation.common.chat.adapter.vh.base.BaseDocumentMessageViewHolder
import kz.gov.mia.sos.widget.ui.presentation.common.chat.adapter.vh.base.BaseRichContentMessageViewHolder

internal class ChatMessagesAdapter constructor(
    private val callback: Callback? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private val TAG = ChatMessagesAdapter::class.java.simpleName
    }

    // TODO: Support separate ReplyMarkup message
    object ViewType {
        private const val OUTGOING_MESSAGE: Int = 1000
        const val OUTGOING_TEXT_MESSAGE: Int = OUTGOING_MESSAGE + 2
        const val OUTGOING_IMAGE_MESSAGE: Int = OUTGOING_MESSAGE + 4
        const val OUTGOING_VIDEO_MESSAGE: Int = OUTGOING_MESSAGE + 8
        const val OUTGOING_AUDIO_MESSAGE: Int = OUTGOING_MESSAGE + 16
        const val OUTGOING_DOCUMENT_MESSAGE: Int = OUTGOING_MESSAGE + 32
        const val OUTGOING_RICH_CONTENT_MESSAGE: Int = OUTGOING_MESSAGE + 64

        private const val INCOMING_MESSAGE: Int = 2000
        const val INCOMING_TEXT_MESSAGE: Int = INCOMING_MESSAGE + 2
        const val INCOMING_IMAGE_MESSAGE: Int = INCOMING_MESSAGE + 4
        const val INCOMING_VIDEO_MESSAGE: Int = INCOMING_MESSAGE + 8
        const val INCOMING_AUDIO_MESSAGE: Int = INCOMING_MESSAGE + 16
        const val INCOMING_DOCUMENT_MESSAGE: Int = INCOMING_MESSAGE + 32
        const val INCOMING_RICH_CONTENT_MESSAGE: Int = INCOMING_MESSAGE + 64

        const val NOTIFICATION: Int = 3000
    }

    private object PayloadKey {
        const val ACTION: String = "action"

        const val PROGRESS: String = "progress"

        const val IS_PLAYING: String = "is_playing"

        const val CURRENT_POSITION: String = "current_position_millis"
        const val DURATION: String = "duration_millis"
    }

    private object Action {
        const val SET_DOWNLOAD_STATE: String = "set_download_state"

        const val SET_AUDIO_PLAY_PROGRESS: String = "set_audio_play_progress"

        const val SET_AUDIO_PLAYBACK_STATE: String = "set_audio_playback_state"
        const val RESET_AUDIO_PLAYBACK_STATE: String = "reset_audio_playback_state"
    }

    private val entities = mutableListOf<Entity>()

    fun getEntities(): List<Entity> = entities

    fun setEntities(newEntities: List<Entity>, notify: Boolean = true): Boolean {
        if (newEntities.isEmpty()) return true
        if (entities.isNotEmpty()) {
            val size = entities.size
            entities.clear()
            if (notify) {
                notifyItemRangeRemoved(0, size - 1)
            }
        }
        entities.addAll(newEntities.reversed())
        if (notify) {
            notifyItemRangeInserted(0, entities.size - 1)
        }
        return entities.isNotEmpty()
    }

    fun addNewEntity(message: Entity, notify: Boolean = true): Boolean {
        entities.add(0, message)
        val isAdded = entities.contains(message)
        if (notify) {
            notifyItemInserted(0)
        }
        return isAdded
    }

    fun clear(notify: Boolean = true): Boolean {
        if (entities.isEmpty()) return true
        val size = entities.size
        entities.clear()
        if (notify) {
            notifyItemRangeRemoved(0, size - 1)
        }
        return entities.isEmpty()
    }

    fun setDownloadState(state: DownloadState) {
        notifyItemChanged(state.itemPosition, Bundle().apply {
            putString(PayloadKey.ACTION, Action.SET_DOWNLOAD_STATE)
            putParcelable("state", state)
        })
    }

    fun setAudioPlaybackState(itemPosition: Int, isPlaying: Boolean) {
        notifyItemChanged(itemPosition, Bundle().apply {
            putString(PayloadKey.ACTION, Action.SET_AUDIO_PLAYBACK_STATE)

            putBoolean(PayloadKey.IS_PLAYING, isPlaying)
        })
    }

    fun resetAudioPlaybackState(itemPosition: Int, duration: Long) {
        notifyItemChanged(itemPosition, Bundle().apply {
            putString(PayloadKey.ACTION, Action.RESET_AUDIO_PLAYBACK_STATE)

            putLong(PayloadKey.DURATION, duration)
        })
    }

    fun setAudioPlayProgress(itemPosition: Int, progress: Float, currentPosition: Long, duration: Long) {
        notifyItemChanged(itemPosition, Bundle().apply {
            putString(PayloadKey.ACTION, Action.SET_AUDIO_PLAY_PROGRESS)

            putFloat(PayloadKey.PROGRESS, progress)
            putLong(PayloadKey.CURRENT_POSITION, currentPosition)
            putLong(PayloadKey.DURATION, duration)
        })
    }

    private fun getItem(position: Int): Entity = entities[position]

    override fun getItemCount(): Int = entities.size

    override fun getItemViewType(position: Int): Int {
        val viewType = if (entities.isEmpty()) {
            super.getItemViewType(position)
        } else {
            when (val entity = getItem(position)) {
                is Message -> {
//                    Logger.debug(TAG, "getItemViewType() -> position: $position, entity: $entity")

                    when (entity.direction) {
                        Message.Direction.OUTGOING ->
                            when {
                                entity.isTextMessage() ->
                                    ViewType.OUTGOING_TEXT_MESSAGE
                                entity.isNullableTextMessageWithImages() ->
                                    ViewType.OUTGOING_IMAGE_MESSAGE
                                entity.isNullableTextMessageWithVideos() ->
                                    ViewType.OUTGOING_VIDEO_MESSAGE
                                entity.isNullableTextMessageWithAudio() ->
                                    ViewType.OUTGOING_AUDIO_MESSAGE
                                entity.isNullableTextMessageWithDocuments() ->
                                    ViewType.OUTGOING_DOCUMENT_MESSAGE
                                else ->
                                    ViewType.OUTGOING_RICH_CONTENT_MESSAGE
                            }
                        Message.Direction.INCOMING ->
                            when {
                                entity.isTextMessage() ->
                                    ViewType.INCOMING_TEXT_MESSAGE
                                entity.isNullableTextMessageWithImages() ->
                                    ViewType.INCOMING_IMAGE_MESSAGE
                                entity.isNullableTextMessageWithVideos() ->
                                    ViewType.INCOMING_VIDEO_MESSAGE
                                entity.isNullableTextMessageWithAudio() ->
                                    ViewType.INCOMING_AUDIO_MESSAGE
                                entity.isNullableTextMessageWithDocuments() ->
                                    ViewType.INCOMING_DOCUMENT_MESSAGE
                                else ->
                                    ViewType.INCOMING_RICH_CONTENT_MESSAGE
                            }
                    }
                }
                is Notification -> ViewType.NOTIFICATION
                else -> super.getItemViewType(position)
            }
        }
//        Logger.debug(TAG, "getItemViewType() -> position: $position, viewType: $viewType")
        return viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            ViewType.OUTGOING_TEXT_MESSAGE ->
                OutgoingTextMessageViewHolder(
                    parent.inflate(OutgoingTextMessageViewHolder.getLayoutId()),
                    callback
                )
            ViewType.OUTGOING_IMAGE_MESSAGE ->
                OutgoingImageMessageViewHolder(
                    parent.inflate(OutgoingImageMessageViewHolder.getLayoutId()),
                    callback
                )
            ViewType.OUTGOING_VIDEO_MESSAGE ->
                OutgoingVideoMessageViewHolder(
                    parent.inflate(OutgoingVideoMessageViewHolder.getLayoutId()),
                    callback
                )
            ViewType.OUTGOING_AUDIO_MESSAGE ->
                OutgoingAudioMessageViewHolder(
                    parent.inflate(OutgoingAudioMessageViewHolder.getLayoutId()),
                    callback
                )
            ViewType.OUTGOING_DOCUMENT_MESSAGE ->
                OutgoingDocumentMessageViewHolder(
                    parent.inflate(OutgoingDocumentMessageViewHolder.getLayoutId()),
                    callback
                )
            ViewType.OUTGOING_RICH_CONTENT_MESSAGE ->
                OutgoingRichContentMessageViewHolder(
                    parent.inflate(OutgoingRichContentMessageViewHolder.getLayoutId()),
                    callback
                )

            ViewType.INCOMING_TEXT_MESSAGE ->
                IncomingTextMessageViewHolder(
                    parent.inflate(IncomingTextMessageViewHolder.getLayoutId()),
                    callback
                )
            ViewType.INCOMING_IMAGE_MESSAGE ->
                IncomingImageMessageViewHolder(
                    parent.inflate(IncomingImageMessageViewHolder.getLayoutId()),
                    callback
                )
            ViewType.INCOMING_VIDEO_MESSAGE ->
                IncomingVideoMessageViewHolder(
                    parent.inflate(IncomingVideoMessageViewHolder.getLayoutId()),
                    callback
                )
            ViewType.INCOMING_AUDIO_MESSAGE ->
                IncomingAudioMessageViewHolder(
                    parent.inflate(IncomingAudioMessageViewHolder.getLayoutId()),
                    callback
                )
            ViewType.INCOMING_DOCUMENT_MESSAGE ->
                IncomingDocumentMessageViewHolder(
                    parent.inflate(IncomingDocumentMessageViewHolder.getLayoutId()),
                    callback
                )
            ViewType.INCOMING_RICH_CONTENT_MESSAGE ->
                IncomingRichContentMessageViewHolder(
                    parent.inflate(IncomingRichContentMessageViewHolder.getLayoutId()),
                    callback
                )

            ViewType.NOTIFICATION ->
                NotificationMessageViewHolder(
                    parent.inflate(NotificationMessageViewHolder.getLayoutId())
                )

            else ->
                throw ViewHolderViewTypeException(viewType)
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val entity = getItem(position)

        when (holder) {
            is OutgoingTextMessageViewHolder -> if (entity is Message) holder.bind(entity)
            is OutgoingImageMessageViewHolder -> if (entity is Message) holder.bind(entity)
            is OutgoingVideoMessageViewHolder -> if (entity is Message) holder.bind(entity)
            is OutgoingAudioMessageViewHolder -> if (entity is Message) holder.bind(entity)
            is OutgoingDocumentMessageViewHolder -> if (entity is Message) holder.bind(entity)
            is OutgoingRichContentMessageViewHolder -> if (entity is Message) holder.bind(entity)

            is IncomingTextMessageViewHolder -> if (entity is Message) holder.bind(entity)
            is IncomingImageMessageViewHolder -> if (entity is Message) holder.bind(entity)
            is IncomingVideoMessageViewHolder -> if (entity is Message) holder.bind(entity)
            is IncomingAudioMessageViewHolder -> if (entity is Message) holder.bind(entity)
            is IncomingDocumentMessageViewHolder -> if (entity is Message) holder.bind(entity)
            is IncomingRichContentMessageViewHolder -> if (entity is Message) holder.bind(entity)

            is NotificationMessageViewHolder -> if (entity is Notification) holder.bind(entity)

            else -> {
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        val payload = payloads.lastOrNull()

        if (payload is Bundle) {
            when (payload.getString(PayloadKey.ACTION)) {
                Action.SET_DOWNLOAD_STATE -> {
                    when (holder) {
                        is BaseDocumentMessageViewHolder ->
                            holder.setDocumentDownloadState(payload.getParcelable("state"))
                        is BaseRichContentMessageViewHolder ->
                            holder.setDocumentDownloadState(payload.getParcelable("state"))
                        else -> {
                            super.onBindViewHolder(holder, position, payloads)
                        }
                    }
                }
                Action.SET_AUDIO_PLAYBACK_STATE ->
                    if (holder is BaseAudioPlayerViewHolder) {
                        holder.setAudioPlaybackState(payload.getBoolean(PayloadKey.IS_PLAYING))
                    } else {
                        super.onBindViewHolder(holder, position, payloads)
                    }
                Action.RESET_AUDIO_PLAYBACK_STATE ->
                    if (holder is BaseAudioPlayerViewHolder) {
                        holder.resetAudioPlaybackState(payload.getLong(PayloadKey.DURATION))
                    } else {
                        super.onBindViewHolder(holder, position, payloads)
                    }
                Action.SET_AUDIO_PLAY_PROGRESS ->
                    if (holder is BaseAudioPlayerViewHolder) {
                        holder.setAudioPlayProgress(
                            currentPosition = payload.getLong(PayloadKey.CURRENT_POSITION),
                            duration = payload.getLong(PayloadKey.DURATION),
                            progress = payload.getFloat(PayloadKey.PROGRESS)
                        )
                    } else {
                        super.onBindViewHolder(holder, position, payloads)
                    }
            }
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    interface Callback {
        // Text
        fun onUrlInTextClicked(url: String) {}

        // Multimedia
        fun onImageClicked(imageView: ImageView, image: Image) {}
        fun onVideoClicked(imageView: ImageView, video: Video) {}
        fun onAudioClicked(audio: Audio, itemPosition: Int): Boolean = false
        fun onDocumentClicked(document: Document, itemPosition: Int) {}

        // Button
        fun onInlineButtonClicked(
            message: Message,
            button: Button,
            itemPosition: Int
        ) {}

        // Seek bar
        fun onSliderChange(audio: Audio, progress: Float): Boolean = false

        // Long click
        fun onMessageLongClicked(text: String) {}
    }

}