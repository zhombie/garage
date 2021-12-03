package kz.garage.recyclerview

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.setup(block: RecyclerView.() -> Unit): RecyclerView {
    apply(block)
    return this
}