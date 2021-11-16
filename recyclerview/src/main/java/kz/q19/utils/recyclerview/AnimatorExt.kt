package kz.q19.utils.recyclerview

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.disableChangeAnimations() {
    itemAnimator = null
}