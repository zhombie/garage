package kz.garage.samples.location

import kz.garage.R
import kz.garage.Sample
import kz.garage.activity.intent.createIntent
import kz.garage.kotlin.simpleName
import kz.garage.samples.BaseNestedModuleActivity
import kz.garage.samples.location.compass.CompassActivity
import kz.garage.samples.location.core.CoreActivity
import kz.garage.samples.location.gms.GMSActivity

class LocationActivity : BaseNestedModuleActivity() {

    companion object {
        private val TAG = simpleName()
    }

    override fun getLayoutId(): Int = R.layout.activity_location

    override fun getHeaderTitle(): String = "Location"

    override fun getSamples(): List<Sample> =
        listOf(
            Sample("core", "Core", null),
            Sample("compass", "Compass", null),
            Sample("gms", "GMS", null)
        )

    override fun onSampleClicked(sample: Sample) {
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