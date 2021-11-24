package kz.q19.utils.recyclerview

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * [LinearLayoutManager]
 */

fun RecyclerView.setVerticalLinearLayoutManager(
    reverseLayout: Boolean = false,
    stackFromEnd: Boolean = false
): RecyclerView = setLinearLayoutManager(RecyclerView.VERTICAL, reverseLayout, stackFromEnd)

fun RecyclerView.setHorizontalLinearLayoutManager(
    reverseLayout: Boolean = false,
    stackFromEnd: Boolean = false
): RecyclerView = setLinearLayoutManager(RecyclerView.HORIZONTAL, reverseLayout, stackFromEnd)

fun RecyclerView.setLinearLayoutManager(
    @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL,
    reverseLayout: Boolean = false,
    stackFromEnd: Boolean = false
): RecyclerView {
    layoutManager = LinearLayoutManager(context, orientation, reverseLayout).apply {
        setStackFromEnd(stackFromEnd)
    }
    return this
}

/**
 * [GridLayoutManager]
 */

fun RecyclerView.setVerticalGridLayoutManager(
    spanCount: Int = GridLayoutManager.DEFAULT_SPAN_COUNT,
    reverseLayout: Boolean = false,
    stackFromEnd: Boolean = false
): RecyclerView =
    setGridLayoutManager(spanCount, GridLayoutManager.VERTICAL, reverseLayout, stackFromEnd)

fun RecyclerView.setHorizontalGridLayoutManager(
    spanCount: Int = GridLayoutManager.DEFAULT_SPAN_COUNT,
    reverseLayout: Boolean = false,
    stackFromEnd: Boolean = false
): RecyclerView =
    setGridLayoutManager(spanCount, GridLayoutManager.HORIZONTAL, reverseLayout, stackFromEnd)

fun RecyclerView.setGridLayoutManager(
    spanCount: Int = GridLayoutManager.DEFAULT_SPAN_COUNT,
    @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL,
    reverseLayout: Boolean = false,
    stackFromEnd: Boolean = false
): RecyclerView {
    layoutManager = GridLayoutManager(context, spanCount, orientation, reverseLayout).apply {
        setStackFromEnd(stackFromEnd)
    }
    return this
}

/**
 * [StaggeredGridLayoutManager]
 */

fun RecyclerView.setVerticalStaggeredGridLayoutManager(
    spanCount: Int = 1,
    reverseLayout: Boolean = false
): RecyclerView =
    setStaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL, reverseLayout)

fun RecyclerView.setHorizontalStaggeredGridLayoutManager(
    spanCount: Int = 1,
    reverseLayout: Boolean = false
): RecyclerView =
    setStaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.HORIZONTAL, reverseLayout)

fun RecyclerView.setStaggeredGridLayoutManager(
    spanCount: Int = 1,
    orientation: Int = StaggeredGridLayoutManager.VERTICAL,
    reverseLayout: Boolean = false
): RecyclerView {
    layoutManager = StaggeredGridLayoutManager(spanCount, orientation).apply {
        setReverseLayout(reverseLayout)
    }
    return this
}