package kz.garage.chat.core.model.reply_markup.button

import android.os.Parcelable
import kotlin.random.Random

abstract class Button : Parcelable {

    companion object {
        fun generateId(): String =
            (System.currentTimeMillis() + Random.nextLong(Long.MIN_VALUE, Long.MAX_VALUE)).toString()
    }

    abstract val id: String

}