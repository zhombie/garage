package kz.garage.chat.model.reply_markup

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Keyboard constructor(
    override val rows: List<Row>
) : ReplyMarkup(), Parcelable