@file:Suppress("unused")

package kz.q19.audio.error

import kz.q19.audio.R
import kz.q19.domain.error.BaseException

class RecordingException : BaseException() {
    override val text: Int
        get() = R.string.error_on_recording
}