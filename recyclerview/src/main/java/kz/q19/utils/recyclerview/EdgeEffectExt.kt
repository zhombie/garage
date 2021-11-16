package kz.q19.utils.recyclerview

import android.widget.EdgeEffect
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.setOverscrollColor(@ColorRes colorResId: Int): EdgeEffect {
    val edgeEffect = EdgeEffect(context).apply {
        color = ContextCompat.getColor(context, colorResId)
    }
    edgeEffectFactory = object : RecyclerView.EdgeEffectFactory() {
        override fun createEdgeEffect(view: RecyclerView, direction: Int): EdgeEffect {
            return edgeEffect
        }
    }
    return edgeEffect
}