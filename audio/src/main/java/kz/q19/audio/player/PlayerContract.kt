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

package kz.q19.audio.player

import kz.q19.domain.error.BaseException

interface PlayerContract {
    interface PlayerCallback {
        fun onPreparePlay()
        fun onStartPlay()
        fun onPlayProgress(mills: Long)
        fun onStopPlay()
        fun onPausePlay()
        fun onSeek(mills: Long)
        fun onError(throwable: BaseException?)
    }

    interface Player {
        fun addPlayerCallback(callback: PlayerCallback?)
        fun removePlayerCallback(callback: PlayerCallback?): Boolean
        fun setData(data: String)
        fun playOrPause()
        fun seek(mills: Long)
        fun pause()
        fun stop()
        val isPlaying: Boolean
        val isPause: Boolean
        val pauseTime: Long

        fun release()
    }
}