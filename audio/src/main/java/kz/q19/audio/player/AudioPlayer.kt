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

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.MediaPlayer.OnPreparedListener
import android.util.Log
import kz.q19.audio.Constants
import kz.q19.audio.error.AudioPlayerDataSourceException
import kz.q19.audio.player.PlayerContract.Player
import kz.q19.audio.player.PlayerContract.PlayerCallback
import kz.q19.domain.error.BaseException
import kz.q19.domain.error.ReadPermissionDeniedException
import java.io.IOException
import java.util.*

class AudioPlayer private constructor() : Player, OnPreparedListener {

    companion object {
        private const val TAG = "AudioPlayer"

        val instance: AudioPlayer
            get() = SingletonHolder.singleton
    }

    private object SingletonHolder {
        val singleton = AudioPlayer()
    }

    private val actionsListeners: MutableList<PlayerCallback> = ArrayList()
    private var mediaPlayer: MediaPlayer? = null
    private var timerProgress: Timer? = null
    private var pausePos: Long = 0
    private var dataSource: String? = null

    private var isPrepared = false

    override var isPause = false
        private set

    override var pauseTime: Long = 0
        private set

    override fun addPlayerCallback(callback: PlayerCallback?) {
        if (callback != null) {
            actionsListeners.add(callback)
        }
    }

    override fun removePlayerCallback(callback: PlayerCallback?): Boolean {
        return if (callback != null) {
            actionsListeners.remove(callback)
        } else false
    }

    override fun setData(data: String) {
        if (mediaPlayer != null && dataSource != null && dataSource == data) {
//            Do nothing
            Log.d(TAG, "Do nothing")
        } else {
            Log.d(TAG, "Set data")
            dataSource = data
            restartPlayer()
        }
    }

    private fun restartPlayer() {
        if (dataSource != null) {
            try {
                isPrepared = false
                mediaPlayer = MediaPlayer()
                mediaPlayer?.setDataSource(dataSource)
                mediaPlayer?.setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
//				mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            } catch (e: IOException) {
                Log.e(TAG, e.toString())
                if (e.message?.contains("Permission denied") == true) {
                    onError(ReadPermissionDeniedException())
                } else {
                    onError(AudioPlayerDataSourceException())
                }
            } catch (e: IllegalArgumentException) {
                Log.e(TAG, e.toString())
                if (e.message?.contains("Permission denied") == true) {
                    onError(ReadPermissionDeniedException())
                } else {
                    onError(AudioPlayerDataSourceException())
                }
            } catch (e: IllegalStateException) {
                Log.e(TAG, e.toString())
                if (e.message?.contains("Permission denied") == true) {
                    onError(ReadPermissionDeniedException())
                } else {
                    onError(AudioPlayerDataSourceException())
                }
            } catch (e: SecurityException) {
                Log.e(TAG, e.toString())
                if (e.message?.contains("Permission denied") == true) {
                    onError(ReadPermissionDeniedException())
                } else {
                    onError(AudioPlayerDataSourceException())
                }
            }
        }
    }

    override fun playOrPause() {
        try {
            if (mediaPlayer != null) {
                if (mediaPlayer?.isPlaying == true) {
                    pause()
                } else {
                    isPause = false
                    if (!isPrepared) {
                        try {
                            mediaPlayer?.setOnPreparedListener(this)
                            mediaPlayer?.prepareAsync()
                        } catch (ex: IllegalStateException) {
                            Log.e(TAG, ex.toString())
                            restartPlayer()
                            mediaPlayer?.setOnPreparedListener(this)
                            try {
                                mediaPlayer?.prepareAsync()
                            } catch (e: IllegalStateException) {
                                Log.e(TAG, e.toString())
                                restartPlayer()
                            }
                        }
                    } else {
                        mediaPlayer?.start()
                        mediaPlayer?.seekTo(pausePos.toInt())
                        onStartPlay()
                        mediaPlayer?.setOnCompletionListener {
                            stop()
                            onStopPlay()
                        }
                        timerProgress = Timer()
                        timerProgress?.schedule(object : TimerTask() {
                            override fun run() {
                                try {
                                    if (mediaPlayer != null && mediaPlayer?.isPlaying == true) {
                                        val curPos = mediaPlayer?.currentPosition ?: 0
                                        onPlayProgress(curPos.toLong())
                                    }
                                } catch (e: IllegalStateException) {
                                    Log.e(TAG, "$e. Player is not initialized!")
                                }
                            }
                        }, 0, Constants.VISUALIZATION_INTERVAL)
                    }
                    pausePos = 0
                }
            }
        } catch (e: IllegalStateException) {
            Log.e(TAG, "$e. Player is not initialized!")
        }
    }

