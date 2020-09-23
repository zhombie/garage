package kz.q19.audio

import kz.q19.audio.model.Record
import kz.q19.domain.error.BaseException
import java.io.File

interface AudioRecorderCallback {
    fun onRecordingStarted(file: File)
    fun onRecordingPaused()
    fun onRecordProcessing()
    fun onRecordFinishProcessing()
    fun onRecordingStopped(file: File, record: Record)
    fun onRecordingProgress(mills: Long, amplitude: Int)
    fun onError(throwable: BaseException)
}