package kz.garage.samples.recyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kz.garage.R
import kz.garage.kotlin.simpleName

class RecyclerViewActivity : AppCompatActivity() {

    companion object {
        private val TAG = simpleName()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
    }

}