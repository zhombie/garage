package kz.garage.image.load

import android.widget.ImageView

interface ImageLoader {

    fun enqueue(request: Request): Disposable
    suspend fun execute(request: Request): Response

    fun dispose(imageView: ImageView)

    fun clearCache()

}