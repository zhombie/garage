package kz.garage.animation.funhouse.method.internal.attention

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import kz.garage.animation.funhouse.method.base.BaseAnimation

internal class Flash : BaseAnimation() {

    override fun describe(view: View): AnimatorSet =
        createAnimatorSet().apply {
            playTogether(
                ObjectAnimator
                    .ofFloat(view, "alpha", 1F, 0F, 1F, 0F, 1F)
            )
        }

}