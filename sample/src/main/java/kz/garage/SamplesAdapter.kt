package kz.garage

import android.view.View
import com.google.android.material.button.MaterialButton
import kz.garage.recyclerview.adapter.BaseAdapter
import kz.garage.recyclerview.adapter.viewholder.BaseViewHolder
import kz.garage.recyclerview.adapter.viewholder.view.bind

class SamplesAdapter constructor(
    samples: List<Sample>,
    private val onClickAction: (sample: Sample, position: Int) -> Unit
) : BaseAdapter<Sample>(
    layoutId = R.layout.cell_sample,
    data = samples
) {

    override fun onCreateViewHolder(view: View): BaseViewHolder<Sample> = ViewHolder(view)

    private inner class ViewHolder constructor(
        view: View
    ) : BaseViewHolder<Sample>(view) {
        private val button by bind<MaterialButton>(R.id.button)

        override fun onBind(item: Sample, position: Int) {
            button.text = item.title

            button.setOnClickListener { onClickAction(item, position) }
        }

        override fun onUnbind() {
            button.text = null

            button.setOnClickListener(null)
        }
    }

}
