/*
 * Copyright 2018 Dmitriy Ponomarenko
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    GitHub: https://github.com/Dimowner/AudioRecorder
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@file:Suppress("unused")

package kz.q19.audio.recorder

import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import kz.q19.audio.Constants
import kz.q19.audio.error.AudioRecorderInitException
import kz.q19.audio.recorder.RecorderContract.RecorderCallback
import kz.q19.domain.error.InvalidOutputFileException
import java.io.File
import java.io.IOException
import java.util.*

class AudioRecorder private constructor() : RecorderContract.Recorder {

    companion object {
        private const val TAG = "AudioRecorder"

        val instance: AudioRecorder
            get() = RecorderSingletonHolder.singleton
    }

    private object RecorderSingletonHolder {
        val singleton = AudioRecorder()
    }

    private var recorder: MediaRecorder? = null
    private var recordFile: File? = null
    private var isPrepared = false
    private var timerProgress: Timer? = null
    private var progress: Long = 0
    private var recorderCallback: RecorderCallback? = null

    override var isRecording = false
        private set

    override var isPaused = false
        private set

    override fun setRecorderCallback(callback: RecorderCallback) {
        recorderCallback = callback
    }

    override fun prepare(outputFile: String, channelCount: Int, sampleRate: Int, bitrate: Int) {
        recordFile = File(outputFile)
        if (recordFile?.exists() == true && recordFile?.isFile == true) {
            recorder = MediaRecorder()
            recorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
            recorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            recorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            recorder?.setAudioChannels(channelCount)
            recorder?.setAudioSamplingRate(sampleRate)
            recorder?.setAudioEncodingBitRate(bitrate)
            recorder?.setMaxDuration(-1) // Duration unlimited or use RECORD_MAX_DURATION
            recorder?.setOutputFile(recordFile?.absolutePath)
            try {
                recorder?.prepare()
                isPrepared = true
                recorderCallback?.onPrepareRecord()
            } catch (e: IOException) {
                Log.e(TAG, "$e. prepare() failed")
                recorderCallback?.onError(AudioRecorderInitException())
            } catch (e: IllegalStateException) {
                Log.e(TAG, "$e. prepare() failed")
                recorderCallback?.onError(AudioRecorderInitException())
            }
        } else {
            recorderCallback?.onError(InvalidOutputFileException())
        }
    }

    override fun startRecording() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && isPaused) {
            try {
                recorder?.resume()
                startRecordingTimer()
                recordFile?.let { recorderCallback?.onStartRecord(it) }
                isPaused = false
            } catch (e: IllegalStateException) {
                Log.e(TAG, "$e. unpauseRecording() failed")
                recorderCallback?.onError(AudioRecorderInitException())
            }
        } else {
            if (isPrepared) {
                try {
                    recorder?.start()
                    isRecording = true
                    startRecordingTimer()
                    recordFile?.let { recorderCallback?.onStartRecord(it) }
                } catch (e: RuntimeException) {
                    Log.e(TAG, "$e. startRecording() failed")
                    recorderCallback?.onError(AudioRecorderInitException())
                }
            } else {
                Log.e(TAG, "Recorder is not prepared!!!")
            }
            isPaused = false
        }
    }

    override fun pauseRecording() {
        if (isRecording) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                try {
                    recorder?.pause()
                    pauseRecordingTimer()
                    recorderCallback?.onPauseRecord()
                    isPaused = true
                } catch (e: IllegalStateException) {
                    Log.e(TAG, "$e. pauseRecording() failed")
                    //TODO: Fix exception
                    recorderCallback?.onError(AudioRecorderInitException())
                }
            } else {
                stopRecording()
            }
        }
    }

    override fun stopRecording() {
        if (isRecording) {
            stopRecordingTimer()
            try {
                recorder?.stop()
            } catch (e: RuntimeException) {
                Log.e(TAG, "$e. stopRecording() problems")
            }
            recorder?.release()
            recordFile?.let { recorderCallback?.onStopRecord(it) }
            recordFile = null
            isPrepared = false
            isRecording = false
            isPaused = false
            recorder = null
        } else {
            Log.e(TAG, "Recording has already stopped or hasn't started")
        }
    }

    private fun startRecordingTimer() {
        timerProgress = Timer()
        timerProgress?.schedule(object : TimerTask() {
            override fun run() {
                if (recorderCallback != null && recorder != null) {
                    try {
                        recorderCallback?.onRecordProgress(progress, recorder?.maxAmplitude ?: 0)
                    } catch (e: IllegalStateException) {
                        Log.e(TAG, e.toString())
                    }
                    progress += Constants.VISUALIZATION_INTERVAL
                }
            }
        }, 0, Constants.VISUALIZATION_INTERVAL)
    }

    private fun stopRecordingTimer() {
        timerProgress?.cancel()
        timerProgress?.purge()
        progress = 0
    }

    private fun pauseRecordingTimer() {
        timerProgress?.cancel()
        timerProgress?.purge()
    }

}