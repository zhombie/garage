package kz.garage.recyclerview

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

fun RecyclerView.scrollToBottom() {
    val lastItemPosition = (adapter?.itemCount ?: 0) - 1

    layoutManager?.scrollToPositionWithOffset(lastItemPosition, 0)

    post {
        val target = layoutManager?.findViewByPosition(lastItemPosition)
        if (target != null) {
            // then scroll to specific offset
            val offset = measuredHeight - target.measuredHeight
            layoutManager?.scrollToPositionWithOffset(lastItemPosition, offset)
        }
    }
}

fun RecyclerView.LayoutManager.scrollToPositionWithOffset(position: Int, offset: Int) {
    when (this) {
        is LinearLayoutManager ->
            scrollToPositionWithOffset(position, offset)
        is GridLayoutManager ->
            scrollToPositionWithOffset(position, offset)
        is StaggeredGridLayoutManager ->
            scrollToPositionWithOffset(position, offset)
        else ->
            scrollToPosition(position)
    }
}