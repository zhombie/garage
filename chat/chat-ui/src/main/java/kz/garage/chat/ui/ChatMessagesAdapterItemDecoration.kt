package kz.garage.chat.ui

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.Dimension
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView

class ChatMessagesAdapterItemDecoration constructor(
    context: Context,
    @Dimension
    private val messageVerticalSpacing: Int = context.resources.getDimensionPixelOffset(R.dimen.chat_ui_message_vertical_margin),
    @Dimension
    private val notificationVerticalMargin: Int = context.resources.getDimensionPixelOffset(R.dimen.chat_ui_notification_vertical_margin)
) : RecyclerView.ItemDecoration() {

    companion object {
        private val TAG = ChatMessagesAdapterItemDecoration::class.java.simpleName
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val adapter = parent.adapter

        if (adapter is ConcatAdapter) {
            val chatMessagesAdapter = adapter.adapters.find { it is ChatMessagesAdapter }

            val holder = parent.getChildViewHolder(view)
            val position = holder.bindingAdapterPosition

//            Logger.debug(TAG, "getItemOffsets() -> position: $position, viewType: $viewType")

            when (chatMessagesAdapter?.getItemViewType(position)) {
                ChatMessagesAdapter.ViewType.NOTIFICATION -> {
                    outRect.top = notificationVerticalMargin
                    outRect.bottom = notificationVerticalMargin
                }
                else -> {
                    outRect.top = messageVerticalSpacing
                    outRect.bottom = messageVerticalSpacing
                }
            }
        }
    }
}