package kz.garage.animation.funhouse.method.internal.bounce.enter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import kz.garage.animation.funhouse.method.base.BaseSingleAnimation

internal class BounceInDown : BaseSingleAnimation() {

    override fun describe(view: View): AnimatorSet =
        createAnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(view, "alpha", 0F, 1F, 1F, 1F),
                ObjectAnimator.ofFloat(view, "translationY", -view.height.toFloat(), 30F, -10F, 0F)
            )
        }

}