package kz.garage

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kz.garage.activity.bind
import kz.garage.recyclerview.setVerticalLinearLayoutManager
import kz.garage.recyclerview.setup
import kz.garage.samples.activity.Activity
import kz.garage.samples.fragment.FragmentActivity
import kz.garage.samples.location.LocationActivity
import kz.garage.samples.recyclerview.RecyclerViewActivity
import kz.garage.samples.window.WindowActivity

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
        val samples = getSamples()
        recyclerView.setup {
            setVerticalLinearLayoutManager()

            samplesAdapter = SamplesAdapter(samples) { sample, _ ->
                onSampleClicked(sample)
            }
            adapter = samplesAdapter
        }
    }

    private fun getSamples(): List<Sample> {
        return listOf(
            Sample("activity", "Activity", null),
            Sample("fragment", "Fragment", null),
            Sample("location", "Location", null),
            Sample("recyclerview", "RecyclerView", null),
            Sample("window", "Window", null),
        )
    }

    private fun onSampleClicked(sample: Sample) {
        when (sample.id) {
            "activity" ->
                startActivity(Activity.newIntent(this))
            "fragment" ->
                startActivity(FragmentActivity.newIntent(this))
            "location" ->
                startActivity(LocationActivity.newIntent(this))
            "recyclerview" ->
                startActivity(RecyclerViewActivity.newIntent(this))
            "window" ->
                startActivity(WindowActivity.newIntent(this))
        }
    }

}