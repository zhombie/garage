package kz.garage.image.load.coil

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import kz.garage.image.load.Disposable
import kz.garage.image.load.ImageLoader
import kz.garage.image.load.Request

inline val Context.imageLoader: ImageLoader
    get() = Coil.getImageLoader(this)

inline fun ImageView.load(
    uri: String?,
    imageLoader: ImageLoader = context.imageLoader,
    builder: Request.Builder.() -> Unit = {}
): Disposable = loadAny(uri, imageLoader, builder)

inline fun ImageView.load(
    uri: Uri?,
    imageLoader: ImageLoader = context.imageLoader,
    builder: Request.Builder.() -> Unit = {}
): Disposable = loadAny(uri, imageLoader, builder)

inline fun ImageView.loadAny(
    data: Any?,
    imageLoader: ImageLoader = context.imageLoader,
    builder: Request.Builder.() -> Unit = {}
): Disposable =
    imageLoader.enqueue(
        Request.Builder(context)
            .setData(data)
            .into(this)
            .apply(builder)
            .build()
    )

fun ImageView.dispose() {
    context.imageLoader.dispose(this)
}
