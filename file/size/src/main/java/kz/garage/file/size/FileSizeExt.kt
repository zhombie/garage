@file:Suppress("NOTHING_TO_INLINE")

package kz.garage.file.size

internal fun fileSizeOfBytes(normalBytes: Long): FileSize =
    FileSize((normalBytes shl 1) + 1)


inline operator fun Int.times(fileSize: FileSize): FileSize = fileSize * this

inline operator fun Double.times(fileSize: FileSize): FileSize = fileSize * this
