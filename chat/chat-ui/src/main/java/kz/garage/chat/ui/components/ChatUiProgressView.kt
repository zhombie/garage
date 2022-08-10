package kz.garage.chat.ui.components

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.IntDef
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import androidx.core.view.ViewCompat
import androidx.core.view.contains
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textview.MaterialTextView
import kz.garage.chat.ui.R
import kotlin.math.roundToInt

internal class ChatUiProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val rootLayout: LinearLayout
    private val centerLayout: LinearLayout
    private var progressIndicator: CircularProgressIndicator? = null
    private var textView: MaterialTextView? = null
    private val cancelButton: MaterialButton

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(Type.INDETERMINATE, Type.DETERMINATE)
    annotation class Type {
        companion object {
            const val INDETERMINATE = 0
            const val DETERMINATE = 1
        }
    }

    private var isCancelable: Boolean = false
        set(value) {
            field = value

            with(cancelButton) {
                if (value) {
                    if (visibility != View.VISIBLE) {
                        visibility = View.VISIBLE
                    }
                } else {
                    if (visibility != View.GONE) {
                        visibility = View.GONE
                    }
                }
            }
        }

    init {
        val view = inflate(context, R.layout.chat_ui_view_progress, this)

        rootLayout = view.findViewById(R.id.rootLayout)
        centerLayout = view.findViewById(R.id.centerLayout)
        cancelButton = view.findViewById(R.id.cancelButton)

        context.obtainStyledAttributes(attrs, R.styleable.ChatUiProgressView).use { typedArray ->
            rootLayout.setBackgroundColor(
                typedArray.getColor(
                    R.styleable.ChatUiProgressView_chat_ui_background_color,
                    ContextCompat.getColor(context, R.color.chat_ui_black_alpha)
                )
            )

            progressIndicator = createProgressIndicator(
                R.style.Widget_MaterialComponents_CircularProgressIndicator
            )

            when (typedArray.getInt(
                R.styleable.ChatUiProgressView_chat_ui_type,
                Type.INDETERMINATE
            )) {
                Type.INDETERMINATE -> setIndeterminate()
                Type.DETERMINATE -> setDeterminate()
            }

            centerLayout.addView(progressIndicator)

            isCancelable = typedArray.getBoolean(
                R.styleable.ChatUiProgressView_chat_ui_is_cancelable,
                false
            )
        }

        hide()
    }

    fun show() {
        if (visibility != View.VISIBLE) {
            visibility = View.VISIBLE
        }
    }

    fun hide() {
        if (visibility != View.GONE) {
            visibility = View.GONE
        }
    }

    fun setText(text: String) {
        if (textView == null) {
            textView = createTextView()

            if (requireNotNull(textView) !in centerLayout) {
                centerLayout.addView(textView)
            }
        }

        textView?.text = text
    }

    fun setCancelable(isCancelable: Boolean): Boolean {
        this.isCancelable = isCancelable
        return this.isCancelable == isCancelable
    }

    fun setOnCancelClickListener(listener: OnClickListener?) {
        if (listener == null) {
            cancelButton.setOnClickListener(listener)
        } else {
            if (isCancelable) {
                if (!cancelButton.hasOnClickListeners()) {
                    cancelButton.setOnClickListener(listener)
                }
            }
        }
    }

    private fun createProgressIndicator(style: Int): CircularProgressIndicator =
        CircularProgressIndicator(context, null, style).apply {
            id = ViewCompat.generateViewId()

            layoutParams = MarginLayoutParams(
                context.resources.getDimensionPixelOffset(R.dimen.chat_ui_progress_circle_size),
                context.resources.getDimensionPixelOffset(R.dimen.chat_ui_progress_circle_size)
            )

            indicatorSize =
                context.resources.getDimensionPixelOffset(R.dimen.chat_ui_progress_circle_size)
            trackThickness =
                context.resources.getDimensionPixelOffset(R.dimen.chat_ui_progress_track_thickness)

            setIndicatorColor(ContextCompat.getColor(context, R.color.chat_ui_white))

            trackColor = ContextCompat.getColor(context, R.color.chat_ui_transparent)
        }

    private fun createTextView(): MaterialTextView =
        MaterialTextView(context, null, android.R.attr.textViewStyle, 0).apply {
            id = ViewCompat.generateViewId()

            layoutParams = LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            )

            ellipsize = TextUtils.TruncateAt.MIDDLE

            gravity = Gravity.CENTER

            maxWidth =
                context.resources.getDimensionPixelOffset(R.dimen.chat_ui_progress_text_max_width)

            maxLines = 2

            setTextColor(ContextCompat.getColor(context, R.color.chat_ui_white))
        }


    fun setDeterminate() = with(progressIndicator) {
        if (this == null) return@with

        if (isIndeterminate) {
            isIndeterminate = false
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            if (min != 0) {
                min = 0
            }
        }

        if (max != 100) {
            max = 100
        }
    }

    fun setIndeterminate() = with(progressIndicator) {
        if (this == null) return@with

        try {
            if (!isIndeterminate) {
                isIndeterminate = true
            }
        } catch (_: IllegalStateException) {
        }

        if (visibility != View.VISIBLE) {
            visibility = View.VISIBLE
        }
    }

    fun setProgress(progress: Float) {
        progressIndicator?.progress = progress.roundToInt()
    }

}