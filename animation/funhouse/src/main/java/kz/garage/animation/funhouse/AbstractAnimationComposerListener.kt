package kz.garage.animation.funhouse

import android.animation.Animator
import android.view.View

abstract class AbstractAnimationComposerListener : AnimationComposer.Listener {

    override fun onStart(view: View, animator: Animator?) {
    }

    override fun onEnd(view: View, animator: Animator?) {
    }

    override fun onCancel(view: View, animator: Animator?) {
    }

    override fun onRepeat(view: View, animator: Animator?) {
    }

}