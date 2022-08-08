package kz.gov.mia.sos.widget.ui.presentation.common.chat.adapter.vh.base

import android.view.View
import kz.garage.kotlin.simpleNameOf
import kz.garage.multimedia.store.model.Audio
import kz.garage.multimedia.store.model.Content
import kz.garage.recyclerview.adapter.viewholder.view.bind
import kz.gov.mia.sos.widget.R
import kz.gov.mia.sos.widget.core.logging.Logger
import kz.gov.mia.sos.widget.ui.component.chat.SOSWidgetMessageAudioPlayerView
import kz.garage.ui.ChatMessagesAdapter
import kz.gov.mia.sos.widget.utils.DateUtils

internal abstract class BaseAudioPlayerViewHolder constructor(
    view: View,
    override val callback: ChatMessagesAdapter.Callback? = null
) : BaseViewHolder(view = view, callback = callback) {

    companion object {
        private val TAG = simpleNameOf<BaseAudioPlayerViewHolder>()
    }

    protected val audioPlayerView by bind<SOSWidgetMessageAudioPlayerView>(R.id.audioPlayerView)

    protected fun bindAudio(content: Content?) = bindAudio(listOfNotNull(content))

    protected fun bindAudio(contents: List<Content>?) {
        val audio = if (contents.isNullOrEmpty()) null else contents.first()
        if (audio is Audio) {
            with(audioPlayerView) {
                setIconImageResource(R.drawable.sos_widget_ic_play_white_24dp)

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
        Logger.debug(TAG, "setAudioPlaybackState() -> isPlaying: $isPlaying")

        audioPlayerView.setIconImageResource(
            if (isPlaying) R.drawable.sos_widget_ic_pause_white
            else R.drawable.sos_widget_ic_play_white_24dp
        )
    }

    fun resetAudioPlaybackState(duration: Long) {
        Logger.debug(TAG, "resetAudioPlaybackState() -> duration: $duration")

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
        Logger.debug(
            TAG, "setAudioPlayProgress() -> " +
                    "currentPosition: $currentPosition, " +
                    "duration: $duration, " +
                    "progress: $progress"
        )

        with(audioPlayerView) {
            Logger.debug(
                TAG, "setAudioPlayProgress() -> " +
                        "minSliderValue: $minSliderValue, " +
                        "maxSliderValue: $maxSliderValue"
            )

            minSliderValue = 0F
            maxSliderValue = 100F
            sliderProgress = progress

            setIconImageResource(
                if (sliderProgress < maxSliderValue) R.drawable.sos_widget_ic_pause_white
                else R.drawable.sos_widget_ic_play_white_24dp
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
