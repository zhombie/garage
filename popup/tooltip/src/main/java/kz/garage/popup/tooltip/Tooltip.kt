package kz.garage.popup.tooltip

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Rect
import android.text.Spanned
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes

// TODO: Do refactoring
// Inspired by: https://github.com/jesualex/sTooltip
class Tooltip private constructor(
    private val activity: Activity,
    private val refView: View,
    closeOnParentDetach: Boolean
) {

    companion object {
        fun on(
            refView: View,
            closeOnParentDetach: Boolean = true
        ): TooltipBuilder {
            val activity = requireNotNull(getActivity(refView.context)) { "Something went wrong!" }
            return TooltipBuilder(Tooltip(activity, refView, closeOnParentDetach))
        }

        private fun getActivity(context: Context): Activity? {
            return when (context) {
                is Activity -> context
                is ContextWrapper -> getActivity(context.baseContext)
                else -> null
            }
        }
    }

    internal val tooltipView: TooltipView = TooltipView(activity)
    internal val overlay: FrameLayout = FrameLayout(activity)
    internal var clickToHide: Boolean = true
    internal var displayListener: DisplayListener? = null
    internal var tooltipClickListener: TooltipClickListener? = null
    internal var refViewClickListener: TooltipClickListener? = null
    internal var animIn = 0
    internal var animOut = 0

    init {
        tooltipView.setOnClickListener {
            tooltipClickListener?.onClick(tooltipView, this)

            if (clickToHide) {
                close()
            }
        }

        if (closeOnParentDetach) {
            refView.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
                override fun onViewAttachedToWindow(view: View) {
                }

                override fun onViewDetachedFromWindow(view: View) {
                    closeNow()
                    view.removeOnAttachStateChangeListener(this)
                }
            })
        }
    }

    internal fun getTextView(): TextView =
        tooltipView.childView.textView

    internal fun getStartImageView(): ImageView =
        tooltipView.childView.iconStart

    internal fun getEndImageView(): ImageView =
        tooltipView.childView.iconEnd

    fun show(duration: Long = 0, text: String? = null): Tooltip {
        refView.post {
            closeNow()

            tooltipView.childView.attach()
            text?.let { getTextView().text = it }

            val tooltipParent = activity.window.decorView as ViewGroup
            val rect = Rect()

            refView.getGlobalVisibleRect(rect)

            tooltipView.setup(rect, tooltipParent)

            tooltipParent.addView(overlay, ViewGroup.LayoutParams(
                tooltipParent.width,
                tooltipParent.height
            ))

            if (animIn == 0) {
                displayListener?.onDisplay(tooltipView, true)
            } else {
                val animation = AnimationUtils.loadAnimation(tooltipView.context, animIn)

                animation.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationRepeat(p0: Animation?) {}

                    override fun onAnimationEnd(p0: Animation?) {
                        displayListener?.onDisplay(tooltipView, true)
                    }

                    override fun onAnimationStart(p0: Animation?) {}
                })

                tooltipView.startAnimation(animation)
            }

            if (duration > 0) {
                tooltipView.postDelayed({ close() }, duration)
            }
        }

        return this
    }

    fun show(duration: Long, @StringRes text: Int): Tooltip {
        getTextView().setText(text)
        return show(duration)
    }

    fun show(duration: Long, text: Spanned): Tooltip {
        getTextView().text = text
        return show(duration)
    }

    fun show(@StringRes text: Int): Tooltip {
        getTextView().setText(text)
        return show()
    }

    fun show(text: Spanned): Tooltip {
        getTextView().text = text
        return show()
    }

    fun show(text: String): Tooltip {
        getTextView().text = text
        return show()
    }

    fun close() {
        if (animOut == 0) {
            overlay.post { closeNow() }
        } else {
            val animation = AnimationUtils.loadAnimation(tooltipView.context, animOut)

            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(p0: Animation?) {}

                override fun onAnimationEnd(p0: Animation?) {
                    overlay.post { closeNow() }
                }

                override fun onAnimationStart(p0: Animation?) {}
            })

            tooltipView.startAnimation(animation)
        }
    }

    fun closeNow() {
        val parent = overlay.parent

        if (parent != null && parent is ViewGroup) {
            overlay.post {
                parent.removeView(overlay)
                displayListener?.onDisplay(tooltipView, false)
            }
        }
    }

    fun isShown() = overlay.parent != null

    internal fun initTargetClone() {
        val targetGhostView = TargetView(activity)
        targetGhostView.setTarget(refView)
        overlay.addView(targetGhostView)
        targetGhostView.setOnClickListener {
            refViewClickListener?.onClick(targetGhostView, this)
        }
    }

    internal fun setOverlayListener(overlayClickListener: TooltipClickListener) {
        overlay.setOnClickListener { overlayClickListener.onClick(it, this) }
    }

    fun moveTooltip(x: Int, y: Int) {
        overlay.translationY -= y
        overlay.translationX -= x
    }

}