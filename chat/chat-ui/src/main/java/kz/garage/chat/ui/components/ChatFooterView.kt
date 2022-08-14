package kz.garage.chat.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.internal.TextWatcherAdapter
import com.google.android.material.textfield.TextInputLayout
import kz.garage.chat.ui.R

class ChatFooterView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0,
    @StyleRes defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private var attachmentSelectionButton: MaterialButton
    private val inputView: TextInputLayout
    private var messageSendButton: MaterialButton

    var isAttachmentSelectionButtonEnabled: Boolean = false
        private set

    var isMessageSendButtonEnabled: Boolean = false
        private set

    var callback:   Callback? = null

    private val messageMaxLength: Int

    init {
        val view = inflate(context, R.layout.chat_ui_view_chat_footer, this)

        attachmentSelectionButton = buildAttachmentSelectionButton()
        inputView = view.findViewById(R.id.inputView)
        messageSendButton = buildMessageSendButtonButton()
        messageSendButton = findViewById(R.id.sendMessageButton)
        attachmentSelectionButton = view.findViewById(R.id.mediaSelectionButton)

        attachmentSelectionButton.setOnClickListener { callback?.onSelectAttachment() }

        messageSendButton.setOnClickListener {
            if (isMessageSendButtonEnabled) {
                return@setOnClickListener
            } else {
                if (inputView.editText?.text?.toString().isNullOrBlank()){
                    return@setOnClickListener
                }else{
                    callback?.onSendMessage(
                        inputView.editText?.text?.toString() ?: return@setOnClickListener)
                    inputView?.editText?.text?.clear()
                }
            }
        }

        messageMaxLength = context.resources.getInteger(R.integer.chat_ui_message_max_length)
    }

    private fun buildAttachmentSelectionButton(): AttachmentSelectionButton {
        return AttachmentSelectionButton(context)
    }

    private fun buildMessageSendButtonButton(): MessageSendButton {
        return MessageSendButton(context)
    }

    fun clearInputViewText() {
        inputView.editText?.text?.clear()
    }

    fun enableSendMessageButton() {
        setSendMessageButtonEnabled(true)
    }

    fun disableSendMessageButton() {
        setSendMessageButtonEnabled(false)
    }

    fun setSendMessageButtonClickRestriction(isRestricted: Boolean) {
        isMessageSendButtonEnabled = isRestricted

        if (isRestricted) {
            messageSendButton.isEnabled = false
        } else {
            messageSendButton.isEnabled = (inputView.editText?.text?.length ?: 0) > 0
        }
    }

    private fun setSendMessageButtonEnabled(isEnabled: Boolean) {
        messageSendButton.isEnabled = if (isMessageSendButtonEnabled) {
            false
        } else {
            isEnabled
        }
    }

    fun enableMediaSelectionButton(): Boolean = setMediaSelectionButtonEnabled(true)

    fun disableMediaSelectionButton(): Boolean = setMediaSelectionButtonEnabled(false)

    private fun setMediaSelectionButtonEnabled(isEnabled: Boolean): Boolean {
        isAttachmentSelectionButtonEnabled = isEnabled
        return isAttachmentSelectionButtonEnabled == isEnabled
    }

    fun setOnTextChangedListener(
        callback: (s: CharSequence?, start: Int, before: Int, count: Int) -> Unit
    ) {
        inputView.editText?.addTextChangedListener(object : TextWatcherAdapter() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                callback(s, start, before, count)
            }
        })
    }

    fun setIMESendByEnter() {
        inputView.editText?.imeOptions = EditorInfo.IME_ACTION_DONE
        inputView.editText?.inputType =
            EditorInfo.TYPE_TEXT_FLAG_CAP_SENTENCES or EditorInfo.TYPE_TEXT_FLAG_MULTI_LINE
        inputView.editText?.setHorizontallyScrolling(false)
        inputView.editText?.maxLines = 5
        inputView.editText?.isSingleLine = false
    }

    fun setIMENewLineByEnter() {
        inputView.editText?.imeOptions = EditorInfo.IME_ACTION_NEXT
        inputView.editText?.inputType = EditorInfo.TYPE_CLASS_TEXT
        inputView.editText?.setHorizontallyScrolling(true)
        inputView.editText?.maxLines = 5
        inputView.editText?.isSingleLine = false
    }

    fun setOnInputViewActionListener(
        onActionIds: Array<Int>,
        onKeycode: Int,
        callback: (text: String) -> Unit
    ) {
        inputView.editText?.setOnEditorActionListener { _, actionId, event ->
            if (isMessageSendButtonEnabled) {
                return@setOnEditorActionListener false
            } else {
                if (onActionIds.any { it == actionId } || onKeycode == event.keyCode) {
                    val text = inputView.editText?.text?.toString()
                    if (text.isNullOrBlank()) {
                        return@setOnEditorActionListener false
                    } else {
                        callback(text)
                        return@setOnEditorActionListener true
                    }
                }
                return@setOnEditorActionListener false
            }
        }
    }

    fun resetOnInputViewActionDoneClick() {
        inputView.editText?.setOnEditorActionListener(null)
    }

    interface Callback {
        fun onSelectAttachment(): Boolean
        fun onSendMessage(text: String)
    }

}