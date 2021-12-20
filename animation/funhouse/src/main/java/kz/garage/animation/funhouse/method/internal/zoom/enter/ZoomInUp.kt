package kz.garage.animation.funhouse.method.internal.zoom.enter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.ViewGroup
import kz.garage.animation.funhouse.method.base.BaseAnimation

internal class ZoomInUp : BaseAnimation() {

    override fun describe(view: View): AnimatorSet {
        val parent = view.parent
        val distance = if (parent is ViewGroup) parent.height - view.top else view.top
        return createAnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(view, "alpha", 0f, 1f, 1f),
                ObjectAnimator.ofFloat(view, "scaleX", 0.1f, 0.475f, 1f),
                ObjectAnimator.ofFloat(view, "scaleY", 0.1f, 0.475f, 1f),
                ObjectAnimator.ofFloat(view, "translationY", distance.toFloat(), -60f, 0f)
            )
        }
    }

}