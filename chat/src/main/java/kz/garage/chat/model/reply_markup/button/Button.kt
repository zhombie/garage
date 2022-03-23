package kz.garage.chat.model.reply_markup.button

import android.os.Parcelable

abstract class Button : Parcelable {
    abstract val id: String
}