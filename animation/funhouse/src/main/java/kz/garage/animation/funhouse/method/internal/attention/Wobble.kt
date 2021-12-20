package kz.garage.animation.funhouse.method.internal.attention

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import kz.garage.animation.funhouse.method.base.BaseAnimation

internal class Wobble : BaseAnimation() {

    override fun describe(view: View): AnimatorSet {
        val width = view.width
        val one = width / 100F
        return createAnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(view, "translationX", 0 * one, -25 * one, 20 * one, -15 * one, 10 * one, -5 * one, 0 * one, 0F),
                ObjectAnimator.ofFloat(view, "rotation", 0F, -5F, 3F, -3F, 2F, -1F, 0F)
            )
        }
    }
    
}