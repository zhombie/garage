package kz.garage.animation.funhouse.method.internal.zoom.exit

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import kz.garage.animation.funhouse.method.base.BaseSingleAnimation

internal class ZoomOutLeft : BaseSingleAnimation() {

    override fun describe(view: View): AnimatorSet =
        createAnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(view, "alpha", 1F, 1F, 0F),
                ObjectAnimator.ofFloat(view, "scaleX", 1F, 0.475F, 0.1F),
                ObjectAnimator.ofFloat(view, "scaleY", 1F, 0.475F, 0.1F),
                ObjectAnimator.ofFloat(view, "translationX", 0F, 42F, -view.right.toFloat())
            )
        }

}