/*
 * Copyright 2020 Dmitriy Ponomarenko
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kz.q19.audio

import android.content.Context
import android.os.Handler
import android.util.Log
import kz.q19.audio.AudioDecoder.DecodeListener
import kz.q19.audio.model.Record
import kz.q19.audio.recorder.RecorderContract
import kz.q19.audio.recorder.RecorderContract.RecorderCallback
import kz.q19.domain.error.BaseException
import kz.q19.utils.IntArrayList
import java.io.File
import java.util.*
import kotlin.math.ceil
import kotlin.math.floor

class AppRecorderImpl private constructor(
    private var audioRecorder: RecorderContract.Recorder,
    private val context: Context,
    private val uiHandler: Handler,
    private val localRepository: LocalRepository,
    private val preferences: Preferences,
    private val recordingsTasks: BackgroundQueue,
    private val processingTasks: BackgroundQueue,
) : AudioRecorder {

    companion object {
        private const val TAG = "AppRecorderImpl"

        @Volatile
        private var instance: AppRecorderImpl? = null

        fun getInstance(
            recorder: RecorderContract.Recorder,
            context: Context,
            uiHandler: Handler,
            localRepository: LocalRepository,
            preferences: Preferences,
            tasks: BackgroundQueue,
            processingTasks: BackgroundQueue,
        ): AppRecorderImpl {
            if (instance == null) {
                synchronized(AppRecorderImpl::class.java) {
                    if (instance == null) {
                        instance = AppRecorderImpl(
                            recorder,
                            context,
                            uiHandler,
                            localRepository,
                            preferences,
                            tasks,
                            processingTasks
                        )
                    }
                }
            }
            return instance!!
        }
    }

    private val recorderCallback: RecorderCallback
    private val appCallbacks: MutableList<AudioRecorderCallback>

    override val recordingData: IntArrayList

    override var recordingDuration: Long = 0

    override var isProcessing = false
        private set

    override val isRecording: Boolean
        get() = audioRecorder.isRecording

    override val isPaused: Boolean
        get() = audioRecorder.isPaused

    init {
        appCallbacks = ArrayList()
        recordingData = IntArrayList()

        recorderCallback = object : RecorderCallback {
            override fun onPrepareRecord() {
                audioRecorder.startRecording()
            }

            override fun onStartRecord(output: File) {
                recordingDuration = 0
                onRecordingStarted(output)
            }

            override fun onPauseRecord() {
                onRecordingPaused()
            }

            override fun onRecordProgress(mills: Long, amplitude: Int) {
                recordingDuration = mills
                onRecordingProgress(mills, amplitude)
                recordingData.add(amplitude)
            }

            override fun onStopRecord(output: File) {
                recordingsTasks.postRunnable({
                    val recordInfo = AudioDecoder.readRecordInfo(output)

                    var duration = recordInfo.duration
                    if (duration <= 0) {
                        duration = recordingDuration
                    }

                    recordingDuration = 0

                    val waveForm = convertRecordingData(recordingData,
                        (duration / 1000000F).toInt())

                    val activeRecord = localRepository.getRecord(preferences.getActiveRecord())

                    if (activeRecord != null) {
                        val update = Record(
                            id = activeRecord.id,
                            name = activeRecord.name,
                            duration = duration,
                            created = activeRecord.created,
                            added = activeRecord.added,
                            removed = activeRecord.removed,
                            path = activeRecord.path,
                            format = recordInfo.format,
                            size = recordInfo.size,
                            sampleRate = recordInfo.sampleRate,
                            channelCount = recordInfo.channelCount,
                            bitrate = recordInfo.bitrate,
                            waveformProcessed = activeRecord.isWaveformProcessed,
                            amps = waveForm
                        )

                        if (localRepository.updateRecord(update)) {
                            recordingData.clear()
                            val record = localRepository.getRecord(update.id)
                            if (record != null) {
                                decodeRecordWaveform(record)
                                runOnUIThread { onRecordingStopped(output, record) }
                            } else {
                                onRecordingStopped(output, activeRecord)
                            }
                        }

                    }
                })
            }

            override fun onError(throwable: BaseException) {
                Log.e(TAG, "$throwable")
                onRecordingError(throwable)
            }
        }
        audioRecorder?.setRecorderCallback(recorderCallback)
    }

    private fun convertRecordingData(list: IntArrayList, durationSec: Int): IntArray {
        return if (durationSec > Constants.LONG_RECORD_THRESHOLD_SECONDS) {
            val sampleCount: Int = Utils.getLongWaveformSampleCount(context)
            val waveForm = IntArray(sampleCount)
            if (list.size() < sampleCount * 2) {
                val scale: Float = list.size().toFloat() / sampleCount.toFloat()
                for (i in 0 until sampleCount) {
                    waveForm[i] = convertAmplitude(list[floor(i * scale).toInt()].toDouble())
                }
            } else {
                val scale: Float = list.size().toFloat() / sampleCount.toFloat()
                for (i in 0 until sampleCount) {
                    var value = 0
                    val step = ceil(scale.toDouble()).toInt()
                    for (j in 0 until step) {
                        value += list[((i * scale + j).toInt())]
                    }
                    value = (value / scale).toInt()
                    waveForm[i] = convertAmplitude(value.toDouble())
                }
            }
            waveForm
        } else {
            val waveForm = IntArray(list.size())
            for (i in 0 until list.size()) {
                waveForm[i] = convertAmplitude(list[i].toDouble())
            }
            waveForm
        }
    }

    /**
     * Convert dB amp value to view amp.
     */
    private fun convertAmplitude(amplitude: Double): Int {
        return (255 * (amplitude / 32767F)).toInt()
    }

    override fun decodeRecordWaveform(decRec: Record) {
        processingTasks.postRunnable({
            isProcessing = true
            val path = decRec.path
            if (path.isNotEmpty()) {
                AudioDecoder.decode(context, path, object : DecodeListener {
                    override fun onStartDecode(
                        duration: Long,
                        channelsCount: Int,
                        sampleRate: Int,
                    ) {
                        decRec.duration = duration
                        runOnUIThread { onRecordProcessing() }
                    }

                    override fun onFinishDecode(data: IntArray, duration: Long) {
                        val record = Record(
                            id = decRec.id,
                            name = decRec.name,
                            duration = decRec.duration,
                            created = decRec.created,
                            added = decRec.added,
                            removed = decRec.removed,
                            path = decRec.path,
                            format = decRec.format,
                            size = decRec.size,
                            sampleRate = decRec.sampleRate,
                            channelCount = decRec.channelCount,
                            bitrate = decRec.bitrate,
                            waveformProcessed = true,
                            amps = data
                        )
                        localRepository.updateRecord(record)
                        isProcessing = false
                        runOnUIThread { onRecordFinishProcessing() }
                    }

                    override fun onError(exception: Exception?) {
                        isProcessing = false
                    }
                })
            } else {
                isProcessing = false
                Log.e(TAG, "File path is null or empty")
            }
        })
    }


    private fun runOnUIThread(runnable: Runnable) {
        runOnUIThread(runnable, 0L)
    }

    private fun runOnUIThread(runnable: Runnable, delay: Long) {
        if (delay == 0L) {
            uiHandler.post(runnable)
        } else {
            uiHandler.postDelayed(runnable, delay)
        }
    }

    private fun cancelRunOnUIThread(runnable: Runnable) {
        uiHandler.removeCallbacks(runnable)
    }

    override fun addRecordingCallback(callback: AudioRecorderCallback) {
        appCallbacks.add(callback)
    }

    override fun removeRecordingCallback(callback: AudioRecorderCallback) {
        appCallbacks.remove(callback)
    }

    override fun setRecorder(recorder: RecorderContract.Recorder) {
        audioRecorder = recorder
        audioRecorder.setRecorderCallback(recorderCallback)
    }

    override fun startRecording(
        filePath: String,
        channelCount: Int,
        sampleRate: Int,
        bitrate: Int,
    ) {
        if (!audioRecorder.isRecording) {
            audioRecorder.prepare(filePath, channelCount, sampleRate, bitrate)
        }
    }

    override fun pauseRecording() {
        if (audioRecorder.isRecording) {
            audioRecorder.pauseRecording()
        }
    }

    override fun resumeRecording() {
        if (audioRecorder.isPaused) {
            audioRecorder.startRecording()
        }
    }

    override fun stopRecording() {
        if (audioRecorder.isRecording) {
            audioRecorder.stopRecording()
        }
    }

    override fun release() {
        recordingData.clear()
        audioRecorder.stopRecording()
        appCallbacks.clear()
    }

    private fun onRecordingStarted(output: File) {
        if (appCallbacks.isNotEmpty()) {
            for (i in appCallbacks.indices) {
                appCallbacks[i].onRecordingStarted(output)
            }
        }
    }

    private fun onRecordingPaused() {
        if (appCallbacks.isNotEmpty()) {
            for (i in appCallbacks.indices) {
                appCallbacks[i].onRecordingPaused()
            }
        }
    }

    private fun onRecordProcessing() {
        isProcessing = true
        if (appCallbacks.isNotEmpty()) {
            for (i in appCallbacks.indices) {
                appCallbacks[i].onRecordProcessing()
            }
        }
    }

    private fun onRecordFinishProcessing() {
        isProcessing = false
        if (appCallbacks.isNotEmpty()) {
            for (i in appCallbacks.indices) {
                appCallbacks[i].onRecordFinishProcessing()
            }
        }
    }

    private fun onRecordingStopped(file: File, record: Record) {
        if (appCallbacks.isNotEmpty()) {
            for (i in appCallbacks.indices) {
                appCallbacks[i].onRecordingStopped(file, record)
            }
        }
    }

    private fun onRecordingProgress(mills: Long, amp: Int) {
        if (appCallbacks.isNotEmpty()) {
            for (i in appCallbacks.indices) {
                appCallbacks[i].onRecordingProgress(mills, amp)
            }
        }
    }

    private fun onRecordingError(e: BaseException) {
        if (appCallbacks.isNotEmpty()) {
            for (i in appCallbacks.indices) {
                appCallbacks[i].onError(e)
            }
        }
    }
    
}