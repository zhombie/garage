package kz.garage.animation.funhouse.method.internal.zoom.enter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import kz.garage.animation.funhouse.method.base.BaseSingleAnimation

internal class ZoomInDown : BaseSingleAnimation() {

    override fun describe(view: View): AnimatorSet =
        createAnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(view, "scaleX", 0.1F, 0.475F, 1F),
                ObjectAnimator.ofFloat(view, "scaleY", 0.1F, 0.475F, 1F),
                ObjectAnimator.ofFloat(view, "translationY", -view.bottom.toFloat(), 60F, 0F),
                ObjectAnimator.ofFloat(view, "alpha", 0F, 1F, 1F)
            )
        }

}