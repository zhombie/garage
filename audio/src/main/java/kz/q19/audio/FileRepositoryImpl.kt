/*
 * Copyright 2018 Dmitriy Ponomarenko
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
import android.util.Log
import kz.q19.audio.Utils.addExtension
import kz.q19.audio.Utils.createFile
import kz.q19.audio.Utils.generateRecordNameMills
import kz.q19.audio.Utils.getAvailableInternalMemorySize
import kz.q19.audio.Utils.getPrivateRecordsDir
import kz.q19.domain.error.CannotCreateFileException
import kz.q19.utils.file.Extension
import kz.q19.utils.file.FileUtils.deleteFile
import java.io.File
import java.io.FileNotFoundException

class FileRepositoryImpl private constructor(
    private val context: Context
) : FileRepository {

    companion object {
        private const val TAG = "FileRepositoryImpl"

        @Volatile
        private var instance: FileRepositoryImpl? = null

        fun getInstance(context: Context): FileRepositoryImpl? {
            if (instance == null) {
                synchronized(FileRepositoryImpl::class.java) {
                    if (instance == null) {
                        instance = FileRepositoryImpl(context)
                    }
                }
            }
            return instance
        }
    }

    init {
        updateRecordingDir()
    }

    override var recordingDir: File? = null
        private set

    private fun updateRecordingDir() {
        try {
            recordingDir = getPrivateRecordsDir(context)
        } catch (e: FileNotFoundException) {
            Log.e(TAG, e.toString())
        }
    }

    @Throws(CannotCreateFileException::class)
    override fun provideRecordFile(): File? {
        val recordName = generateRecordNameMills()
        val recordFile = createFile(recordingDir, addExtension(recordName, Extension.M4A.value))
        if (recordFile != null) {
            return recordFile
        }
        throw CannotCreateFileException()
    }

    @Throws(CannotCreateFileException::class)
    override fun provideRecordFile(name: String): File? {
        val recordFile = createFile(recordingDir, name)
        if (recordFile != null) {
            return recordFile
        }
        throw CannotCreateFileException()
    }

    override fun deleteRecordFile(path: String?): Boolean {
        return if (path != null) {
            deleteFile(File(path))
        } else false
    }

    @Throws(IllegalArgumentException::class)
    override fun hasAvailableSpace(): Boolean {
        val space = getAvailableInternalMemorySize(context)
        val time = spaceToTimeSecs(space)
        return time > Constants.MIN_REMAIN_RECORDING_TIME
    }

    private fun spaceToTimeSecs(spaceBytes: Long): Long {
        return 1000 * (spaceBytes / (Constants.RECORD_ENCODING_BITRATE_128000 / 8))
    }

}