package kz.garage.samples.image.coil

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import kz.garage.R
import kz.garage.activity.view.bind
import kz.garage.image.load.Transformation
import kz.garage.image.load.coil.dispose
import kz.garage.image.load.coil.load
import kz.garage.kotlin.simpleNameOf

class CoilActivity : AppCompatActivity() {

    companion object {
        private val TAG = simpleNameOf<CoilActivity>()
    }

    private val imageView by bind<ShapeableImageView>(R.id.imageView)
    private val textView by bind<MaterialTextView>(R.id.textView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_coil)

        textView.text = null

        imageView.load("https://media.sproutsocial.com/uploads/2017/02/10x-featured-social-media-image-size.png") {
            setTransformations(Transformation.CircleCrop)

            listener(
                onStart = {
                    textView.text = textView.text.toString() + "\nonStart()"
                },
                onCancel = {
                    textView.text = textView.text.toString() + "\nonCancel()"
                },
                onError = { _, _ ->
                    textView.text = textView.text.toString() + "\nonError()"
                },
                onSuccess = { _, _ ->
                    textView.text = textView.text.toString() + "\nonSuccess()"
                }
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        textView.text = null

        imageView.dispose()
    }

}