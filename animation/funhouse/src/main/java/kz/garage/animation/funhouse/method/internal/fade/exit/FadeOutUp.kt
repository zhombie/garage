package kz.garage.animation.funhouse.method.internal.fade.exit

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import kz.garage.animation.funhouse.method.base.BaseAnimation

internal class FadeOutUp : BaseAnimation() {

    override fun describe(view: View): AnimatorSet =
        createAnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(view, "alpha", 1F, 0F),
                ObjectAnimator.ofFloat(view, "translationY", 0F, -view.height / 4F)
            )
        }

}