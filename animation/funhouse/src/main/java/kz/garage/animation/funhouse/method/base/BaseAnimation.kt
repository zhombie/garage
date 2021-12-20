package kz.garage.animation.funhouse.method.base

import android.animation.AnimatorSet
import android.view.View
import android.view.animation.Interpolator

internal abstract class BaseAnimation {

    data class Definition constructor(
        val duration: Long,
        val interpolator: Interpolator
    )

    private var definition: Definition? = null

    fun setDefinition(definition: Definition?): BaseAnimation {
        this.definition = definition
        return this
    }

    protected fun createAnimatorSet(): AnimatorSet = AnimatorSet()

    abstract fun describe(view: View): AnimatorSet

    fun start(view: View) {
        val animatorSet = describe(view)
        definition?.let { definition ->
            animatorSet.duration = definition.duration
            animatorSet.interpolator = definition.interpolator
        }
        animatorSet.start()
    }

}