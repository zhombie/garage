package kz.garage.animation.funhouse

import android.animation.Animator
import android.view.View
import android.view.animation.Interpolator
import kz.garage.animation.funhouse.method.Method

inline fun View.animate(
    method: Method,
    duration: Long = AnimationComposer.DEFAULT_DURATION,
    interpolator: Interpolator = AnimationComposer.DEFAULT_INTERPOLATOR,
    crossinline onStart: (view: View, animator: Animator?) -> Unit = { _, _ -> },
    crossinline onEnd: (view: View, animator: Animator?) -> Unit = { _, _ -> },
    crossinline onCancel: (view: View, animator: Animator?) -> Unit = { _, _ -> },
    crossinline onRepeat: (view: View, animator: Animator?) -> Unit = { _, _ -> }
): AnimationComposer.Listener {
    val listener = object : AnimationComposer.Listener {
        override fun onStart(view: View, animator: Animator?) = onStart(view, animator)
        override fun onEnd(view: View, animator: Animator?) = onEnd(view, animator)
        override fun onCancel(view: View, animator: Animator?) = onCancel(view, animator)
        override fun onRepeat(view: View, animator: Animator?) = onRepeat(view, animator)
    }

    AnimationComposer()
        .setDuration(duration)
        .setInterpolator(interpolator)
        .play(method)
        .start(this, listener)

    return listener
}

inline fun View.animate(
    vararg methods: Method,
    duration: Long = AnimationComposer.DEFAULT_DURATION,
    interpolator: Interpolator = AnimationComposer.DEFAULT_INTERPOLATOR,
    crossinline onStart: (view: View, animator: Animator?) -> Unit = { _, _ -> },
    crossinline onEnd: (view: View, animator: Animator?) -> Unit = { _, _ -> },
    crossinline onCancel: (view: View, animator: Animator?) -> Unit = { _, _ -> },
    crossinline onRepeat: (view: View, animator: Animator?) -> Unit = { _, _ -> }
): AnimationComposer.Listener {
    val listener = object : AnimationComposer.Listener {
        override fun onStart(view: View, animator: Animator?) = onStart(view, animator)
        override fun onEnd(view: View, animator: Animator?) = onEnd(view, animator)
        override fun onCancel(view: View, animator: Animator?) = onCancel(view, animator)
        override fun onRepeat(view: View, animator: Animator?) = onRepeat(view, animator)
    }

    AnimationComposer()
        .setDuration(duration)
        .setInterpolator(interpolator)
        .play(*methods)
        .start(this, listener)

    return listener
}