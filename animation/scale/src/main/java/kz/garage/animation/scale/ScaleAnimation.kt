package kz.garage.animation.scale

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import java.lang.ref.WeakReference

// Inspired by: https://github.com/TheKhaeng/pushdown-anim-click
class ScaleAnimation internal constructor(
    private val viewReference: WeakReference<View>,
    val startInterpolator: Interpolator = DEFAULT_START_INTERPOLATOR,
    val endInterpolator: Interpolator = DEFAULT_END_INTERPOLATOR,
    requestedStartDuration: Long = DEFAULT_START_DURATION,
    requestedEndDuration: Long = DEFAULT_END_DURATION,
    requestedPercentage: Float,
    private val onAnimationStart: () -> Unit = {},
    private val onAnimationEnd: () -> Unit = {}
) {

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

    private var scaleAnimatorSet: AnimatorSet? = null

    init {
        view?.setOnClickListener {
            if (scaleAnimatorSet?.isRunning == true) {
                scaleAnimatorSet?.end()
                scaleAnimatorSet = null
                return@setOnClickListener
            }

            val startScaleX = ObjectAnimator.ofFloat(it, "scaleX", scale)
            val startScaleY = ObjectAnimator.ofFloat(it, "scaleY", scale)
            val endScaleX = ObjectAnimator.ofFloat(it, "scaleX", it.scaleX)
            val endScaleY = ObjectAnimator.ofFloat(it, "scaleY", it.scaleY)

            startScaleX.interpolator = startInterpolator
            startScaleX.duration = startDuration

            startScaleY.interpolator = startInterpolator
            startScaleY.duration = startDuration

            endScaleX.interpolator = endInterpolator
            endScaleX.duration = endDuration

            endScaleY.interpolator = endInterpolator
            endScaleY.duration = endDuration

            scaleAnimatorSet = AnimatorSet()

            scaleAnimatorSet
                ?.play(startScaleX)
                ?.with(startScaleY)

            scaleAnimatorSet
                ?.play(endScaleX)
                ?.after(startScaleX)
                ?.with(endScaleY)

            scaleAnimatorSet?.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                    onAnimationStart.invoke()
                }

                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    onAnimationEnd.invoke()
                }
            })

            scaleAnimatorSet?.start()
        }
    }

    fun end() {
        scaleAnimatorSet?.end()
        scaleAnimatorSet = null
    }

}