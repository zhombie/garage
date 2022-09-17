package kz.garage.image.load

fun interface ImageLoaderFactory {
    fun getImageLoader(): ImageLoader
}