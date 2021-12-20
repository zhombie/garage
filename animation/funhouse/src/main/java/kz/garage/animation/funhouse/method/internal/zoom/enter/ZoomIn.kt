package kz.garage.animation.funhouse.method.internal.zoom.enter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import kz.garage.animation.funhouse.method.base.BaseAnimation

internal class ZoomIn : BaseAnimation() {

    override fun describe(view: View): AnimatorSet =
        createAnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(view, "scaleX", 0.45F, 1F),
                ObjectAnimator.ofFloat(view, "scaleY", 0.45F, 1F),
                ObjectAnimator.ofFloat(view, "alpha", 0F, 1F)
            )
        }

}