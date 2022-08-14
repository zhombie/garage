package kz.garage.chat.ui.components.platforrm

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.util.TypedValue
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kz.garage.chat.ui.R

sealed interface ThemedAlertDialog {

    open class Builder constructor(
        context: Context
    ) : MaterialAlertDialogBuilder(context, R.style.ChatUi_AlertDialogTheme)

    fun build(
        negative: (() -> Unit)? = null,
        positive: (() -> Unit)? = null,
    ): MaterialAlertDialogBuilder

    class CallHangupConfirmation constructor(
        private val context: Context
    ) : ThemedAlertDialog {

        override fun build(
            negative: (() -> Unit)?,
            positive: (() -> Unit)?
        ): MaterialAlertDialogBuilder {
            return Builder(context)
                .setTitle(R.string.chat_ui_attention)
                .setMessage(R.string.chat_ui_user_confirmation_end_call)
                .setNegativeButton(R.string.chat_ui_no) { dialog, _ ->
                    dialog.dismiss()
                    negative?.invoke()
                }
                .setPositiveButton(R.string.chat_ui_yes) { dialog, _ ->
                    dialog.dismiss()
                    positive?.invoke()
                }
        }

    }

    class OpenLinkConfirmationConfirmation constructor(
        private val context: Context,
        private val url: String
    ) : ThemedAlertDialog {

        override fun build(
            negative: (() -> Unit)?,
            positive: (() -> Unit)?
        ): MaterialAlertDialogBuilder {
            val messageView = FrameLayout(context)

            val textView = TextView(context)

            textView.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.chat_ui_very_dark_gray
                )
            )

            val colorStateList = ColorStateList(
                arrayOf(
                    intArrayOf(android.R.attr.state_pressed),
                    intArrayOf(android.R.attr.state_selected),
                    intArrayOf()
                ),
                intArrayOf(
                    ContextCompat.getColor(context, R.color.chat_ui_very_light_blue),
                    ContextCompat.getColor(context, R.color.chat_ui_very_light_blue),
                    ContextCompat.getColor(context, R.color.chat_ui_light_blue)
                )
            )

            textView.highlightColor = Color.TRANSPARENT

            textView.setLinkTextColor(colorStateList)

            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)

            textView.linksClickable = true

            textView.layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                val margin =
                    context.resources.getDimensionPixelOffset(R.dimen.chat_ui_dialog_horizontal_padding)
                setMargins(margin, margin, margin, margin)
            }

            val spannable =
                SpannableString(context.getString(R.string.chat_ui_go_to_link_confirmation, url))
            textView.autoLinkMask = Linkify.WEB_URLS
            textView.text = spannable
            textView.movementMethod = LinkMovementMethod.getInstance()

            messageView.addView(textView)

            return Builder(context)
                .setTitle(R.string.chat_ui_open_link)
                .setView(messageView)
                .setNegativeButton(R.string.chat_ui_no) { dialog, _ ->
                    dialog.dismiss()
                    negative?.invoke()
                }
                .setPositiveButton(R.string.chat_ui_yes) { dialog, _ ->
                    dialog.dismiss()
                    positive?.invoke()
                }
        }

    }

    class FakeLocationUsage constructor(
        private val context: Context
    ) : ThemedAlertDialog {

        override fun build(
            negative: (() -> Unit)?,
            positive: (() -> Unit)?
        ): MaterialAlertDialogBuilder {
            return Builder(context)
                .setTitle(R.string.chat_ui_attention)
                .setMessage(R.string.chat_ui_error_mocked_location)
                .setPositiveButton(R.string.chat_ui_ok) { dialog, _ ->
                    dialog.dismiss()
                    positive?.invoke()
                }
        }

    }

}