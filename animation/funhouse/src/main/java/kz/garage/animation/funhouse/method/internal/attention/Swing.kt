package kz.garage.animation.funhouse.method.internal.attention

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import kz.garage.animation.funhouse.method.base.BaseAnimation

internal class Swing : BaseAnimation() {

    override fun describe(view: View): AnimatorSet =
        createAnimatorSet().apply {
            playTogether(
                ObjectAnimator
                    .ofFloat(view, "rotation", 0F, 10F, -10F, 6F, -6F, 3F, -3F, 0F)
            )
        }

}