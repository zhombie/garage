package kz.garage.multimedia.store.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Resolution constructor(
    val width: Int,
    val height: Int,
) : Parcelable