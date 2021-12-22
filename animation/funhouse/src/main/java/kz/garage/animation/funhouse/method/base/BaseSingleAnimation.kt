package kz.garage.animation.funhouse.method.base

import android.animation.AnimatorSet
import android.view.View

internal abstract class BaseSingleAnimation : BaseAnimation() {

    abstract fun describe(view: View): AnimatorSet

    override fun start(view: View) {
        val animatorSet = describe(view)
        getDefinition()?.let { definition ->
            animatorSet.duration = definition.duration
            animatorSet.interpolator = definition.interpolator
        }
        animatorSet.start()
    }

}