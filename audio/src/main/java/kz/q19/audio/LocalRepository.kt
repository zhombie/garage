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

import kz.q19.audio.model.Record
import java.io.IOException

interface LocalRepository {
    fun getRecord(id: Long): Record?

    fun findRecordByPath(path: String): Record?

    fun insertRecord(record: Record): Record?

    @Throws(IOException::class)
    fun insertEmptyFile(filePath: String): Record?

    fun updateRecord(record: Record): Boolean

    fun deleteRecord(id: Long): Boolean
}