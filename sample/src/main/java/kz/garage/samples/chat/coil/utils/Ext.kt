package kz.garage.samples.chat.coil.utils

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import kz.garage.chat.ui.imageloader.ChatUiImageLoader
import kz.garage.samples.chat.coil.ChatUI

internal inline val Context.imageLoader: ChatUiImageLoader
    get() = ChatUI.getImageLoader(this)

internal inline fun ImageView.load(
    uri: String?,
    imageLoader: ChatUiImageLoader = context.imageLoader,
    builder: ChatUiImageLoader.Request.Builder.() -> Unit = {}
): ChatUiImageLoader.Disposable = loadAny(uri, imageLoader, builder)

internal inline fun ImageView.load(
    uri: Uri?,
    imageLoader: ChatUiImageLoader = context.imageLoader,
    builder: ChatUiImageLoader.Request.Builder.() -> Unit = {}
): ChatUiImageLoader.Disposable = loadAny(uri, imageLoader, builder)

internal inline fun ImageView.loadAny(
    data: Any?,
    imageLoader: ChatUiImageLoader = context.imageLoader,
    builder: ChatUiImageLoader.Request.Builder.() -> Unit = {}
): ChatUiImageLoader.Disposable =
    imageLoader.enqueue(
        ChatUiImageLoader.Request.Builder(context)
            .setData(data)
            .into(this)
            .apply(builder)
            .build()
    )

internal fun ImageView.dispose() {
    context.imageLoader.dispose(this)
}