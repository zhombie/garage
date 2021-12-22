package kz.garage.recyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import kz.garage.recyclerview.adapter.viewholder.BaseViewHolder

abstract class BaseAdapter<T> constructor(
    private var data: List<T> = emptyList()
) : RecyclerView.Adapter<BaseViewHolder<T>>() {

    init {
        setData(data, true)
    }

    fun getData(): List<T> = data

    fun setData(data: List<T>, notify: Boolean = true) {
        this.data = data

        if (notify) {
            notifyDataSetChanged()
        }
    }

    protected fun getItem(position: Int): T? = data.getOrNull(position)

    protected fun getPosition(item: T): Int = data.indexOf(item)

    override fun getItemCount(): Int = data.size

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract fun onCreateViewHolder(view: View): BaseViewHolder<T>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> =
        onCreateViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(getLayoutId(), parent, false)
        )

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        getItem(position)?.let { holder.onBind(it, position) }
    }

    override fun onViewRecycled(holder: BaseViewHolder<T>) {
        super.onViewRecycled(holder)
        holder.onUnbind()
    }

}