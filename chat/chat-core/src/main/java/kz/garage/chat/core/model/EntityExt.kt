package kz.garage.chat.core.model

import android.os.Build
import android.text.Spanned
import android.text.format.DateFormat
import kz.garage.chat.core.HtmlCompat
import java.util.*

val Entity.body: String?
    get() {
        if (this is Message) return body
        if (this is Notification) return body
        return null
    }

val Entity.asHTMLText: Spanned?
    get() {
        val body = body
        return if (body.isNullOrBlank()) {
            null
        } else {
            HtmlCompat.fromHtml(body)
        }
    }

fun Entity.getDisplayCreatedAt(
    inFormat: String = "HH:MM",
    locale: Locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Locale.getDefault(Locale.Category.FORMAT)
    } else {
        Locale.getDefault()
    },
    timeZone: TimeZone = TimeZone.getDefault(),
    convertToMillis: Boolean = true
): String? {
    val createdAt = createdAt ?: return null
    if (createdAt == 0L) return null
    return DateFormat.format(
        inFormat,
        Calendar.getInstance(timeZone, locale).apply {
            timeInMillis = if (convertToMillis) createdAt * 1000L else createdAt
        }
    ).toString()
}