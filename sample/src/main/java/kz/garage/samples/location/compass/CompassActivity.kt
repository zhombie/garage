package kz.garage.samples.location.compass

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textview.MaterialTextView
import kz.garage.R
import kz.garage.activity.view.bind
import kz.garage.kotlin.simpleNameOf
import kz.garage.location.compass.Compass

class CompassActivity : AppCompatActivity() {

    companion object {
        private val TAG = simpleNameOf<CompassActivity>()
    }

    private val textView by bind<MaterialTextView>(R.id.textView)
    private val textView2 by bind<MaterialTextView>(R.id.textView2)

    private var compass: Compass? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_compass)

        textView.text = "Undefined"
        textView2.text = "Undefined"

        compass = Compass.newInstance(this)
        compass?.start()

        compass?.getAngle()?.observe(this) {
            textView.text = it.toString()
            textView2.text = ((it - 180) % 360).toString()
        }
    }

    override fun onDestroy() {
        compass?.stop()
        compass = null

        super.onDestroy()

        textView.text = "Undefined"
        textView2.text = "Undefined"
    }

}