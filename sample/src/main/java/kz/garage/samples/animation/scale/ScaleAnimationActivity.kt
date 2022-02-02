package kz.garage.samples.animation.scale

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import kz.garage.R
import kz.garage.activity.toast.toast
import kz.garage.activity.view.bind
import kz.garage.animation.scale.setScaleAnimationOnClick
import kz.garage.kotlin.simpleNameOf
import kz.garage.recyclerview.layoutmanager.setLinearLayoutManager

class ScaleAnimationActivity : AppCompatActivity() {

    companion object {
        private val TAG = simpleNameOf<ScaleAnimationActivity>()
    }

    private val button by bind<MaterialButton>(R.id.button)
    private val recyclerView by bind<RecyclerView>(R.id.recyclerView)

    private var adapter: ButtonsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation_scale)

        setupButton()
        setupRecyclerView()
    }

    private fun setupButton() {
        button.setScaleAnimationOnClick {
            toast("Click Action")
        }
    }

    private fun setupRecyclerView() {
        adapter = ButtonsAdapter {
            toast("Click Action: $it")
        }

        recyclerView.setLinearLayoutManager(LinearLayoutManager.HORIZONTAL)
        recyclerView.adapter = adapter

        adapter?.setData((1..15).map { "Button $it" })
    }

}