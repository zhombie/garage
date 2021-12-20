package kz.garage.animation.funhouse.method.internal.attention

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import kz.garage.animation.funhouse.method.base.BaseAnimation

internal class StandUp : BaseAnimation() {

    override fun describe(view: View): AnimatorSet {
        val x = (view.width - view.paddingLeft - view.paddingRight) / 2F + view.paddingLeft
        val y = (view.height - view.paddingBottom).toFloat()
        return createAnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(view, "pivotX", x, x, x, x, x),
                ObjectAnimator.ofFloat(view, "pivotY", y, y, y, y, y),
                ObjectAnimator.ofFloat(view, "rotationX", 55F, -30F, 15F, -15F, 0F)
            )
        }
    }
    
}