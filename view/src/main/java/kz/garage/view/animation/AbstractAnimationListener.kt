package kz.garage.view.animation

import android.view.animation.Animation

abstract class AbstractAnimationListener : Animation.AnimationListener {
    override fun onAnimationStart(animation: Animation?) {
    }

    override fun onAnimationRepeat(animation: Animation?) {
    }

    override fun onAnimationEnd(animation: Animation?) {
    }
}