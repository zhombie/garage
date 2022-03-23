package kz.garage.chat.model

import android.os.Parcelable

abstract class Entity : Parcelable {
    abstract val id: String
}