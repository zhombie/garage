package kz.garage.animation.funhouse

import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.Interpolator
import kz.garage.animation.funhouse.method.Method
import kz.garage.animation.funhouse.method.Methods
import kz.garage.animation.funhouse.method.base.Definition
import kz.garage.animation.funhouse.method.factory.DefaultAnimationFactory

// TODO: Add listeners
// Inspired by: https://github.com/daimajia/AndroidViewAnimations,
// https://github.com/gayanvoice/android-animations-kotlin
class AnimationComposer {

    companion object {
        const val DEFAULT_DURATION = 125L
        val DEFAULT_INTERPOLATOR = AccelerateInterpolator()
    }

    private var before: MutableList<Method>? = null
    private var current: MutableList<Method>? = null
    private var after: MutableList<Method>? = null

    private var duration: Long = DEFAULT_DURATION
    private var interpolator: Interpolator = DEFAULT_INTERPOLATOR

    fun getBefore(): MutableList<Method>? = before

    fun getCurrent(): MutableList<Method>? = current

    fun getAfter(): MutableList<Method>? = after

    fun getDuration(): Long = duration

    fun getInterpolator(): Interpolator = interpolator

    fun play(method: Method): AnimationComposer {
        if (current == null) {
            current = mutableListOf()
        }
        current?.add(method)
        return this
    }

    fun play(vararg methods: Method): AnimationComposer {
        if (methods.isEmpty()) return this
        methods.forEach { method -> play(method) }
        return this
    }

    fun with(method: Method): AnimationComposer =
        play(method)

    fun before(method: Method): AnimationComposer {
        if (before == null) {
            before = mutableListOf()
        }
        before?.add(method)
        return this
    }

    fun before(vararg methods: Method): AnimationComposer {
        if (methods.isEmpty()) return this
        methods.forEach { method -> before(method) }
        return this
    }

    fun after(method: Method): AnimationComposer {
        if (after == null) {
            after = mutableListOf()
        }
        after?.add(method)
        return this
    }

    fun after(vararg methods: Method): AnimationComposer {
        if (methods.isEmpty()) return this
        methods.forEach { method -> after(method) }
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
        when (getTotalCount()) {
            0 -> return
            1 -> {
                DefaultAnimationFactory()
                    .create(requireNotNull(getSingle()))
                    .setDefinition(
                        Definition(
                            duration = duration,
                            interpolator = interpolator
                        )
                    )
                    .start(view)
            }
            else -> {
                DefaultAnimationFactory()
                    .create(Methods(after, current, before))
                    ?.setDefinition(
                        Definition(
                            duration = duration,
                            interpolator = interpolator
                        )
                    )
                    ?.start(view)
            }
        }
    }

    private fun getTotalCount(): Int =
        (before?.size ?: 0) + (current?.size ?: 0) + (after?.size ?: 0)

    private fun getSingle(): Method? = when {
        !before.isNullOrEmpty() -> before?.first()
        !current.isNullOrEmpty() -> current?.first()
        !after.isNullOrEmpty() -> after?.first()
        else -> null
    }

}