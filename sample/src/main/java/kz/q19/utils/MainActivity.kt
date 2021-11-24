package kz.q19.utils

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kz.q19.utils.activity.bind
import kz.q19.utils.compass.CompassActivity
import kz.q19.utils.recyclerview.RecyclerViewActivity
import kz.q19.utils.recyclerview.setVerticalLinearLayoutManager
import kz.q19.utils.recyclerview.setup

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