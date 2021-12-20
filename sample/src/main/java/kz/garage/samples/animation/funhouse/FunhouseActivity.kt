package kz.garage.samples.animation.funhouse

import android.os.Bundle
import android.util.Log
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import kz.garage.R
import kz.garage.activity.view.bind
import kz.garage.animation.funhouse.AnimationComposer
import kz.garage.animation.funhouse.method.Method
import kz.garage.kotlin.simpleNameOf
import kotlin.reflect.KClass
import kotlin.reflect.safeCast

class FunhouseActivity : AppCompatActivity() {

    companion object {
        private val TAG = simpleNameOf<FunhouseActivity>()
    }

    private val button by bind<MaterialButton>(R.id.button)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation_funhouse)

        val list = Method::class.flatten()

        Log.d(TAG, "$list")

        button.setOnClickListener {
            AnimationComposer()
                .setMethod(Method.Attention.Wobble)
                .setDuration(175L)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .start(it)
        }
    }

    private inline fun <reified T : Any> KClass<T>.flatten(): List<KClass<out T>> {
        val subclasses = mutableListOf<KClass<out T>?>()
        Log.d(TAG, "sealedSubclasses: $sealedSubclasses")
        this::class.sealedSubclasses.forEach { kClass ->
            Log.d(TAG, "sealedSubclasses: $kClass")

            if (kClass.isSealed) {
                kClass.sealedSubclasses.forEach {
                    Log.d(TAG, "sealedSubclasses -> sealedSubclasses: $it")

                    subclasses.add(it.safeCast(kClass::class))
                }
            } else {
                subclasses.add(kClass.safeCast(T::class))
            }
        }
        return subclasses.filterNotNull()
    }

}