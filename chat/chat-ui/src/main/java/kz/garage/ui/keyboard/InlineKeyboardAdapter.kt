package kz.gov.mia.sos.widget.ui.presentation.common.keyboard

import android.os.Bundle
import android.view.View
import com.google.android.material.button.MaterialButton
import kz.garage.recyclerview.adapter.BaseAdapter
import kz.garage.recyclerview.adapter.viewholder.BaseViewHolder
import kz.garage.recyclerview.adapter.viewholder.view.bind
import kz.gov.mia.sos.widget.R
import kz.gov.mia.sos.widget.ui.model.UIKeyboard

internal class InlineKeyboardAdapter constructor(
    private val callback: (inlineButton: UIKeyboard.UIInlineButton) -> Unit
) : BaseAdapter<UIKeyboard.UIInlineButton>(
    layoutId = R.layout.sos_widget_cell_inline_button
) {

    private object Payload {
        const val ACTIVE_BUTTON_POSITION = "active_button_position"
        const val INACTIVE_BUTTON_POSITION = "inactive_button_position"
    }

    fun setActiveButton(inlineButton: UIKeyboard.UIInlineButton?) {
        getData().forEach {
            it.isPressed = it.text == inlineButton?.text && it.payload == inlineButton.payload
        }

        notifyDataSetChanged()
    }

    fun unsetActiveButton() {
        getData().forEach {
            it.isPressed = false
        }

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(view: View): BaseViewHolder<UIKeyboard.UIInlineButton> =
        ViewHolder(view)

    override fun onBindViewHolder(
        holder: BaseViewHolder<UIKeyboard.UIInlineButton>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNullOrEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            payloads.forEach { payload ->
                if (payload is Bundle) {
//                    Logger.debug(TAG, "payload: $payload")

                    val inactiveButtonPosition = payload.getInt(Payload.INACTIVE_BUTTON_POSITION, -1)
                    val activeButtonPosition = payload.getInt(Payload.ACTIVE_BUTTON_POSITION, -1)
                    if (holder is ViewHolder) {
                        if (inactiveButtonPosition > -1) {
                            holder.setUnselectedState()
                        }
                        if (activeButtonPosition > -1) {
                            holder.setSelectedState()
                        }
                    }
                }
            }
        }
    }

    private inner class ViewHolder constructor(
        view: View
    ) : BaseViewHolder<UIKeyboard.UIInlineButton>(view) {
        private val button by bind<MaterialButton>(R.id.button)

        override fun onBind(item: UIKeyboard.UIInlineButton, position: Int) {
            super.onBind(item, position)

            if (item.isPressed) {
                setSelectedState()
            } else {
                setUnselectedState()
            }

            button.text = item.text

            button.setOnClickListener {
                item.isPressed = !item.isPressed
                callback.invoke(item)
            }
        }

        fun setSelectedState() {
            val backgroundTintList = getColorStateListCompat(R.color.sos_widget_button_bg_light_blue_to_gray)
            val textColorStateList = getColorStateListCompat(R.color.sos_widget_button_text_white_to_light_black)

            if (button.backgroundTintList != backgroundTintList) {
                button.backgroundTintList = backgroundTintList
            }

            if (button.textColors != textColorStateList) {
                button.setTextColor(textColorStateList)
            }
        }

        fun setUnselectedState() {
            val backgroundTintList = getColorStateListCompat(R.color.sos_widget_button_bg_gray_to_light_blue)
            val textColorStateList = getColorStateListCompat(R.color.sos_widget_button_text_light_black_to_white)

            if (button.backgroundTintList != backgroundTintList) {
                button.backgroundTintList = backgroundTintList
            }

            if (button.textColors != textColorStateList) {
                button.setTextColor(textColorStateList)
            }
        }
    }

}
