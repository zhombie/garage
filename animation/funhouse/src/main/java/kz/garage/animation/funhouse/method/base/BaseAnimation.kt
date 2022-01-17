package kz.garage.animation.funhouse.method.base

import android.animation.AnimatorSet
import android.view.View
import kz.garage.animation.funhouse.AnimationComposer

internal abstract class BaseAnimation {

    private var definition: Definition? = null
    private var listener: AnimationComposer.Listener? = null

    fun getDefinition(): Definition? = definition

    fun requireDefinition(): Definition = requireNotNull(definition)

    fun getListener(): AnimationComposer.Listener? = listener

    fun setDefinition(definition: Definition?): BaseAnimation {
        this.definition = definition
        return this
    }

    fun setListener(listener: AnimationComposer.Listener?): BaseAnimation {
        this.listener = listener
        return this
    }

    protected fun createAnimatorSet(): AnimatorSet = AnimatorSet()

    abstract fun start(view: View)

}