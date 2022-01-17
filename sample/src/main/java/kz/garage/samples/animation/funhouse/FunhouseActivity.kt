package kz.garage.samples.animation.funhouse

import android.os.Bundle
import android.util.Log
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import kz.garage.R
import kz.garage.activity.view.bind
import kz.garage.animation.funhouse.AnimationComposer
import kz.garage.animation.funhouse.animate
import kz.garage.animation.funhouse.method.Method
import kz.garage.kotlin.simpleNameOf

class FunhouseActivity : AppCompatActivity() {

    companion object {
        private val TAG = simpleNameOf<FunhouseActivity>()
    }

    private val button by bind<MaterialButton>(R.id.button)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation_funhouse)

        val isExtensionFunctionUsed = false

        button.setOnClickListener {
            if (isExtensionFunctionUsed) {
                it.animate(
                    Method.Attention.Wobble,
                    duration = 175L,
                    interpolator = AccelerateDecelerateInterpolator(),
                    onStart = { _, _ ->
                        Log.d(TAG, "onStart()")
                    },
                    onEnd = { _, _ ->
                        Log.d(TAG, "onEnd()")
                    },
                    onCancel = { _, _ ->
                        Log.d(TAG, "onCancel()")
                    },
                    onRepeat = { _, _ ->
                        Log.d(TAG, "onRepeat()")
                    }
                )
            } else {
                AnimationComposer()
                    .setDuration(175L)
                    .setInterpolator(AccelerateDecelerateInterpolator())
                    .play(Method.Attention.Wobble)
                    .doOnStart { _, _ ->
                        Log.d(TAG, "doOnStart()")
                    }
                    .start(it)
            }
        }
    }

}