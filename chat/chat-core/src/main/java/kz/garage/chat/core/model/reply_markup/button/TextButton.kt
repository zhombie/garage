package kz.garage.chat.core.model.reply_markup.button

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
open class TextButton constructor(
    override val id: String,
    open val text: String
) : Button(), Parcelable {

    constructor(text: String) : this(id = generateId(), text = text)

}