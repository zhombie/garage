package kz.garage.animation.funhouse.method.base

import android.animation.AnimatorSet
import android.view.View

internal abstract class BaseAnimation {

    private var definition: Definition? = null

    fun getDefinition(): Definition? = definition

    fun requireDefinition(): Definition = requireNotNull(definition)

    fun setDefinition(definition: Definition?): BaseAnimation {
        this.definition = definition
        return this
    }

    protected fun createAnimatorSet(): AnimatorSet = AnimatorSet()

    abstract fun start(view: View)

}