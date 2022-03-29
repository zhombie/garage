@file:Suppress("NOTHING_TO_INLINE")

package kz.garage.file.extension

inline fun Extension.isImage(): Boolean = isInstance(Extensions.IMAGE)

inline fun Extension.isVideo(): Boolean = isInstance(Extensions.VIDEO)

inline fun Extension.isAudio(): Boolean = isInstance(Extensions.AUDIO)

inline fun Extension.isDocument(): Boolean = isInstance(Extensions.DOCUMENT)

inline fun Extension.isInstance(extensions: Extensions): Boolean = this in extensions
