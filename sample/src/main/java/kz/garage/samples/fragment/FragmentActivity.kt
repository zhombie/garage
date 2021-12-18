package kz.garage.samples.fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kz.garage.R
import kz.garage.kotlin.simpleNameOf

class FragmentActivity : AppCompatActivity() {

    companion object {
        private val TAG = simpleNameOf<FragmentActivity>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
    }

}