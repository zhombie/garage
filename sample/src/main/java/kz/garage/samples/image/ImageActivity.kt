package kz.garage.samples.image

import kz.garage.BaseNestedModuleActivity
import kz.garage.R
import kz.garage.Sample
import kz.garage.activity.intent.startActivity
import kz.garage.kotlin.simpleNameOf
import kz.garage.samples.image.load.coil.CoilActivity
import kz.garage.samples.image.preview.PreviewActivity

class ImageActivity : BaseNestedModuleActivity() {

    companion object {
        private val TAG = simpleNameOf<ImageActivity>()
    }

    override fun getLayoutId(): Int = R.layout.activity_image

    override fun getHeaderTitle(): String = "Image"

    override fun getSamples(): List<Sample> =
        listOf(
            Sample("coil", "Coil", null),
            Sample("preview", "Preview", null),
        )

    override fun onSampleClicked(sample: Sample) {
        when (sample.id) {
            "coil" ->
                startActivity<CoilActivity>()
            "preview" ->
                startActivity<PreviewActivity>()
        }
    }

}