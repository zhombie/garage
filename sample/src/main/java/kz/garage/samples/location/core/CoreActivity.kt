package kz.garage.samples.location.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kz.garage.R
import kz.garage.kotlin.simpleNameOf

class CoreActivity : AppCompatActivity() {

    companion object {
        private val TAG = simpleNameOf<CoreActivity>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_core)
    }

}