package kz.garage

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kz.garage.activity.bind
import kz.garage.compass.CompassActivity
import kz.garage.recyclerview.RecyclerViewActivity
import kz.garage.recyclerview.setVerticalLinearLayoutManager
import kz.garage.recyclerview.setup

class MainActivity : AppCompatActivity() {

    private val textView by bind<TextView>(R.id.textView)
    private val recyclerView by bind<RecyclerView>(R.id.recyclerView)

    private var samplesAdapter: SamplesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupTitleView()
        setupRecyclerView()
    }

    private fun setupTitleView() {
        textView.isVisible = false
    }

    private fun setupRecyclerView() {
        val data = generateDummyData()
        recyclerView.setup {
            setVerticalLinearLayoutManager()

            samplesAdapter = SamplesAdapter(data) { sample, _ ->
                onSampleClicked(sample)
            }
            adapter = samplesAdapter
        }
    }

    private fun generateDummyData(): List<Sample> {
        return listOf(
            Sample("compass", "Compass", null),
            Sample("recyclerview", "RecyclerView", null),
        )
    }

    private fun onSampleClicked(sample: Sample) {
        when (sample.id) {
            "compass" ->
                startActivity(CompassActivity.newIntent(this))
            "recyclerview" ->
                startActivity(RecyclerViewActivity.newIntent(this))
        }
    }

}