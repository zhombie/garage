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
package kz.q19.audio.recorder

import kz.q19.domain.error.BaseException
import java.io.File

interface RecorderContract {
    interface RecorderCallback {
        fun onPrepareRecord()
        fun onStartRecord(output: File)
        fun onPauseRecord()
        fun onRecordProgress(mills: Long, amplitude: Int)
        fun onStopRecord(output: File)
        fun onError(throwable: BaseException)
    }

    interface Recorder {
        fun setRecorderCallback(callback: RecorderCallback)
        fun prepare(outputFile: String, channelCount: Int, sampleRate: Int, bitrate: Int)
        fun startRecording()
        fun pauseRecording()
        fun stopRecording()
        val isRecording: Boolean
        val isPaused: Boolean
    }
}