package kz.garage.animation.funhouse.method.internal.bounce.enter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import kz.garage.animation.funhouse.method.base.BaseAnimation

internal class BounceInRight : BaseAnimation() {

    override fun describe(view: View): AnimatorSet =
        createAnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(view, "translationX", view.measuredWidth + view.width.toFloat(), -30F, 10F, 0F),
                ObjectAnimator.ofFloat(view, "alpha", 0F, 1F, 1F, 1F)
            )
        }

}