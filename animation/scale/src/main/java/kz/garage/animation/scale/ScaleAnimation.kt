package kz.garage.animation.scale

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.Interpolator
import android.view.animation.ScaleAnimation
import java.lang.ref.WeakReference

// Inspired by: https://github.com/TheKhaeng/pushdown-anim-click,
// https://gist.github.com/gokulkrizh/42aa995bd7845770588461fc7bf726be
class ScaleAnimation internal constructor(
    private val viewReference: WeakReference<View>,
    val startInterpolator: Interpolator = DEFAULT_START_INTERPOLATOR,
    val endInterpolator: Interpolator = DEFAULT_END_INTERPOLATOR,
    requestedStartDuration: Long = DEFAULT_START_DURATION,
    requestedEndDuration: Long = DEFAULT_END_DURATION,
    requestedPercentage: Float,
    private val onAnimationStart: () -> Unit = {},
    private val onAnimationEnd: () -> Unit = {}
) : View.OnTouchListener {

    companion object {
        var DEFAULT_START_INTERPOLATOR: Interpolator = AccelerateDecelerateInterpolator()
        var DEFAULT_END_INTERPOLATOR: Interpolator = AccelerateDecelerateInterpolator()

        var DEFAULT_START_DURATION: Long = 50L
        var DEFAULT_END_DURATION: Long = 125L
        val ALLOWED_DURATION_RANGE: LongRange = 10L..250L

        var DEFAULT_PERCENTAGE: Float = 90F
    }

    val view: View?
        get() = viewReference.get()

    val startDuration = if (requestedStartDuration in ALLOWED_DURATION_RANGE) {
        requestedStartDuration
    } else {
        DEFAULT_START_DURATION
    }

    val endDuration = if (requestedEndDuration in ALLOWED_DURATION_RANGE) {
        requestedEndDuration
    } else {
        DEFAULT_END_DURATION
    }

    val percentage: Float = if (requestedPercentage in 0F..100F) {
        requestedPercentage
    } else {
        DEFAULT_PERCENTAGE
    }

    val scale: Float = percentage / 100F

    private var startScaleAnimation: ScaleAnimation? = null
    private var endScaleAnimation: ScaleAnimation? = null

    init {
        view?.setOnTouchListener(this)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (v == null) return false

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                startScaleAnimation = v.scaleAnimation(
                    fromX = 1.0f,
                    toX = scale,
                    fromY = 1.0f,
                    toY = scale,
                    duration = startDuration,
                    interpolator = startInterpolator,
                    onStart = {
                        onAnimationStart.invoke()
                    }
                )
                return true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                endScaleAnimation = v.scaleAnimation(
                    fromX = scale,
                    toX = 1.0f,
                    fromY = scale,
                    toY = 1.0f,
                    duration = endDuration,
                    interpolator = endInterpolator,
                    onEnd = {
                        onAnimationEnd.invoke()
                    }
                )
            }
        }

        return false
    }

    private fun View.scaleAnimation(
        fromX: Float,
        toX: Float,
        fromY: Float,
        toY: Float,
        duration: Long,
        interpolator: Interpolator = AccelerateDecelerateInterpolator(),
        onStart: (animation: Animation?) -> Unit = {},
        onEnd: (animation: Animation?) -> Unit = {}
    ): ScaleAnimation {
        val scaleAnimation = ScaleAnimation(
            fromX,
            toX,
            fromY,
            toY,
            Animation.RELATIVE_TO_SELF,
            0.5F,
            Animation.RELATIVE_TO_SELF,
            0.5F
        )
        scaleAnimation.interpolator = interpolator
        scaleAnimation.duration = duration
        scaleAnimation.fillAfter = true
        scaleAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                onStart.invoke(animation)
            }

            override fun onAnimationEnd(animation: Animation?) {
                onEnd.invoke(animation)
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }
        })
        startAnimation(scaleAnimation)
        return scaleAnimation
    }

}