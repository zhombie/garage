package kz.garage.animation.funhouse

import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.Interpolator
import kz.garage.animation.funhouse.method.base.BaseAnimation
import kz.garage.animation.funhouse.method.factory.DefaultAnimationFactory
import kz.garage.animation.funhouse.method.Method

// Inspired by: https://github.com/daimajia/AndroidViewAnimations,
// https://github.com/gayanvoice/android-animations-kotlin
class AnimationComposer {

    companion object {
        const val DEFAULT_DURATION = 125L
        val DEFAULT_INTERPOLATOR = AccelerateInterpolator()
    }

    private var method: Method? = null
    private var duration: Long = DEFAULT_DURATION
    private var interpolator: Interpolator = DEFAULT_INTERPOLATOR

    fun getMethod(): Method? = method

    fun getDuration(): Long = duration

    fun getInterpolator(): Interpolator = interpolator

    fun setMethod(method: Method?): AnimationComposer {
        this.method = method
        return this
    }

    fun setDuration(duration: Long): AnimationComposer {
        this.duration = duration
        return this
    }

    fun setInterpolator(interpolator: Interpolator): AnimationComposer {
        this.interpolator = interpolator
        return this
    }

    fun start(view: View) {
        DefaultAnimationFactory()
            .create(requireNotNull(method))
            ?.setDefinition(
                BaseAnimation.Definition(
                    duration = duration,
                    interpolator = interpolator
                )
            )
            ?.start(view)
    }

}