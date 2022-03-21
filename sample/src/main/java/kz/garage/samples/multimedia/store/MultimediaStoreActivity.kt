package kz.garage.samples.multimedia.store

import android.net.Uri
import android.os.Bundle
import androidx.core.os.bundleOf
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import kz.garage.R
import kz.garage.activity.view.bind
import kz.garage.locale.base.LocaleManagerBaseActivity
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
            val image = bundle.getParcelable<Image>("image")

            if (image == null) {
                bundle.putParcelable(
                    "image",
                    Image(
                        uri = Uri.EMPTY,
                        displayName = "Display name"
                    )
                )
            } else {
                bundle.clear()
            }

            setup(bundle.getParcelable("image"))
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