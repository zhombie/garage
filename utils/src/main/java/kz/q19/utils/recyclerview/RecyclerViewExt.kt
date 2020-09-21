package kz.q19.utils.recyclerview

import android.widget.EdgeEffect
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.setOverScrollColor(@ColorRes colorResId: Int) {
    edgeEffectFactory = object : RecyclerView.EdgeEffectFactory() {
        override fun createEdgeEffect(view: RecyclerView, direction: Int): EdgeEffect {
            return EdgeEffect(view.context).apply {
                color = ContextCompat.getColor(context, colorResId)
            }
        }
    }
}

fun RecyclerView.disableChangeAnimations() {
//    (itemAnimator as? SimpleItemAnimator?)?.supportsChangeAnimations = false
    itemAnimator = null
}