package kz.garage.image.load

import android.content.Context
import android.net.Uri
import android.widget.ImageView

inline val Context.imageLoader: ImageLoader
    get() = Instance.getImageLoader(this)

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
