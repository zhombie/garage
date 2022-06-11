package kz.garage.multimedia.store

import android.graphics.BitmapFactory
import kz.garage.multimedia.store.model.Resolution
import java.io.FileNotFoundException
import java.io.InputStream

@Throws(FileNotFoundException::class)
fun InputStream?.getImageResolution(): Resolution? {
    if (this == null) return null
    val options = BitmapFactory.Options().apply {
        inJustDecodeBounds = true
    }
    BitmapFactory.decodeStream(this, null, options)
    return if (options.outWidth > 0 && options.outHeight > 0) {
        Resolution(options.outWidth, options.outHeight)
    } else {
        null
    }
}