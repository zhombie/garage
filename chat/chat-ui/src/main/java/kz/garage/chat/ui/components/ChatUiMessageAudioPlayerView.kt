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
import com.google.android.material.slider.Slider
import com.google.android.material.textview.MaterialTextView
import kz.garage.chat.ui.R

internal class ChatUiMessageAudioPlayerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val rootLayout: RelativeLayout
    private val indicatorLayout: FrameLayout
    private val iconView: ShapeableImageView
    private val progressIndicator: CircularProgressIndicator
    private val titleView: MaterialTextView
    private val slider: Slider
    private val playbackTimeView: MaterialTextView
    private val durationView: MaterialTextView

    init {
        val view = inflate(context, R.layout.chat_ui_view_audio_player, this)

        rootLayout = view.findViewById(R.id.rootLayout)
        indicatorLayout = view.findViewById(R.id.indicatorLayout)
        iconView = view.findViewById(R.id.iconView)
        progressIndicator = view.findViewById(R.id.progressIndicator)
        titleView = view.findViewById(R.id.titleView)
        slider = view.findViewById(R.id.slider)
        playbackTimeView = view.findViewById(R.id.playbackTimeView)
        durationView = view.findViewById(R.id.durationView)

        context.obtainStyledAttributes(attrs, R.styleable.ChatUiMessageAudioPlayerView).use {
            when (it.getInt(R.styleable.ChatUiMessageAudioPlayerView_chat_ui_theme_mode, -1)) {
                Theme.DARK -> {
                    background = it.getDrawable(R.styleable.ChatUiMessageAudioPlayerView_chat_ui_background)
                        ?: Themes.getDrawable(context, Themes.Dark.background)
                    indicatorBackgroundColor =
                        Themes.getColorAsColorStateList(context, Themes.Dark.iconBackgroundColor)
                    iconColor = Themes.getColorAsColorStateList(context, Themes.Dark.iconColor)
                    progressIndicatorColor =
                        intArrayOf(Themes.getColor(context, Themes.Dark.progressIndicatorColor))
                    titleTextColor = ColorStateList.valueOf(
                        it.getColor(
                            R.styleable.ChatUiMessageAudioPlayerView_chat_ui_title_text_color,
                            Themes.getColor(context, Themes.Dark.titleTextColor)
                        )
                    )
                    timeTextColor = ColorStateList.valueOf(
                        it.getColor(
                            R.styleable.ChatUiMessageAudioPlayerView_chat_ui_time_text_color,
                            Themes.getColor(context, Themes.Dark.timeTextColor)
                        )
                    )
                    sliderInactiveTrackColor = it.getColorStateList(R.styleable.ChatUiMessageAudioPlayerView_chat_ui_slider_inactive_track_color)
                        ?: Themes.getColorAsColorStateList(
                            context,
                            Themes.Dark.sliderInactiveTrackColor
                        )
                    sliderActiveTrackColor = it.getColorStateList(R.styleable.ChatUiMessageAudioPlayerView_chat_ui_slider_active_track_color)
                        ?: Themes.getColorAsColorStateList(
                            context,
                            Themes.Dark.sliderActiveTrackColor
                        )
                    sliderThumbColor = it.getColorStateList(R.styleable.ChatUiMessageAudioPlayerView_chat_ui_slider_thumb_color)
                        ?: Themes.getColorAsColorStateList(context, Themes.Dark.sliderThumbColor)
                }
                else -> {
                    background = it.getDrawable(R.styleable.ChatUiMessageAudioPlayerView_chat_ui_background)
                        ?: Themes.getDrawable(context, Themes.Light.background)
                    indicatorBackgroundColor =
                        Themes.getColorAsColorStateList(context, Themes.Light.iconBackgroundColor)
                    iconColor = Themes.getColorAsColorStateList(context, Themes.Light.iconColor)
                    progressIndicatorColor =
                        intArrayOf(Themes.getColor(context, Themes.Light.progressIndicatorColor))
                    titleTextColor = ColorStateList.valueOf(
                        it.getColor(
                            R.styleable.ChatUiMessageAudioPlayerView_chat_ui_title_text_color,
                            Themes.getColor(context, Themes.Light.titleTextColor)
                        )
                    )
                    timeTextColor = ColorStateList.valueOf(
                        it.getColor(
                            R.styleable.ChatUiMessageAudioPlayerView_chat_ui_time_text_color,
                            Themes.getColor(context, Themes.Light.timeTextColor)
                        )
                    )
                    sliderInactiveTrackColor = it.getColorStateList(R.styleable.ChatUiMessageAudioPlayerView_chat_ui_slider_inactive_track_color)
                        ?: Themes.getColorAsColorStateList(
                            context,
                            Themes.Light.sliderInactiveTrackColor
                        )
                    sliderActiveTrackColor = it.getColorStateList(R.styleable.ChatUiMessageAudioPlayerView_chat_ui_slider_active_track_color)
                        ?: Themes.getColorAsColorStateList(
                            context,
                            Themes.Light.sliderActiveTrackColor
                        )
                    sliderThumbColor = it.getColorStateList(R.styleable.ChatUiMessageAudioPlayerView_chat_ui_slider_thumb_color)
                        ?: Themes.getColorAsColorStateList(context, Themes.Light.sliderThumbColor)
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

    var sliderInactiveTrackColor: ColorStateList = slider.trackInactiveTintList
        get() = slider.trackInactiveTintList
        set(value) {
            field = value
            slider.trackInactiveTintList = value
        }

    var sliderActiveTrackColor: ColorStateList = slider.trackActiveTintList
        get() = slider.trackActiveTintList
        set(value) {
            field = value
            slider.trackActiveTintList = value
        }

    var sliderThumbColor: ColorStateList = slider.thumbTintList
        get() = slider.thumbTintList
        set(value) {
            field = value
            slider.thumbTintList = value
        }

    var timeTextColor: ColorStateList? = null
        set(value) {
            field = value
            playbackTimeView.setTextColor(value)
            durationView.setTextColor(value)
        }

    var title: CharSequence? = titleView.text
        get() = titleView.text
        set(value) {
            if (field != value) {
                field = value
                titleView.text = value
            }
        }

    var progressIndicatorProgress: Int = progressIndicator.progress
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

    var isSliderVisible: Boolean = slider.isVisible
        get() = slider.isVisible
        set(value) {
            field = value
            slider.isVisible = value
        }

    var isPlaybackTimeVisible: Boolean = playbackTimeView.isVisible
        get() = playbackTimeView.isVisible
        set(value) {
            field = value
            playbackTimeView.isVisible = value
        }

    var isDurationVisible: Boolean = durationView.isVisible
        get() = durationView.isVisible
        set(value) {
            field = value
            durationView.isVisible = value
        }

    var minSliderValue: Float = slider.valueFrom
        get() = slider.valueFrom
        set(value) {
            field = value
            slider.valueFrom = value
        }

    var maxSliderValue: Float = slider.valueTo
        get() = slider.valueTo
        set(value) {
            field = value
            slider.valueTo = value
        }

    var sliderProgress: Float = slider.value
        get() = slider.value
        set(value) {
            field = value
            try {
                slider.value = value
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    var playbackTimeText: CharSequence = playbackTimeView.text
        get() = playbackTimeView.text
        set(value) {
            field = value
            playbackTimeView.text = value
        }

    fun setIconImageResource(@DrawableRes iconRes: Int) {
        iconView.setImageResource(iconRes)
    }

    override fun setOnClickListener(listener: OnClickListener?) {
        if (listener == null) {
            indicatorLayout.isClickable = false
            indicatorLayout.isFocusable = false
            indicatorLayout.setOnClickListener(listener)
        } else {
            indicatorLayout.isClickable = true
            indicatorLayout.isFocusable = true
            indicatorLayout.setOnClickListener(listener)
        }
    }

    fun addOnSliderValueChangeListener(callback: (progress: Float) -> Unit) {
        slider.addOnChangeListener { _, value, fromUser ->
            if (fromUser) {
                callback(value)
            }
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

        @get:ColorRes
        val timeTextColor: Int

        @get:ColorRes
        val sliderInactiveTrackColor: Int

        @get:ColorRes
        val sliderActiveTrackColor: Int

        @get:ColorRes
        val sliderThumbColor: Int

        object Light : Themes {
            override val background: Int = R.drawable.chat_ui_ripple_top_left_rounded_transparent

            override val iconBackgroundColor: Int = R.color.chat_ui_purple_blue

            override val iconColor: Int = R.color.chat_ui_white

            override val progressIndicatorColor: Int = R.color.chat_ui_white

            override val titleTextColor: Int = R.color.chat_ui_light_black

            override val timeTextColor: Int = R.color.chat_ui_dark_gray

            override val sliderInactiveTrackColor: Int = R.color.chat_ui_dark_gray

            override val sliderActiveTrackColor: Int = R.color.chat_ui_purple_blue

            override val sliderThumbColor: Int = R.color.chat_ui_purple_blue
        }

        object Dark : Themes {
            override val background: Int = R.drawable.chat_ui_ripple_top_right_rounded_transparent

            override val iconBackgroundColor: Int = R.color.chat_ui_white

            override val iconColor: Int = R.color.chat_ui_purple_blue

            override val progressIndicatorColor: Int = R.color.chat_ui_purple_blue

            override val titleTextColor: Int = R.color.chat_ui_white

            override val timeTextColor: Int = R.color.chat_ui_white

            override val sliderInactiveTrackColor: Int = R.color.chat_ui_moderate_blue

            override val sliderActiveTrackColor: Int = R.color.chat_ui_white

            override val sliderThumbColor: Int = R.color.chat_ui_white
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