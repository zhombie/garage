/*
 * Copyright 2018 Dmitriy Ponomarenko
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kz.q19.audio

import android.content.Context
import android.util.Log
import kz.q19.audio.model.Record
import java.io.File
import java.io.IOException
import java.util.*

class LocalRepositoryImpl private constructor(
    private val context: Context
) : LocalRepository {

    companion object {
        private const val TAG = "LocalRepositoryImpl"

        @Volatile
        private var instance: LocalRepositoryImpl? = null

        fun getInstance(context: Context): LocalRepositoryImpl {
            if (instance == null) {
                synchronized(LocalRepositoryImpl::class.java) {
                    if (instance == null) {
                        instance = LocalRepositoryImpl(context)
                    }
                }
            }
            return instance!!
        }
    }

    private val records: MutableList<Record> = mutableListOf()

    override fun getRecord(id: Long): Record? {
        return records.find { it.id == id }
    }

    override fun findRecordByPath(path: String): Record? {
        return records.find {
            var targetPath = path
            if (targetPath.contains("'")) {
                targetPath = targetPath.replace("'", "''")
            }
            it.path == targetPath
        }
    }

    override fun insertRecord(record: Record): Record? {
        val isAdded = records.add(record)
        return if (isAdded) record else null
    }

    @Throws(IOException::class)
    override fun insertEmptyFile(filePath: String): Record? {
        if (filePath.isNotEmpty()) {
            val file = File(filePath)
            val record = Record(
                id = Record.NO_ID,
                name = Utils.removeFileExtension(file.name),
                duration = 0,  // mills
                created = file.lastModified(),
                added = Date().time,
                removed = Long.MAX_VALUE,
                path = filePath,
                format = Constants.DEFAULT_RECORDING_FORMAT,
                size = 0,
                sampleRate = Constants.RECORD_SAMPLE_RATE_44100,
                channelCount = Constants.RECORD_AUDIO_STEREO,
                bitrate = Constants.RECORD_ENCODING_BITRATE_128000,
                waveformProcessed = false,
                amps = IntArray(Utils.getLongWaveformSampleCount(context))
            )
            val outputRecord = insertRecord(record)
            if (outputRecord != null) {
                return outputRecord
            } else {
                Log.e(TAG, "Failed to insert record into local database!")
            }
        } else {
            Log.e(TAG, "Unable to read sound file by specified path!")
            throw IOException("Unable to read sound file by specified path!")
        }
        return null
    }

    override fun updateRecord(record: Record): Boolean {
        val index = records.indexOfFirst { it.id == record.id }
        if (index > -1) {
            records[index] = record
            return true
        }
        return false
    }

    override fun deleteRecord(id: Long): Boolean {
        var removed = false
        val each = records.iterator()
        while (each.hasNext()) {
            if (each.next().id == id) {
                each.remove()
                removed = true
            }
        }
        return removed
    }

}