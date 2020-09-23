@file:Suppress("unused")

package kz.q19.audio.error

import kz.q19.audio.R
import kz.q19.domain.error.BaseException

class AudioRecorderInitException : BaseException() {
    override val text: Int
        get() = R.string.error_failed_to_init_audio_recorder
}