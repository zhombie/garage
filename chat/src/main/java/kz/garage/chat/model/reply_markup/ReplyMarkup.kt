package kz.garage.chat.model.reply_markup

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kz.garage.chat.model.reply_markup.button.Button

abstract class ReplyMarkup : Parcelable {

    abstract val rows: List<Row>

    @Parcelize
    data class Row constructor(
        val buttons: List<Button>
    ) : Collection<Button>, Parcelable {

        override val size: Int
            get() = buttons.size

        override fun isEmpty(): Boolean = buttons.isEmpty()

        override fun iterator(): Iterator<Button> = buttons.iterator()

        override operator fun contains(element: Button): Boolean =
            buttons.contains(element)

        override fun containsAll(elements: Collection<Button>): Boolean =
            buttons.containsAll(elements)

    }

    fun getColumnsCount(): Int =
        if (rows.isNullOrEmpty()) 0 else rows.maxOf { it.size }

    fun flatten(): List<Button> = rows.flatten()

}