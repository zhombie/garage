package kz.gov.mia.sos.widget.ui.presentation.common.keyboard

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.garage.kotlin.simpleNameOf

internal class InlineKeyboardAdapterItemDecoration : RecyclerView.ItemDecoration() {

    companion object {
        private val TAG = simpleNameOf<InlineKeyboardAdapterItemDecoration>()
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val adapter = (parent.adapter as? InlineKeyboardAdapter?) ?: return

        val itemCount = adapter.itemCount

        val position = parent.getChildAdapterPosition(view)

        val spanCount = getSpanCount(parent)

        outRect.top = 25

        if (itemCount == 2) {
            if (position == 0) {
                outRect.right = 25
            }
        } else {
            if (isLastInRow(position, spanCount)) {
                outRect.right = 0
            } else {
                if (getItemSpanSize(parent, position) == itemCount - 1) {
                    outRect.right = 0
                } else {
                    outRect.right = 25
                }
            }
        }
    }

    private fun isInTheFirstRow(position: Int, spanCount: Int): Boolean {
        return position < spanCount
    }

    private fun isFirstInRow(position: Int, spanCount: Int): Boolean {
        return position % spanCount == 0
    }

    private fun isLastInRow(position: Int, spanCount: Int): Boolean {
        return isFirstInRow(position + 1, spanCount)
    }

    private fun getSpanCount(parent: RecyclerView): Int {
        return getGridLayoutManager(parent)?.spanCount ?: 1
    }

    private fun getItemSpanSize(parent: RecyclerView, position: Int): Int {
        return getGridLayoutManager(parent)?.spanSizeLookup?.getSpanSize(position) ?: 1
    }

    private fun getGridLayoutManager(parent: RecyclerView): GridLayoutManager? {
        val layoutManager = parent.layoutManager
        return if (layoutManager is GridLayoutManager) layoutManager else null
    }

}