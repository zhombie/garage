package kz.garage.samples.window

import kz.garage.R
import kz.garage.Sample
import kz.garage.activity.intent.createIntent
import kz.garage.kotlin.simpleName
import kz.garage.samples.BaseNestedModuleActivity
import kz.garage.samples.window.keyboard.KeyboardActivity
import kz.garage.samples.window.size.WindowSizeActivity

class WindowActivity : BaseNestedModuleActivity() {

    companion object {
        private val TAG = simpleName()
    }

    override fun getLayoutId(): Int = R.layout.activity_window

    override fun getHeaderTitle(): String = "Window"

    override fun getSamples(): List<Sample> =
        listOf(
            Sample("keyboard", "Keyboard", null),
            Sample("window_size", "Window size", null)
        )

    override fun onSampleClicked(sample: Sample) {
        when (sample.id) {
            "keyboard" ->
                startActivity(createIntent<KeyboardActivity>())
            "window_size" ->
                startActivity(createIntent<WindowSizeActivity>())
        }
    }

}