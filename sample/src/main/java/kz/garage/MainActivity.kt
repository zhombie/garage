package kz.garage

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kz.garage.activity.intent.createIntent
import kz.garage.activity.view.bind
import kz.garage.kotlin.simpleName
import kz.garage.recyclerview.layoutmanager.setVerticalLinearLayoutManager
import kz.garage.recyclerview.setup
import kz.garage.samples.activity.Activity
import kz.garage.samples.fragment.FragmentActivity
import kz.garage.samples.location.LocationActivity
import kz.garage.samples.recyclerview.RecyclerViewActivity
import kz.garage.samples.window.WindowActivity

class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = simpleName()
    }

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
                startActivity(createIntent<Activity>())
            "fragment" ->
                startActivity(createIntent<FragmentActivity>())
            "location" ->
                startActivity(createIntent<LocationActivity>())
            "recyclerview" ->
                startActivity(createIntent<RecyclerViewActivity>())
            "window" ->
                startActivity(createIntent<WindowActivity>())
        }
    }

}