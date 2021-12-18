package kz.garage.samples.window.size

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kz.garage.R
import kz.garage.activity.view.bind
import kz.garage.kotlin.simpleNameOf
import kz.garage.window.computeMaximumWindowSize
import kz.garage.window.computeCurrentWindowSize

@SuppressLint("SetTextI18n")
class WindowSizeActivity : AppCompatActivity() {

    companion object {
        private val TAG = simpleNameOf<WindowSizeActivity>()
    }

    private val textView by bind<TextView>(R.id.textView)
    private val textView2 by bind<TextView>(R.id.textView2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_window_size)

        val size = computeCurrentWindowSize()
        textView.text = "Current: ${size.width} x ${size.height}"

        val maxSize = computeMaximumWindowSize()
        textView2.text = "Maximum: ${maxSize.width} x ${maxSize.height}"
    }

}