package kz.garage.chat

import android.os.Build
import android.text.Html
import android.text.Spanned

internal object HtmlCompat {

    fun fromHtml(source: String, flags: Int? = null): Spanned =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(source, flags ?: Html.FROM_HTML_MODE_COMPACT)
        } else {
            @Suppress("DEPRECATION")
            Html.fromHtml(source)
        }

}