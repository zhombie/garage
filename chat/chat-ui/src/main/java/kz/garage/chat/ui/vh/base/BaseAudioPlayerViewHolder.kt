package kz.garage.chat.ui.vh.base

import android.view.View
import kz.garage.chat.ui.ChatMessagesAdapter
import kz.garage.chat.ui.ContentSourceProvider
import kz.garage.chat.ui.R
import kz.garage.chat.ui.components.ChatUiMessageAudioPlayerView
import kz.garage.chat.utils.DateUtils
import kz.garage.multimedia.store.model.Audio
import kz.garage.multimedia.store.model.Content
import kz.garage.recyclerview.adapter.viewholder.view.bind

internal abstract class BaseAudioPlayerViewHolder constructor(
    view: View,
    override val contentSourceProvider: ContentSourceProvider,
    override val callback: ChatMessagesAdapter.Callback? = null
) : BaseViewHolder(view = view, contentSourceProvider = contentSourceProvider, callback = callback) {

    companion object {
        private val TAG = BaseAudioPlayerViewHolder::class.java.simpleName
    }

    protected val audioPlayerView by bind<ChatUiMessageAudioPlayerView>(R.id.audioPlayerView)

    protected fun bindAudio(content: Content?) = bindAudio(listOfNotNull(content))

    protected fun bindAudio(contents: List<Content>?) {
        val audio = if (contents.isNullOrEmpty()) null else contents.first()
        if (audio is Audio) {
            with(audioPlayerView) {
                setIconImageResource(R.drawable.chat_ui_ic_play_white_24dp)

                title = audio.label

                playbackTimeText = formatAudioProgress(0, 0)

                if (!isPlaybackTimeVisible) {
                    isPlaybackTimeVisible = true
                }

                setOnClickListener {
                    callback?.onAudioClicked(audio, absoluteAdapterPosition)
                }

                addOnSliderValueChangeListener { progress ->
                    if (callback?.onSliderChange(audio, progress) == true) {
                        // Ignored
                    } else {
                        sliderProgress = 0F
                    }
                }

                toggleVisibility(true)
            }
        } else {
            audioPlayerView.toggleVisibility(false)
        }
    }

    fun setAudioPlaybackState(isPlaying: Boolean) {
//        Logger.debug(TAG, "setAudioPlaybackState() -> isPlaying: $isPlaying")

        audioPlayerView.setIconImageResource(
            if (isPlaying) R.drawable.chat_ui_ic_pause_white
            else R.drawable.chat_ui_ic_play_white_24dp
        )
    }

    fun resetAudioPlaybackState(duration: Long) {
//        Logger.debug(TAG, "resetAudioPlaybackState() -> duration: $duration")

        with(audioPlayerView) {
            minSliderValue = 0F
            maxSliderValue = 100F

            sliderProgress = 0F

            playbackTimeText = formatAudioProgress(0, duration)

            if (!isPlaybackTimeVisible) {
                isPlaybackTimeVisible = true
            }
        }
    }

    fun setAudioPlayProgress(currentPosition: Long, duration: Long, progress: Float) {
//        Logger.debug(
//            TAG, "setAudioPlayProgress() -> " +
//                    "currentPosition: $currentPosition, " +
//                    "duration: $duration, " +
//                    "progress: $progress"
//        )

        with(audioPlayerView) {
//            Logger.debug(
//                TAG, "setAudioPlayProgress() -> " +
//                        "minSliderValue: $minSliderValue, " +
//                        "maxSliderValue: $maxSliderValue"
//            )

            minSliderValue = 0F
            maxSliderValue = 100F
            sliderProgress = progress

            setIconImageResource(
                if (sliderProgress < maxSliderValue) R.drawable.chat_ui_ic_pause_white
                else R.drawable.chat_ui_ic_play_white_24dp
            )

            playbackTimeText = formatAudioProgress(currentPosition, duration)

            if (!isPlaybackTimeVisible) {
                isPlaybackTimeVisible = true
            }
        }
    }

    protected fun formatAudioProgress(left: Long, right: Long): String =
        DateUtils.formatToDigitalClock(left) + " / " + DateUtils.formatToDigitalClock(right)

}
