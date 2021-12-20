package kz.garage.animation.funhouse.method.internal.fade.enter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import kz.garage.animation.funhouse.method.base.BaseAnimation

internal class FadeIn : BaseAnimation() {

    override fun describe(view: View): AnimatorSet =
        createAnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(view, "alpha", 0F, 1F)
            )
        }

}