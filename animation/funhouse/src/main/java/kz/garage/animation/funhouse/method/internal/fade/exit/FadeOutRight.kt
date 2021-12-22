package kz.garage.animation.funhouse.method.internal.fade.exit

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import kz.garage.animation.funhouse.method.base.BaseSingleAnimation

internal class FadeOutRight : BaseSingleAnimation() {

    override fun describe(view: View): AnimatorSet =
        createAnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(view, "alpha", 1F, 0F),
                ObjectAnimator.ofFloat(view, "translationX", 0F, view.width / 4F)
            )
        }

}