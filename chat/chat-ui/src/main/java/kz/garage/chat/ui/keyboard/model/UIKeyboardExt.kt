package kz.garage.chat.ui.keyboard.model

import kz.garage.chat.core.model.reply_markup.ReplyMarkup
import kz.garage.chat.core.model.reply_markup.button.Button
import kz.garage.chat.core.model.reply_markup.button.CallbackButton
import kz.garage.chat.core.model.reply_markup.button.TextButton
import kz.garage.chat.core.model.reply_markup.button.URLButton

internal fun ReplyMarkup.asUIKeyboard(): UIKeyboard =
    UIKeyboard(
        rows.map { rows ->
            rows.mapNotNull { button ->
                val output = button.asUIInlineButton()
//                Logger.debug("UIKeyboardExt", "asUIKeyboard() -> button: $button")
                output
            }
        }
    )

internal fun Button.asUIInlineButton(): UIKeyboard.UIInlineButton? =
    when (this) {
        is CallbackButton ->
            UIKeyboard.UIInlineButton(
                text = text,
                payload = "${CallbackButton::class.java.simpleName}:${payload}"
            )
//        is RateButton ->
//            UIKeyboard.UIInlineButton(
//                text = text,
//                payload = "${RateButton::class.java.simpleName}:${chatId}:${rating}"
//            )
        is TextButton ->
            UIKeyboard.UIInlineButton(text = text)
        is URLButton ->
            UIKeyboard.UIInlineButton(
                text = text,
                payload = "${URLButton::class.java.simpleName}:${url}"
            )
        else ->
            null
    }

internal fun UIKeyboard.UIInlineButton.asButton(): Button? =
    if (payload.isNullOrBlank()) {
        TextButton(text = text)
    } else {
        when {
            payload.startsWith(CallbackButton::class.java.simpleName) -> {
                val (_, payload) = payload.split(":")
                CallbackButton(text = text, payload = payload)
            }
//            payload.startsWith(RateButton::class.java.simpleName) -> {
//                val (_, chatId, rating) = payload.split(":")
//                RateButton(text = text, chatId = chatId.toLong(), rating = rating.toInt())
//            }
            payload.startsWith(URLButton::class.java.simpleName) -> {
                val (_, url) = payload.split(":")
                URLButton(text = text, url = url)
            }
            else -> null
        }
    }
