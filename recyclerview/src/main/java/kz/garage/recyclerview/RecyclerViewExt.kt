package kz.garage.recyclerview

import androidx.recyclerview.widget.RecyclerView

inline fun RecyclerView.setup(
    block: RecyclerView.() -> Unit
): RecyclerView {
    apply(block)
    return this
}