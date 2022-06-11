package kz.garage.animation.scale

import android.view.View
import android.view.animation.CycleInterpolator
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListener
import java.lang.ref.WeakReference

// Inspired by: https://github.com/TheKhaeng/pushdown-anim-click,
// https://gist.github.com/gokulkrizh/42aa995bd7845770588461fc7bf726be,
// https://github.com/jd-alexander/LikeButton/blob/master/likebutton/src/main/java/com/like/LikeButton.java
// https://github.com/skydoves/ElasticViews
class ScaleAnimation internal constructor(
    private val viewReference: WeakReference<View>,
//    val startInterpolator: Interpolator = DEFAULT_START_INTERPOLATOR,
//    val endInterpolator: Interpolator = DEFAULT_END_INTERPOLATOR,
    requestedDuration: Long = DEFAULT_DURATION,
//    requestedStartDuration: Long = DEFAULT_START_DURATION,
//    requestedEndDuration: Long = DEFAULT_END_DURATION,
    requestedPercentage: Float,
    private val onAnimationStart: () -> Unit = {},
    private val onAnimationEnd: () -> Unit = {},
    private val onClickAction: (view: View) -> Unit = {}
) {

    companion object {
//        var DEFAULT_START_INTERPOLATOR: Interpolator = AccelerateDecelerateInterpolator()
//        var DEFAULT_END_INTERPOLATOR: Interpolator = AccelerateDecelerateInterpolator()

        var DEFAULT_DURATION: Long = 95L
//        var DEFAULT_START_DURATION: Long = 50L
//        var DEFAULT_END_DURATION: Long = 125L
        val ALLOWED_DURATION_RANGE: LongRange = 10L..250L

        var DEFAULT_PERCENTAGE: Float = 90F
    }

    val view: View?
        get() = viewReference.get()

    val duration = if (requestedDuration in ALLOWED_DURATION_RANGE) {
        requestedDuration
    } else {
        DEFAULT_DURATION
    }

//    val startDuration = if (requestedStartDuration in ALLOWED_DURATION_RANGE) {
//        requestedStartDuration
//    } else {
//        DEFAULT_START_DURATION
//    }
//
//    val endDuration = if (requestedEndDuration in ALLOWED_DURATION_RANGE) {
//        requestedEndDuration
//    } else {
//        DEFAULT_END_DURATION
//    }

    val percentage: Float = if (requestedPercentage in 0F..100F) {
        requestedPercentage
    } else {
        DEFAULT_PERCENTAGE
    }

    val scale: Float = percentage / 100F

    var isAnimationRunning = false
        private set

    init {
        view?.setOnClickListener {
            if (isAnimationRunning) return@setOnClickListener
            if (it.scaleX == 1F && it.scaleY == 1F) {
                ViewCompat.animate(it)
                    .setDuration(duration)
                    .scaleX(scale)
                    .scaleY(scale)
                    .setInterpolator(CycleInterpolator(0.5F))  // In order to repeat
                    .setListener(object : ViewPropertyAnimatorListener {
                        override fun onAnimationStart(view: View) {
                            isAnimationRunning = true
                            onAnimationStart.invoke()
                        }

                        override fun onAnimationEnd(view: View) {
                            isAnimationRunning = false
                            onAnimationEnd.invoke()
                            onClickAction.invoke(it)
                        }

                        override fun onAnimationCancel(view: View) {
                            isAnimationRunning = false
                        }
                    })
                    .withLayer()
                    .start()
            }
        }
    }

//    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//        if (v == null) return false
//
//        when (event?.action) {
//            MotionEvent.ACTION_DOWN -> {
//                v.isPressed = true
//                return true
//            }
//            MotionEvent.ACTION_MOVE -> {
//                val isInside = isMotionEventInsideView(v, event)
//                if (v.isPressed != isInside) {
//                    v.isPressed = isInside
//                    return true
//                }
//            }
//            MotionEvent.ACTION_UP -> {
//                if (v.isPressed) {
//                    startScaleAnimation = v.scaleAnimation(
//                        fromX = 1.0F,
//                        toX = scale,
//                        fromY = 1.0F,
//                        toY = scale,
//                        duration = startDuration,
//                        interpolator = startInterpolator,
//                        onStart = {
//                            onAnimationStart.invoke()
//                        },
//                        onEnd = {
//                            endScaleAnimation = v.scaleAnimation(
//                                fromX = scale,
//                                toX = 1.0F,
//                                fromY = scale,
//                                toY = 1.0F,
//                                duration = endDuration,
//                                interpolator = endInterpolator,
//                                onEnd = {
//                                    onAnimationEnd.invoke()
//                                }
//                            )
//                        }
//                    )
//
//                    v.performClick()
//                    v.isPressed = false
//
//                    return true
//                }
//            }
//            MotionEvent.ACTION_CANCEL -> {
//                v.isPressed = false
//                return true
//            }
//        }
//
//        return false
//    }
//
//    private fun isMotionEventInsideView(view: View, event: MotionEvent): Boolean {
//        val rect = Rect(view.left, view.top, view.right, view.bottom)
//        return rect.contains(view.left + event.x.toInt(), view.top + event.y.toInt())
//    }
//
//    private fun View.scaleAnimation(
//        fromX: Float,
//        toX: Float,
//        fromY: Float,
//        toY: Float,
//        duration: Long,
//        interpolator: Interpolator = AccelerateDecelerateInterpolator(),
//        onStart: (animation: Animation?) -> Unit = {},
//        onEnd: (animation: Animation?) -> Unit = {}
//    ): ScaleAnimation {
//        val scaleAnimation = ScaleAnimation(
//            fromX,
//            toX,
//            fromY,
//            toY,
//            Animation.RELATIVE_TO_SELF,
//            0.5F,
//            Animation.RELATIVE_TO_SELF,
//            0.5F
//        )
//        scaleAnimation.interpolator = interpolator
//        scaleAnimation.duration = duration
//        scaleAnimation.fillAfter = true
//        scaleAnimation.setAnimationListener(object : Animation.AnimationListener {
//            override fun onAnimationStart(animation: Animation?) {
//                onStart.invoke(animation)
//            }
//
//            override fun onAnimationEnd(animation: Animation?) {
//                onEnd.invoke(animation)
//            }
//
//            override fun onAnimationRepeat(animation: Animation?) {
//            }
//        })
//        startAnimation(scaleAnimation)
//        return scaleAnimation
//    }

}