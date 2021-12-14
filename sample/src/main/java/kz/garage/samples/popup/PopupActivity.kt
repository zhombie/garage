package kz.garage.samples.popup

import kz.garage.R
import kz.garage.Sample
import kz.garage.activity.intent.createIntent
import kz.garage.kotlin.simpleName
import kz.garage.samples.BaseNestedModuleActivity
import kz.garage.samples.popup.tooltip.TooltipActivity

class PopupActivity : BaseNestedModuleActivity() {

    companion object {
        private val TAG = simpleName()
    }

    override fun getLayoutId(): Int = R.layout.activity_popup

    override fun getHeaderTitle(): String = "Popup"

    override fun getSamples(): List<Sample> =
        listOf(
            Sample("tooltip", "Tooltip", null),
        )

    override fun onSampleClicked(sample: Sample) {
        when (sample.id) {
            "tooltip" ->
                startActivity(createIntent<TooltipActivity>())
        }
    }

}