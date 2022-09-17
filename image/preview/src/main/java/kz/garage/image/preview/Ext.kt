package kz.garage.image.preview

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.showImagePreview(
    uri: String,
    caption: String? = null
): ImagePreviewDialogFragment {
    return showImagePreview(
        uri = Uri.parse(uri),
        caption = caption
    )
}

fun AppCompatActivity.showImagePreview(
    uri: Uri,
    caption: String? = null
): ImagePreviewDialogFragment {
    return ImagePreviewDialogFragment.show(
        fragmentManager = supportFragmentManager,
        uri = uri,
        caption = caption
    )
}