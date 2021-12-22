package kz.garage.animation.funhouse.method.internal.attention

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import kz.garage.animation.funhouse.method.base.BaseSingleAnimation

internal class RubberBand : BaseSingleAnimation() {

    override fun describe(view: View): AnimatorSet =
        createAnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(view, "scaleX", 1F, 1.25F, 0.75F, 1.15F, 1F),
                ObjectAnimator.ofFloat(view, "scaleY", 1F, 0.75F, 1.25F, 0.85F, 1F)
            )
        }

}