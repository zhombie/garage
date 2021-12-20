package kz.garage.animation.funhouse.method.internal.bounce.enter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import kz.garage.animation.funhouse.method.base.BaseAnimation

internal class BounceIn : BaseAnimation() {

    override fun describe(view: View): AnimatorSet =
        createAnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(view, "alpha", 0F, 1F, 1F, 1F),
                ObjectAnimator.ofFloat(view, "scaleX", 0.3F, 1.05F, 0.9F, 1F),
                ObjectAnimator.ofFloat(view, "scaleY", 0.3F, 1.05F, 0.9F, 1F)
            )
        }

}