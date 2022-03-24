@file:Suppress("NOTHING_TO_INLINE")

package kz.garage.chat.model.reply_markup

import kz.garage.chat.model.reply_markup.button.Button

inline fun MutableCollection<ReplyMarkup.Row>.addRow(elements: List<Button>): Boolean =
    add(ReplyMarkup.Row(elements))
