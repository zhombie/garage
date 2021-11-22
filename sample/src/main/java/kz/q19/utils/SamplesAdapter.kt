package kz.q19.utils

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import kz.q19.utils.recyclerview.bind
import kz.q19.utils.view.inflate

class SamplesAdapter constructor(
    private val samples: List<Sample>,
    private val onClick: (sample: Sample) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private fun getItem(position: Int): Sample {
        return samples[position]
    }

    override fun getItemCount(): Int = samples.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(parent.inflate(R.layout.cell_sample))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(getItem(position))
        }
    }

    private inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val button by bind<MaterialButton>(R.id.button)

        fun bind(sample: Sample) {
            button.text = sample.title

            button.setOnClickListener { onClick(sample) }
        }
    }

}
