package kz.garage.samples.image.preview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import kz.garage.R
import kz.garage.activity.view.bind
import kz.garage.image.load.dispose
import kz.garage.image.load.load
import kz.garage.image.preview.showImagePreview
import kz.garage.kotlin.simpleNameOf

class PreviewActivity : AppCompatActivity() {

    companion object {
        private val TAG = simpleNameOf<PreviewActivity>()

        private const val IMAGE_URL =
            "https://media.sproutsocial.com/uploads/2017/02/10x-featured-social-media-image-size.png"
    }

    private val imageView by bind<ShapeableImageView>(R.id.imageView)
    private val button by bind<MaterialButton>(R.id.button)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_preview)

        imageView.load(IMAGE_URL)

        button.setOnClickListener {
            showImagePreview(IMAGE_URL)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        imageView.dispose()
    }

}