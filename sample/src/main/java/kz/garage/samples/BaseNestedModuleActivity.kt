package kz.garage.samples

import android.os.Bundle
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kz.garage.R
import kz.garage.Sample
import kz.garage.SamplesAdapter
import kz.garage.activity.view.bind
import kz.garage.recyclerview.layoutmanager.setVerticalLinearLayoutManager
import kz.garage.recyclerview.setup

abstract class BaseNestedModuleActivity : AppCompatActivity() {

    protected val textView by bind<TextView>(R.id.textView)
    protected val recyclerView by bind<RecyclerView>(R.id.recyclerView)

    protected var samplesAdapter: SamplesAdapter? = null

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract fun getHeaderTitle(): String

    protected abstract fun getSamples(): List<Sample>

    protected abstract fun onSampleClicked(sample: Sample)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())

        setupTitleView()
        setupRecyclerView()
    }

    private fun setupTitleView() {
        textView.text = getHeaderTitle()
    }

    private fun setupRecyclerView() {
        recyclerView.setup {
            setVerticalLinearLayoutManager()

            samplesAdapter = SamplesAdapter(getSamples()) { sample, _ ->
                onSampleClicked(sample)
            }
            adapter = samplesAdapter
        }
    }

}