package kz.garage.animation.scale

import android.view.View
import java.lang.ref.WeakReference

fun View.setScaleAnimationOnClick(
    percentage: Float = ScaleAnimation.DEFAULT_PERCENTAGE,
    onClickAction: (view: View) -> Unit
) = setScaleAnimationOnClick(
//    startInterpolator = ScaleAnimation.DEFAULT_START_INTERPOLATOR,
//    endInterpolator = ScaleAnimation.DEFAULT_END_INTERPOLATOR,
//    startDuration = ScaleAnimation.DEFAULT_START_DURATION,
//    endDuration = ScaleAnimation.DEFAULT_END_DURATION,
    requestedDuration = ScaleAnimation.DEFAULT_DURATION,
    percentage = percentage,
    onAnimationStart = {},
    onAnimationEnd = {},
    onClickAction = onClickAction
)

fun View.setScaleAnimationOnClick(
//    startInterpolator: Interpolator = ScaleAnimation.DEFAULT_START_INTERPOLATOR,
//    endInterpolator: Interpolator = ScaleAnimation.DEFAULT_END_INTERPOLATOR,
//    startDuration: Long = ScaleAnimation.DEFAULT_START_DURATION,
//    endDuration: Long = ScaleAnimation.DEFAULT_END_DURATION,
    requestedDuration: Long,
    percentage: Float,
    onAnimationStart: () -> Unit = {},
    onAnimationEnd: () -> Unit = {},
    onClickAction: (view: View) -> Unit = {}
): ScaleAnimation {
    return ScaleAnimation(
        viewReference = WeakReference(this),
//        startInterpolator = startInterpolator,
//        endInterpolator = endInterpolator,
//        requestedStartDuration = startDuration,
//        requestedEndDuration = endDuration,
        requestedDuration = requestedDuration,
        requestedPercentage = percentage,
        onAnimationStart = onAnimationStart,
        onAnimationEnd = onAnimationEnd,
        onClickAction = onClickAction
    )
}