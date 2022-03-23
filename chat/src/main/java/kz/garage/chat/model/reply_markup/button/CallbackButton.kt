package kz.garage.chat.model.reply_markup.button

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
open class CallbackButton constructor(
    override val id: String,
    override val text: String,
    val payload: String? = null
) : TextButton(id = id, text = text), Parcelable