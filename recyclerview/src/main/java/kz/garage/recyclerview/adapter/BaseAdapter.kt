package kz.garage.recyclerview.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import kz.garage.recyclerview.adapter.viewholder.BaseViewHolder

abstract class BaseAdapter<T> constructor(
    @LayoutRes
    val layoutId: Int,
    private var data: List<T> = emptyList()
) : RecyclerView.Adapter<BaseViewHolder<T>>() {

    init {
        setData(data, true)
    }

    fun getData(): List<T> = data

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<T>?, notify: Boolean = true) {
        this.data = data ?: emptyList()

        if (notify) {
            notifyDataSetChanged()
        }
    }

    fun getItem(position: Int): T? = data.getOrNull(position)

    fun getPosition(item: T): Int = data.indexOf(item)

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> =
        onCreateViewHolder(inflateView(parent, layoutId))

    abstract fun onCreateViewHolder(view: View): BaseViewHolder<T>

    private fun inflateView(
        parent: ViewGroup,
        @LayoutRes layoutId: Int
    ): View = LayoutInflater.from(parent.context)
        .inflate(layoutId, parent, false)

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        getItem(position)?.let { holder.onBind(it, position) }
    }

    override fun onViewRecycled(holder: BaseViewHolder<T>) {
        super.onViewRecycled(holder)

        holder.onUnbind()
    }

}