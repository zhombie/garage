package kz.garage.chat.model.reply_markup

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class InlineReplyMarkup constructor(
    override val rows: List<Row>
) : ReplyMarkup(), Parcelable