package kz.garage.animation.funhouse.method.internal.zoom.enter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import kz.garage.animation.funhouse.method.base.BaseSingleAnimation

internal class ZoomInLeft : BaseSingleAnimation() {

    override fun describe(view: View): AnimatorSet =
        createAnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(view, "scaleX", 0.1F, 0.475F, 1F),
                ObjectAnimator.ofFloat(view, "scaleY", 0.1F, 0.475F, 1F),
                ObjectAnimator.ofFloat(view, "translationX", -view.right.toFloat(), 48F, 0F),
                ObjectAnimator.ofFloat(view, "alpha", 0F, 1F, 1F)
            )
        }

}