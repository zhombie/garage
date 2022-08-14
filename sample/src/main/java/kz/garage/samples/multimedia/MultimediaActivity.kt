package kz.garage.samples.multimedia

import kz.garage.BaseNestedModuleActivity
import kz.garage.R
import kz.garage.Sample
import kz.garage.activity.intent.startActivity
import kz.garage.kotlin.simpleNameOf
import kz.garage.samples.multimedia.store.MultimediaStoreActivity

class MultimediaActivity : BaseNestedModuleActivity() {

    companion object {
        private val TAG = simpleNameOf<MultimediaActivity>()
    }

    override fun getLayoutId(): Int = R.layout.activity_multimedia

    override fun getHeaderTitle(): String = "Multimedia"

    override fun getSamples(): List<Sample> =
        listOf(
            Sample("store", "Store", null)
        )

    override fun onSampleClicked(sample: Sample) {
        when (sample.id) {
            "store" ->
                startActivity<MultimediaStoreActivity>()
        }
    }

}