package kz.garage.image.load

import android.content.Context

object Instance {

    private var imageLoader: ImageLoader? = null
    private var imageLoaderFactory: ImageLoaderFactory? = null

    @Synchronized
    fun getImageLoader(context: Context?): ImageLoader =
        imageLoader ?: setImageLoaderFactory(context)

    @Synchronized
    fun setImageLoader(loader: ImageLoader) {
        imageLoaderFactory = null
        imageLoader = loader
    }

    @Synchronized
    fun setImageLoader(factory: ImageLoaderFactory) {
        imageLoaderFactory = factory
        imageLoader = null
    }

    @Synchronized
    fun setImageLoaderFactory(context: Context?): ImageLoader {
        imageLoader?.let { return it }

        imageLoader = imageLoaderFactory?.getImageLoader()
            ?: (context?.applicationContext as? ImageLoaderFactory)?.getImageLoader()

        imageLoaderFactory = null

        return requireNotNull(imageLoader)
    }

    @Synchronized
    internal fun reset() {
        imageLoader = null
        imageLoaderFactory = null
    }

}