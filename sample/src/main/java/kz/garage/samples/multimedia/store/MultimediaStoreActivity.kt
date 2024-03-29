package kz.garage.samples.multimedia.store

import android.os.Build
import android.os.Bundle
import androidx.core.os.bundleOf
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import kz.garage.R
import kz.garage.activity.view.bind
import kz.garage.locale.base.LocaleManagerBaseActivity
import kz.garage.multimedia.store.model.Content
import kz.garage.multimedia.store.model.Image

class MultimediaStoreActivity : LocaleManagerBaseActivity() {

    private val button by bind<MaterialButton>(R.id.button)
    private val textView by bind<MaterialTextView>(R.id.textView)
    private val textView2 by bind<MaterialTextView>(R.id.textView2)

    private val bundle: Bundle = bundleOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multimedia_store)

        setup(null)

        button.setOnClickListener {
            if (bundle.containsKey("image")) {
                bundle.clear()
            } else {
                bundle.putParcelable(
                    "image",
                    Image(
                        id = Content.generateId(),
                        title = "Image",
                        remoteFile = Content.RemoteFile("https://google.com"),
                    )
                )
            }

            setup(
                if (Build.VERSION.SDK_INT >= 33) {
                    bundle.getParcelable("image", Image::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    bundle.getParcelable("image")
                }
            )
        }
    }

    private fun setup(image: Image?) {
        textView.text = image?.toString()

        textView2.text = bundle.toString()

        button.text = if (image == null) {
            "Put into bundle"
        } else {
            "Clear bundle"
        }
    }

}