package kz.garage.animation.funhouse.method.internal.attention

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import kz.garage.animation.funhouse.method.base.BaseAnimation

internal class Tada : BaseAnimation() {

    override fun describe(view: View): AnimatorSet =
        createAnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(view, "scaleX", 1F, 0.9F, 0.9F, 1.1F, 1.1F, 1.1F, 1.1F, 1.1F, 1.1F, 1F),
                ObjectAnimator.ofFloat(view, "scaleY", 1F, 0.9F, 0.9F, 1.1F, 1.1F, 1.1F, 1.1F, 1.1F, 1.1F, 1F),
                ObjectAnimator.ofFloat(view, "rotation", 0F, -3F, -3F, 3F, -3F, 3F, -3F, 3F, -3F, 0F)
            )
        }

}