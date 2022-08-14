package kz.garage.chat.ui.components

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IntDef
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import androidx.core.view.isVisible
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textview.MaterialTextView
import kz.garage.chat.ui.R

internal class ChatUiMessageContentView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : RelativeLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val rootLayout: RelativeLayout
    private val indicatorLayout: FrameLayout
    private val iconView: ShapeableImageView
    private val progressIndicator: CircularProgressIndicator
    private val titleView: MaterialTextView
    private val subtitleView: MaterialTextView
    private val descriptionView: MaterialTextView

    init {
        val view = inflate(context, R.layout.chat_ui_view_content, this)

        rootLayout = view.findViewById(R.id.rootLayout)
        indicatorLayout = view.findViewById(R.id.indicatorLayout)
        iconView = view.findViewById(R.id.iconView)
        progressIndicator = view.findViewById(R.id.progressIndicator)
        titleView = view.findViewById(R.id.titleView)
        subtitleView = view.findViewById(R.id.subtitleView)
        descriptionView = view.findViewById(R.id.descriptionView)

        context.obtainStyledAttributes(attrs, R.styleable.ChatUiMessageContentView).use {
            when (it.getInt(R.styleable.ChatUiMessageContentView_chat_ui_theme_mode, -1)) {
                Theme.DARK -> {
                    background = it.getDrawable(R.styleable.ChatUiMessageContentView_chat_ui_background)
                        ?: Themes.getDrawable(context, Themes.Dark.background)
                    indicatorBackgroundColor =
                        Themes.getColorAsColorStateList(context, Themes.Dark.iconBackgroundColor)
                    iconColor = Themes.getColorAsColorStateList(context, Themes.Dark.iconColor)
                    progressIndicatorColor =
                        intArrayOf(Themes.getColor(context, Themes.Dark.progressIndicatorColor))
                    titleTextColor = ColorStateList.valueOf(
                        it.getColor(
                            R.styleable.ChatUiMessageContentView_chat_ui_title_text_color,
                            Themes.getColor(context, Themes.Dark.titleTextColor)
                        )
                    )
                }
                else -> {
                    background = it.getDrawable(R.styleable.ChatUiMessageContentView_chat_ui_background)
                        ?: Themes.getDrawable(context, Themes.Light.background)
                    indicatorBackgroundColor =
                        Themes.getColorAsColorStateList(context, Themes.Light.iconBackgroundColor)
                    iconColor = Themes.getColorAsColorStateList(context, Themes.Light.iconColor)
                    progressIndicatorColor =
                        intArrayOf(Themes.getColor(context, Themes.Light.progressIndicatorColor))
                    titleTextColor = ColorStateList.valueOf(
                        it.getColor(
                            R.styleable.ChatUiMessageContentView_chat_ui_title_text_color,
                            Themes.getColor(context, Themes.Light.titleTextColor)
                        )
                    )
                }
            }
        }
    }

    var indicatorBackgroundColor: ColorStateList? = indicatorLayout.backgroundTintList
        get() = indicatorLayout.backgroundTintList
        set(value) {
            field = value
            indicatorLayout.backgroundTintList = value
        }

    var iconColor: ColorStateList? = iconView.imageTintList
        get() = iconView.imageTintList
        set(value) {
            field = value
            iconView.imageTintList = value
        }

    var progressIndicatorColor: IntArray = progressIndicator.indicatorColor
        get() = progressIndicator.indicatorColor
        set(value) {
            field = value
            progressIndicator.setIndicatorColor(*value)
        }

    var titleTextColor: ColorStateList? = titleView.textColors
        get() = titleView.textColors
        set(value) {
            field = value
            titleView.setTextColor(value)
        }

    var title: CharSequence? = titleView.text
        get() = titleView.text
        set(value) {
            if (field != value) {
                field = value
                titleView.text = value
            }
        }

    var subtitle: CharSequence? = subtitleView.text
        get() = subtitleView.text
        set(value) {
            if (field != value) {
                field = value
                subtitleView.text = value
            }
        }

    var description: CharSequence? = descriptionView.text
        get() = descriptionView.text
        set(value) {
            if (field != value) {
                field = value
                descriptionView.text = value
            }
        }

    var progress: Int = progressIndicator.progress
        get() = progressIndicator.progress
        set(value) {
            field = value
            if (progressIndicator.isIndeterminate) {
                progressIndicator.isIndeterminate = false
            }
            progressIndicator.progress = value
        }

    var isProgressIndicatorVisible: Boolean = progressIndicator.isVisible
        get() = progressIndicator.isVisible
        set(value) {
            field = value
            progressIndicator.isVisible = value
        }

    fun setIconImageResource(@DrawableRes iconRes: Int) {
        iconView.setImageResource(iconRes)
    }

    override fun setOnClickListener(listener: OnClickListener?) {
        if (listener == null) {
            isClickable = false
            isFocusable = false
            super.setOnClickListener(listener)
        } else {
            isClickable = true
            isFocusable = true
            super.setOnClickListener(listener)
        }
    }

    private sealed interface Themes {
        companion object {
            @ColorInt
            fun getColor(context: Context, @ColorRes id: Int): Int =
                ContextCompat.getColor(context, id)

            fun getColorStateList(context: Context, @ColorRes id: Int): ColorStateList? =
                ContextCompat.getColorStateList(context, id)

            fun getColorAsColorStateList(context: Context, @ColorRes id: Int): ColorStateList =
                ColorStateList.valueOf(getColor(context, id))

            fun getDrawable(context: Context, @DrawableRes id: Int): Drawable? =
                AppCompatResources.getDrawable(context, id)
        }

        @get:DrawableRes
        val background: Int

        @get:ColorRes
        val iconBackgroundColor: Int

        @get:ColorRes
        val iconColor: Int

        @get:ColorRes
        val progressIndicatorColor: Int

        @get:ColorRes
        val titleTextColor: Int

        object Light : Themes {
            override val background: Int = R.drawable.chat_ui_ripple_top_left_rounded_transparent

            override val iconBackgroundColor: Int = R.color.chat_ui_purple_blue

            override val iconColor: Int = R.color.chat_ui_white

            override val progressIndicatorColor: Int = R.color.chat_ui_white

            override val titleTextColor: Int = R.color.chat_ui_light_black
        }

        object Dark : Themes {
            override val background: Int = R.drawable.chat_ui_ripple_top_right_rounded_transparent

            override val iconBackgroundColor: Int = R.color.chat_ui_white

            override val iconColor: Int = R.color.chat_ui_purple_blue

            override val progressIndicatorColor: Int = R.color.chat_ui_purple_blue

            override val titleTextColor: Int = R.color.chat_ui_white
        }
    }

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(Theme.LIGHT, Theme.DARK)
    annotation class Theme {
        companion object {
            const val LIGHT = 0
            const val DARK = 1
        }
    }

}