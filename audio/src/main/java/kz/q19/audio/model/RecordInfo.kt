@file:Suppress("unused")

package kz.q19.audio.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RecordInfo constructor(
    val name: String,
    val format: String,
    val location: String,
    val duration: Long,
    val created: Long,
    val size: Long,
    val sampleRate: Int,
    val channelCount: Int,
    val bitrate: Int,
    val isInTrash: Boolean
) : Parcelable