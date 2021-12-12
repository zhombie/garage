package kz.garage.samples.location

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kz.garage.R
import kz.garage.Sample
import kz.garage.SamplesAdapter
import kz.garage.activity.intent.createIntent
import kz.garage.activity.view.bind
import kz.garage.kotlin.simpleName
import kz.garage.recyclerview.layoutmanager.setVerticalLinearLayoutManager
import kz.garage.recyclerview.setup
import kz.garage.samples.location.compass.CompassActivity
import kz.garage.samples.location.core.CoreActivity
import kz.garage.samples.location.gms.GMSActivity

class LocationActivity : AppCompatActivity() {

    companion object {
        private val TAG = simpleName()
    }

    private val textView by bind<TextView>(R.id.textView)
    private val recyclerView by bind<RecyclerView>(R.id.recyclerView)

    private var samplesAdapter: SamplesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

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
            Sample("core", "Core", null),
            Sample("compass", "Compass", null),
            Sample("gms", "GMS", null),
        )
    }

    private fun onSampleClicked(sample: Sample) {
        when (sample.id) {
            "core" ->
                startActivity(createIntent<CoreActivity>())
            "compass" ->
                startActivity(createIntent<CompassActivity>())
            "gms" ->
                startActivity(createIntent<GMSActivity>())
        }
    }


}