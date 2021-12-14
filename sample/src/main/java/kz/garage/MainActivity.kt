package kz.garage

import kz.garage.activity.intent.createIntent
import kz.garage.kotlin.simpleName
import kz.garage.samples.BaseNestedModuleActivity
import kz.garage.samples.activity.Activity
import kz.garage.samples.animation.AnimationActivity
import kz.garage.samples.fragment.FragmentActivity
import kz.garage.samples.location.LocationActivity
import kz.garage.samples.recyclerview.RecyclerViewActivity
import kz.garage.samples.window.WindowActivity

class MainActivity : BaseNestedModuleActivity() {

    companion object {
        private val TAG = simpleName()
    }

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun getHeaderTitle(): String = "Entry Point"

    override fun getSamples(): List<Sample> =
        listOf(
            Sample("activity", "Activity", null),
            Sample("animation", "Animation", null),
            Sample("fragment", "Fragment", null),
            Sample("location", "Location", null),
            Sample("recyclerview", "RecyclerView", null),
            Sample("window", "Window", null)
        )

    override fun onSampleClicked(sample: Sample) {
        when (sample.id) {
            "activity" ->
                startActivity(createIntent<Activity>())
            "animation" ->
                startActivity(createIntent<AnimationActivity>())
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