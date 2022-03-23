@file:Suppress("NOTHING_TO_INLINE")

package kz.garage.chat.model.reply_markup

import kz.garage.chat.model.reply_markup.button.Button

inline fun MutableList<ReplyMarkup.Row>.addRow(elements: MutableList<Button>): Boolean =
    add(ReplyMarkup.Row(elements))
