package kz.q19.utils.compass

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textview.MaterialTextView
import kz.q19.utils.R
import kz.q19.utils.activity.bind
import kz.q19.utils.location.compass.Compass

class CompassActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, CompassActivity::class.java)
    }

    private val textView by bind<MaterialTextView>(R.id.textView)
    private val textView2 by bind<MaterialTextView>(R.id.textView2)

    private var compass: Compass? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compass)

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