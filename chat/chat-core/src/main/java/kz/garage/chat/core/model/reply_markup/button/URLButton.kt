package kz.garage.chat.core.model.reply_markup.button

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
open class URLButton constructor(
    override val id: String,
    override val text: String,
    val url: String
) : TextButton(id = id, text = text), Parcelable {

    constructor(
        text: String,
        url: String
    ) : this(id = generateId(), text = text, url = url)

    constructor(
        text: String,
        uri: Uri
    ) : this(text = text, url = uri.toString())

    constructor(
        id: String,
        text: String,
        uri: Uri
    ) : this(id = id, text = text, url = uri.toString())

}