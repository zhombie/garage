package kz.garage.chat.core.model.reply_markup

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kz.garage.chat.core.model.reply_markup.button.Button

@Parcelize
data class InlineReplyMarkup constructor(
    override val rows: List<Row>
) : ReplyMarkup(), Parcelable {

    constructor(rows: Collection<List<Button>>) : this(rows.map { Row(it) })

}