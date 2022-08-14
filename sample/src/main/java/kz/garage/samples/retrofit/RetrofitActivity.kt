package kz.garage.samples.retrofit

import kz.garage.BaseNestedModuleActivity
import kz.garage.R
import kz.garage.Sample
import kz.garage.activity.intent.startActivity
import kz.garage.kotlin.simpleNameOf
import kz.garage.samples.retrofit.download.RetrofitDownloadActivity

class RetrofitActivity : BaseNestedModuleActivity() {

    companion object {
        private val TAG = simpleNameOf<RetrofitActivity>()
    }

    override fun getLayoutId(): Int = R.layout.activity_retrofit

    override fun getHeaderTitle(): String = "Retrofit"

    override fun getSamples(): List<Sample> =
        listOf(
            Sample("download", "Download", null),
        )

    override fun onSampleClicked(sample: Sample) {
        when (sample.id) {
            "download" ->
                startActivity<RetrofitDownloadActivity>()
        }
    }

}