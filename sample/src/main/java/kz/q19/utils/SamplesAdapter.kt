package kz.q19.utils

import android.view.View
import com.google.android.material.button.MaterialButton
import kz.q19.utils.recyclerview.BaseAdapter
import kz.q19.utils.recyclerview.BaseViewHolder
import kz.q19.utils.recyclerview.bind

class SamplesAdapter constructor(
    samples: List<Sample>,
    private val onClickAction: (sample: Sample, position: Int) -> Unit
) : BaseAdapter<Sample>(samples) {

    override fun getLayoutId(): Int = R.layout.cell_sample

    override fun onCreateViewHolder(view: View): BaseViewHolder<Sample> = ViewHolder(view)

    private inner class ViewHolder constructor(
        view: View
    ) : BaseViewHolder<Sample>(view) {
        private val button by bind<MaterialButton>(R.id.button)

        override fun onBind(item: Sample, position: Int) {
            button.text = item.title

            button.setOnClickListener { onClickAction(item, position) }
        }
    }

}
