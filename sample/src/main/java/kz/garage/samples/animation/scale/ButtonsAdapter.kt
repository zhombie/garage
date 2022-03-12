package kz.garage.samples.animation.scale

import android.view.View
import com.google.android.material.button.MaterialButton
import kz.garage.R
import kz.garage.animation.scale.setScaleAnimationOnClick
import kz.garage.recyclerview.adapter.BaseAdapter
import kz.garage.recyclerview.adapter.viewholder.BaseViewHolder
import kz.garage.recyclerview.adapter.viewholder.view.bind

class ButtonsAdapter constructor(
    private val onButtonClick: (item: String) -> Unit
) : BaseAdapter<String>(layoutId = R.layout.cell_button) {

    override fun onCreateViewHolder(view: View): BaseViewHolder<String> = ViewHolder(view)

    private inner class ViewHolder constructor(view: View) : BaseViewHolder<String>(view) {
        private val button by bind<MaterialButton>(R.id.button)

        override fun onBind(item: String, position: Int) {
            super.onBind(item, position)

            button.text = item

            button.setScaleAnimationOnClick {
                onButtonClick.invoke(item)
            }
        }
    }

}