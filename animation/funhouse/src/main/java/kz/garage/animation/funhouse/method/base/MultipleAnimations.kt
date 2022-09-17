package kz.garage.animation.funhouse.method.base

import android.animation.Animator
import android.view.View

internal data class MultipleAnimations constructor(
    val after: List<BaseSingleAnimation>? = null,
    val current: List<BaseSingleAnimation>? = null,
    val before: List<BaseSingleAnimation>? = null
) : BaseAnimation() {

    override fun start(view: View) {
        val animatorSet = createAnimatorSet()

        val listener = getListener()
        if (listener != null) {
            animatorSet.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    listener.onStart(view, animation)
                }

                override fun onAnimationEnd(animation: Animator) {
                    listener.onEnd(view, animation)
                }

                override fun onAnimationCancel(animation: Animator) {
                    listener.onCancel(view, animation)
                }

                override fun onAnimationRepeat(animation: Animator) {
                    listener.onRepeat(view, animation)
                }
            })
        }

        if (current.isNullOrEmpty()) {
            if (after.isNullOrEmpty()) {
                if (!before.isNullOrEmpty()) {
                    animatorSet.playTogether(before.map { it.describe(view) })
                }
            } else {
                if (before.isNullOrEmpty()) {
                    animatorSet.playTogether(after.map { it.describe(view) })
                } else {
                    val mutableAfter = after.toMutableList()
                    animatorSet
                        .play(mutableAfter.removeFirst().describe(view))
                        .apply {
                            if (!mutableAfter.isNullOrEmpty()) {
                                mutableAfter.forEach {
                                    with(it.describe(view))
                                }
                            }

                            if (!before.isNullOrEmpty()) {
                                before.forEach {
                                    before(it.describe(view))
                                }
                            }
                        }
                }
            }
        } else {
            val mutableCurrent = current.toMutableList()
            animatorSet
                .play(mutableCurrent.removeFirst().describe(view))
                .apply {
                    if (!mutableCurrent.isNullOrEmpty()) {
                        mutableCurrent.forEach {
                            with(it.describe(view))
                        }
                    }

                    if (!after.isNullOrEmpty()) {
                        after.forEach {
                            after(it.describe(view))
                        }
                    }

                    if (!before.isNullOrEmpty()) {
                        before.forEach {
                            before(it.describe(view))
                        }
                    }
                }
        }

        getDefinition()?.let { definition ->
            animatorSet.duration = definition.duration
            animatorSet.interpolator = definition.interpolator
        }

        animatorSet.start()
    }

}