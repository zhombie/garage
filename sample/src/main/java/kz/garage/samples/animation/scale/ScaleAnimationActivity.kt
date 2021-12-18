package kz.garage.samples.animation.scale

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import kz.garage.R
import kz.garage.activity.toast.toast
import kz.garage.activity.view.bind
import kz.garage.animation.scale.setScaleAnimationOnClick
import kz.garage.kotlin.simpleNameOf

class ScaleAnimationActivity : AppCompatActivity() {

    companion object {
        private val TAG = simpleNameOf<ScaleAnimationActivity>()
    }

    private val button by bind<MaterialButton>(R.id.button)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation_scale)

        button.setScaleAnimationOnClick {
            toast("Animation end")
        }
    }

}