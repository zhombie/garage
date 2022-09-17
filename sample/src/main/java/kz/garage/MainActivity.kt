package kz.garage

import kz.garage.activity.intent.startActivity
import kz.garage.kotlin.simpleNameOf
import kz.garage.samples.activity.Activity
import kz.garage.samples.animation.AnimationActivity
import kz.garage.samples.fragment.FragmentActivity
import kz.garage.samples.image.ImageActivity
import kz.garage.samples.locale.LocaleActivity
import kz.garage.samples.location.LocationActivity
import kz.garage.samples.multimedia.MultimediaActivity
import kz.garage.samples.permission.PermissionActivity
import kz.garage.samples.popup.PopupActivity
import kz.garage.samples.recyclerview.RecyclerViewActivity
import kz.garage.samples.retrofit.RetrofitActivity
import kz.garage.samples.window.WindowActivity

class MainActivity : BaseNestedModuleActivity() {

    companion object {
        private val TAG = simpleNameOf<MainActivity>()
    }

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun getHeaderTitle(): String = "Entry Point"

    override fun getSamples(): List<Sample> =
        listOf(
            Sample("activity", "Activity", null),
            Sample("animation", "Animation", null),
            Sample("fragment", "Fragment", null),
            Sample("image", "Image", null),
            Sample("locale", "Locale", null),
            Sample("location", "Location", null),
            Sample("multimedia", "Multimedia", null),
            Sample("permission", "Permission", null),
            Sample("popup", "Popup", null),
            Sample("recyclerview", "RecyclerView", null),
            Sample("retrofit", "Retrofit", null),
            Sample("window", "Window", null)
        )

    override fun onSampleClicked(sample: Sample) {
        when (sample.id) {
            "activity" ->
                startActivity<Activity>()
            "animation" ->
                startActivity<AnimationActivity>()
            "fragment" ->
                startActivity<FragmentActivity>()
            "image" ->
                startActivity<ImageActivity>()
            "locale" ->
                startActivity<LocaleActivity>()
            "location" ->
                startActivity<LocationActivity>()
            "multimedia" ->
                startActivity<MultimediaActivity>()
            "permission" ->
                startActivity<PermissionActivity>()
            "popup" ->
                startActivity<PopupActivity>()
            "recyclerview" ->
                startActivity<RecyclerViewActivity>()
            "retrofit" ->
                startActivity<RetrofitActivity>()
            "window" ->
                startActivity<WindowActivity>()
        }
    }

}