    override fun onPrepared(mp: MediaPlayer) {
        if (mediaPlayer != mp) {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = mp
        }
        onPreparePlay()
        isPrepared = true
        mediaPlayer?.start()
        mediaPlayer?.seekTo(pauseTime.toInt())
        onStartPlay()
        mediaPlayer?.setOnCompletionListener {
            stop()
            onStopPlay()
        }
        timerProgress = Timer()
        timerProgress?.schedule(object : TimerTask() {
            override fun run() {
                try {
                    if (mediaPlayer?.isPlaying == false) {
                        val curPos = mediaPlayer?.currentPosition ?: 0
                        onPlayProgress(curPos.toLong())
                    }
                } catch (e: IllegalStateException) {
                    Log.e(TAG, "$e. Player is not initialized!")
                }
            }
        }, 0, Constants.VISUALIZATION_INTERVAL)
    }

    override fun seek(mills: Long) {
        pauseTime = mills
        if (isPause) {
            pausePos = mills
        }
        try {
            if (mediaPlayer != null && mediaPlayer?.isPlaying == true) {
                mediaPlayer?.seekTo(pauseTime.toInt())
                onSeek(pauseTime)
            }
        } catch (e: IllegalStateException) {
            Log.e(TAG, "$e. Player is not initialized!")
        }
    }

    override fun pause() {
        timerProgress?.cancel()
        timerProgress?.purge()

        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.pause()
            onPausePlay()
            pauseTime = (mediaPlayer?.currentPosition ?: 0).toLong()
            isPause = true
            pausePos = pauseTime
        }
    }

    override fun stop() {
        timerProgress?.cancel()
        timerProgress?.purge()

        if (mediaPlayer != null) {
            mediaPlayer?.stop()
            mediaPlayer?.setOnCompletionListener(null)
            isPrepared = false
            onStopPlay()
            mediaPlayer?.currentPosition
            pauseTime = 0
        }

        isPause = false
        pausePos = 0
    }

    override val isPlaying: Boolean
        get() {
            return try {
                mediaPlayer?.isPlaying == true
            } catch (e: IllegalStateException) {
                Log.e(TAG, "$e. Player is not initialized!")
                false
            }
        }

    override fun release() {
        stop()
        mediaPlayer?.release()
        mediaPlayer = null
        isPrepared = false
        isPause = false
        dataSource = null
        actionsListeners.clear()
    }

    private fun onPreparePlay() {
        if (actionsListeners.isNotEmpty()) {
            for (i in actionsListeners.indices) {
                actionsListeners[i].onPreparePlay()
            }
        }
    }

    private fun onStartPlay() {
        if (actionsListeners.isNotEmpty()) {
            for (i in actionsListeners.indices) {
                actionsListeners[i].onStartPlay()
            }
        }
    }

    private fun onPlayProgress(mills: Long) {
        if (actionsListeners.isNotEmpty()) {
            for (i in actionsListeners.indices) {
                actionsListeners[i].onPlayProgress(mills)
            }
        }
    }

    private fun onStopPlay() {
        if (actionsListeners.isNotEmpty()) {
            for (i in actionsListeners.indices.reversed()) {
                actionsListeners[i].onStopPlay()
            }
        }
    }

    private fun onPausePlay() {
        if (actionsListeners.isNotEmpty()) {
            for (i in actionsListeners.indices) {
                actionsListeners[i].onPausePlay()
            }
        }
    }

    private fun onSeek(mills: Long) {
        if (actionsListeners.isNotEmpty()) {
            for (i in actionsListeners.indices) {
                actionsListeners[i].onSeek(mills)
            }
        }
    }

    private fun onError(throwable: BaseException) {
        if (actionsListeners.isNotEmpty()) {
            for (i in actionsListeners.indices) {
                actionsListeners[i].onError(throwable)
            }
        }
    }

}