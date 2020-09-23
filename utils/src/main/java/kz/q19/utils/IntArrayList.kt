/*
 * Copyright 2018 Dmitriy Ponomarenko
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     GitHub: https://github.com/Dimowner/AudioRecorder
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package kz.q19.utils

class IntArrayList {
    private var data = IntArray(100)
    private var size = 0

    fun add(value: Int) {
        if (data.size == size) {
            grow()
            add(value)
        }
        data[size] = value
        size++
    }

    operator fun get(index: Int): Int {
        return data[index]
    }

    fun getData(): IntArray {
        val array = IntArray(size)
        for (i in 0 until size) {
            array[i] = data[i]
        }
        return array
    }

    fun clear() {
        data = IntArray(100)
        size = 0
    }

    fun size(): Int {
        return size
    }

    private fun grow() {
        val backup = data
        data = IntArray(size * 2)
        for (i in backup.indices) {
            data[i] = backup[i]
        }
    }
}