/*
 * Copyright 2020 Dmitriy Ponomarenko
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

package kz.q19.audio

import android.content.Context
import android.media.MediaCodec
import android.media.MediaCodec.CodecException
import android.media.MediaExtractor
import android.media.MediaFormat
import android.util.Log
import kz.q19.audio.model.RecordInfo
import kz.q19.utils.IntArrayList
import kz.q19.utils.android.AndroidUtils
import kz.q19.utils.file.Extension
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.nio.ByteOrder
import java.util.*
import kotlin.math.max
import kotlin.math.sqrt

class AudioDecoder private constructor(private val context: Context) {

    companion object {
        private const val TAG = "AudioDecoder"

        private const val QUEUE_INPUT_BUFFER_EFFECTIVE = 1 // Most effective and fastest
        private const val QUEUE_INPUT_BUFFER_SIMPLE = 2 // Less effective and slower

        private const val TRASH_EXT = "del"

        fun decode(context: Context, fileName: String, decodeListener: DecodeListener) {
            try {
                val file = File(fileName)
                if (!file.exists()) {
                    throw FileNotFoundException(fileName)
                }
                val name = file.name.toLowerCase(Locale.getDefault())
                val components = name.split("\\.".toRegex()).toTypedArray()
                if (components.size < 2) {
                    throw IOException()
                }
                if (!Constants.SUPPORTED_EXT.contains(components[components.size - 1])) {
                    throw IOException()
                }
                val decoder = AudioDecoder(context)
                decoder.decodeFile(file, decodeListener, QUEUE_INPUT_BUFFER_EFFECTIVE)
            } catch (e: Exception) {
                decodeListener.onError(e)
            }
        }

        @Throws(OutOfMemoryError::class, IllegalStateException::class)
        fun readRecordInfo(inputFile: File): RecordInfo {
            var isInTrash = false
            return try {
                if (!inputFile.exists()) {
                    throw FileNotFoundException(inputFile.absolutePath)
                }
                val name = inputFile.name.toLowerCase(Locale.getDefault())
                val components = name.split("\\.".toRegex()).toTypedArray()
                if (components.size < 2) {
                    throw IOException()
                }
                isInTrash = TRASH_EXT.equals(components[components.size - 1], ignoreCase = true)
                if (!isInTrash && !Utils.isSupportedExtension(components[components.size - 1])) {
                    throw IOException()
                }
                val extractor = MediaExtractor()
                var format: MediaFormat? = null
                extractor.setDataSource(inputFile.path)
                val numTracks = extractor.trackCount
                // find and select the first audio track present in the file.
                var i = 0
                while (i < numTracks) {
                    format = extractor.getTrackFormat(i)
                    try {
                        if (format.getString(MediaFormat.KEY_MIME)?.startsWith("audio/") == true) {
                            extractor.selectTrack(i)
                            break
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, e.toString())
                    }
                    i++
                }
                if (i == numTracks || format == null) {
                    throw IOException("No audio track found in $inputFile")
                }
                val channelCount: Int = try {
                    format.getInteger(MediaFormat.KEY_CHANNEL_COUNT)
                } catch (e: Exception) {
                    Log.e(TAG, e.toString())
                    0
                }
                val sampleRate: Int = try {
                    format.getInteger(MediaFormat.KEY_SAMPLE_RATE)
                } catch (e: Exception) {
                    Log.e(TAG, e.toString())
                    0
                }
                val bitrate: Int = try {
                    format.getInteger(MediaFormat.KEY_BIT_RATE)
                } catch (e: Exception) {
                    Log.e(TAG, e.toString())
                    0
                }
                val duration: Long = try {
                    format.getLong(MediaFormat.KEY_DURATION)
                } catch (e: Exception) {
                    Log.e(TAG, e.toString())
                    0L
                }
                val mimeType: String? = try {
                    format.getString(MediaFormat.KEY_MIME)
                } catch (e: Exception) {
                    Log.e(TAG, e.toString())
                    null
                }
                RecordInfo(
                    name = Utils.removeFileExtension(inputFile.name),
                    format = readFileFormat(inputFile, mimeType),
                    duration = duration,
                    size = inputFile.length(),
                    location = inputFile.absolutePath,
                    created = inputFile.lastModified(),
                    sampleRate = sampleRate,
                    channelCount = channelCount,
                    bitrate = bitrate,
                    isInTrash = isInTrash
                )
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
                RecordInfo(
                    name = Utils.removeFileExtension(inputFile.name),
                    format = "",
                    duration = 0,
                    size = inputFile.length(),
                    location = inputFile.absolutePath,
                    created = inputFile.lastModified(),
                    sampleRate = 0,
                    channelCount = 0,
                    bitrate = 0,
                    isInTrash = isInTrash
                )
            }
        }

        private fun readFileFormat(file: File, mime: String?): String {
            val name = file.name.toLowerCase(Locale.getDefault())

            fun contains(extension: String, other: String): Boolean =
                name.contains(extension) || (mime?.contains("audio") == true && mime.contains(other))

            val extension = when {
                contains(Extension.M4A.value, "m4a") -> Extension.M4A
                contains(Extension.WAV.value, "raw") -> Extension.WAV
                contains(Extension.THREE_GP.value, "3gpp") -> Extension.THREE_GP
                name.contains(Extension.THREE_GPP.value) -> Extension.THREE_GPP
                contains(Extension.MP3.value, "mpeg") -> Extension.MP3
                name.contains(Extension.AMR.value) -> Extension.AMR
                name.contains(Extension.AAC.value) -> Extension.AAC
                name.contains(Extension.MP4.value) -> Extension.MP4
                name.contains(Extension.OGG.value) -> Extension.OGG
                contains(Extension.FLAC.value, "flac") -> Extension.FLAC
                else -> null
            }

            return extension?.value ?: ""
        }
    }

    private var dpPerSec: Float = Constants.SHORT_RECORD_DP_PER_SECOND.toFloat()
    private var sampleRate = 0
    private var channelCount = 0
    private var oneFrameAmps: IntArray = intArrayOf()
    private var frameIndex = 0
    private var duration: Long = 0
    private var gains: IntArrayList? = null

    interface DecodeListener {
        fun onStartDecode(duration: Long, channelsCount: Int, sampleRate: Int)
        fun onFinishDecode(data: IntArray, duration: Long)
        fun onError(exception: Exception?)
    }

    private fun calculateSamplesPerFrame(): Int {
        return (sampleRate / dpPerSec).toInt()
    }

    @Throws(IOException::class, OutOfMemoryError::class, IllegalStateException::class)
    private fun decodeFile(
        inputFile: File,
        decodeListener: DecodeListener,
        queueType: Int
    ) {
        gains = IntArrayList()
        val extractor = MediaExtractor()
        var format: MediaFormat? = null
        extractor.setDataSource(inputFile.path)
        val numTracks = extractor.trackCount
        // find and select the first audio track present in the file.
        var i = 0
        while (i < numTracks) {
            format = extractor.getTrackFormat(i)
            if (format.getString(MediaFormat.KEY_MIME)?.startsWith("audio/") == true) {
                extractor.selectTrack(i)
                break
            }
            i++
        }
        if (i == numTracks || format == null) {
            throw IOException("No audio track found in $inputFile")
        }
        channelCount = format.getInteger(MediaFormat.KEY_CHANNEL_COUNT)
        sampleRate = format.getInteger(MediaFormat.KEY_SAMPLE_RATE)
        duration = format.getLong(MediaFormat.KEY_DURATION)

        // TODO: Make waveform independent from dpPerSec!!!
        this.dpPerSec = getDpPerSecond(duration.toFloat() / 1000000f)
        oneFrameAmps = IntArray(calculateSamplesPerFrame() * channelCount)
        val mimeType = format.getString(MediaFormat.KEY_MIME)
        // Start decoding
        val mediaCodec = MediaCodec.createDecoderByType(mimeType ?: return)
        decodeListener.onStartDecode(duration, channelCount, sampleRate)
        mediaCodec.setCallback(object : MediaCodec.Callback() {
            private var mOutputEOS = false
            private var mInputEOS = false
            override fun onError(codec: MediaCodec, exception: CodecException) {
                Log.e(TAG, exception.toString())
                if (queueType == QUEUE_INPUT_BUFFER_EFFECTIVE) {
                    try {
                        val decoder = AudioDecoder(context)
                        decoder.decodeFile(inputFile, decodeListener, QUEUE_INPUT_BUFFER_SIMPLE)
                    } catch (e: IllegalStateException) {
                        decodeListener.onError(exception)
                    } catch (e: IOException) {
                        decodeListener.onError(exception)
                    } catch (e: OutOfMemoryError) {
                        decodeListener.onError(exception)
                    }
                } else {
                    decodeListener.onError(exception)
                }
            }

            override fun onOutputFormatChanged(codec: MediaCodec, format: MediaFormat) {}

            override fun onInputBufferAvailable(codec: MediaCodec, index: Int) {
                if (mOutputEOS or mInputEOS) return
                try {
                    val inputBuffer = codec.getInputBuffer(index) ?: return
                    var sampleTime: Long = 0
                    var result: Int
                    if (queueType == QUEUE_INPUT_BUFFER_EFFECTIVE) {
                        var total = 0
                        var advanced = false
                        var maxResult = 0
                        do {
                            result = extractor.readSampleData(inputBuffer, total)
                            if (result >= 0) {
                                total += result
                                sampleTime = extractor.sampleTime
                                advanced = extractor.advance()
                                maxResult = max(maxResult, result)
                            }
                        } while (result >= 0 && total < maxResult * 5 && advanced && inputBuffer.capacity() - inputBuffer.limit() > maxResult * 3) // 3 it is just for insurance. When remove it crash happens. it is ok if replace it by 2 number.
                        if (advanced) {
                            codec.queueInputBuffer(index, 0, total, sampleTime, 0)
                        } else {
                            codec.queueInputBuffer(
                                index,
                                0,
                                0,
                                -1,
                                MediaCodec.BUFFER_FLAG_END_OF_STREAM
                            )
                            mInputEOS = true
                        }
                    } else {
                        //If QUEUE_INPUT_BUFFER_EFFECTIVE failed then trying this way.
                        result = extractor.readSampleData(inputBuffer, 0)
                        if (result >= 0) {
                            sampleTime = extractor.sampleTime
                            codec.queueInputBuffer(index, 0, result, sampleTime, 0)
                            extractor.advance()
                        } else {
                            codec.queueInputBuffer(
                                index,
                                0,
                                0,
                                -1,
                                MediaCodec.BUFFER_FLAG_END_OF_STREAM
                            )
                            mInputEOS = true
                        }
                    }
                } catch (e: IllegalStateException) {
                    Log.e(TAG, e.toString())
                } catch (e: IllegalArgumentException) {
                    Log.e(TAG, e.toString())
                }
            }

            override fun onOutputBufferAvailable(
                codec: MediaCodec,
                index: Int,
                info: MediaCodec.BufferInfo,
            ) {
                try {
                    val outputBuffer = codec.getOutputBuffer(index)
                    if (outputBuffer != null) {
                        outputBuffer.rewind()
                        outputBuffer.order(ByteOrder.LITTLE_ENDIAN)
                        while (outputBuffer.remaining() > 0) {
                            oneFrameAmps[frameIndex] = outputBuffer.short.toInt()
                            frameIndex++
                            if (frameIndex >= oneFrameAmps.size - 1) {
                                var gain: Int
                                var value: Int
                                gain = -1
                                var j = 0
                                while (j < oneFrameAmps.size) {
                                    value = 0
                                    for (k in 0 until channelCount) {
                                        value += oneFrameAmps[j + k]
                                    }
                                    value /= channelCount
                                    if (gain < value) {
                                        gain = value
                                    }
                                    j += channelCount
                                }
                                gains?.add(sqrt(gain.toDouble()).toInt())
                                frameIndex = 0
                            }
                        }
                    }
                    mOutputEOS =
                        mOutputEOS or (info.flags and MediaCodec.BUFFER_FLAG_END_OF_STREAM != 0)
                    codec.releaseOutputBuffer(index, false)
                    if (mOutputEOS) {
                        decodeListener.onFinishDecode(gains?.getData() ?: intArrayOf(), duration)
                        codec.stop()
                        codec.release()
                        extractor.release()
                    }
                } catch (e: IllegalStateException) {
                    Log.e(TAG, e.toString())
                }
            }
        })
        mediaCodec.configure(format, null, null, 0)
        mediaCodec.start()
    }

    /**
     * Calculate density pixels per second for record duration.
     * Used for visualisation waveform in view.
     * @param durationSec record duration in seconds
     */
    private fun getDpPerSecond(durationSec: Float): Float {
        return if (durationSec > Constants.LONG_RECORD_THRESHOLD_SECONDS) {
            Constants.WAVEFORM_WIDTH * AndroidUtils.pxToDp(AndroidUtils.getScreenWidth(context)) / durationSec
        } else {
            Constants.SHORT_RECORD_DP_PER_SECOND.toFloat()
        }
    }

}