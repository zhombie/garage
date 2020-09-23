package kz.q19.audio

import kz.q19.audio.model.Record
import kz.q19.audio.recorder.RecorderContract
import kz.q19.utils.IntArrayList

interface AudioRecorder {
    fun addRecordingCallback(callback: AudioRecorderCallback)
    fun removeRecordingCallback(callback: AudioRecorderCallback)
    fun setRecorder(recorder: RecorderContract.Recorder)
    fun startRecording(filePath: String, channelCount: Int, sampleRate: Int, bitrate: Int)
    fun pauseRecording()
    fun resumeRecording()
    fun stopRecording()
    fun decodeRecordWaveform(decRec: Record)
    val recordingData: IntArrayList
    val recordingDuration: Long
    val isRecording: Boolean
    val isPaused: Boolean
    fun release()
    val isProcessing: Boolean
}