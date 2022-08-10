package kz.garage.animation.funhouse.method.base

import android.animation.Animator
import android.animation.AnimatorSet
import android.view.View

internal abstract class BaseSingleAnimation : BaseAnimation() {

    abstract fun describe(view: View): AnimatorSet

    override fun start(view: View) {
        val animatorSet = describe(view)

        val listener = getListener()
        if (listener != null) {
            animatorSet.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    listener.onStart(view, animation)
                }

                override fun onAnimationEnd(animation: Animator) {
                    listener.onEnd(view, animation)
                }

                override fun onAnimationCancel(animation: Animator) {
                    listener.onCancel(view, animation)
                }

                override fun onAnimationRepeat(animation: Animator) {
                    listener.onRepeat(view, animation)
                }
            })
        }


        getDefinition()?.let { definition ->
            animatorSet.duration = definition.duration
            animatorSet.interpolator = definition.interpolator
        }

        animatorSet.start()
    }

}