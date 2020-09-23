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
import kz.q19.domain.error.CannotCreateFileException
import java.io.File

interface FileRepository {
    @Throws(CannotCreateFileException::class)
    fun provideRecordFile(): File?

    @Throws(CannotCreateFileException::class)
    fun provideRecordFile(name: String): File?

    val recordingDir: File?

    fun deleteRecordFile(path: String?): Boolean

    @Throws(IllegalArgumentException::class)
    fun hasAvailableSpace(): Boolean
}