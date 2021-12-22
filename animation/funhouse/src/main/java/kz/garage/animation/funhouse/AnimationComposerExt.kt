package kz.garage.animation.funhouse

import android.view.View
import android.view.animation.Interpolator
import kz.garage.animation.funhouse.method.Method

fun View.animate(
    method: Method,
    duration: Long = AnimationComposer.DEFAULT_DURATION,
    interpolator: Interpolator = AnimationComposer.DEFAULT_INTERPOLATOR
) {
    AnimationComposer()
        .setDuration(duration)
        .setInterpolator(interpolator)
        .play(method)
        .start(this)
}