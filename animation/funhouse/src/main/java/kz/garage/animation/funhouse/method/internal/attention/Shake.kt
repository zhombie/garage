package kz.garage.animation.funhouse.method.internal.attention

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import kz.garage.animation.funhouse.method.base.BaseAnimation

internal class Shake : BaseAnimation() {

    override fun describe(view: View): AnimatorSet =
        createAnimatorSet().apply {
            playTogether(
                ObjectAnimator
                    .ofFloat(view, "translationX", 0F, 25F, -25F, 25F, -25F, 15F, -15F, 6F, -6F, 0F)
            )
        }

}