package kz.garage.chat.ui.imageloader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.MainThread
import androidx.annotation.Px
import androidx.appcompat.content.res.AppCompatResources

interface ChatUiImageLoader {
    fun interface Factory {
        fun getImageLoader(): ChatUiImageLoader
    }

    data class Request internal constructor(
        val context: Context,
        val bitmapConfig: Bitmap.Config,
        val data: Any,
        val crossfade: Crossfade,
        val errorDrawable: Drawable?,
        val placeholderDrawable: Drawable?,
        val scale: Scale,
        val size: Size,
        val imageView: ImageView,
        val listener: Listener?
    ) {

        open class Crossfade constructor(
            val duration: Int,
            val isEnabled: Boolean
        ) {
            companion object {
                const val DEFAULT_DURATION = 100
            }

            class DefaultEnabled : Crossfade(DEFAULT_DURATION, true)

            class Disabled : Crossfade(0, false)
        }

        enum class Scale {
            FILL,
            FIT
        }

        sealed interface Size {
            object Inherit : Size

            object Original : Size

            data class Pixel constructor(
                @Px val width: Int,
                @Px val height: Int
            ) : Size {

                init {
                    require(width > 0 && height > 0) { "width and height must be > 0." }
                }

            }
        }

        class Builder constructor(private val context: Context) {
            private var bitmapConfig: Bitmap.Config? = null

            private var data: Any? = null

            private var crossfade: Crossfade? = null

            private var errorDrawable: Drawable? = null
            private var placeholderDrawable: Drawable? = null

            private var scale: Scale? = null
            private var size: Size? = null

            private var imageView: ImageView? = null

            private var listener: Listener? = null

            fun setBitmapConfig(config: Bitmap.Config?): Builder {
                this.bitmapConfig = config
                return this
            }

            fun setData(data: Any?): Builder {
                var parsed: Any? = data
                if (parsed is String) {
                    parsed = Uri.parse(parsed)
                }
                this.data = parsed
                return this
            }

            fun setCrossfade(isEnabled: Boolean): Builder {
                if (isEnabled) {
                    this.crossfade = Crossfade.DefaultEnabled()
                } else {
                    this.crossfade = null
                }
                return this
            }

            fun setCrossfade(crossfade: Crossfade?): Builder {
                this.crossfade = crossfade
                return this
            }

            fun setErrorDrawable(@DrawableRes resId: Int): Builder =
                setErrorDrawable(getDrawable(resId))

            fun setErrorDrawable(drawable: Drawable?): Builder {
                this.errorDrawable = drawable
                return this
            }

            fun setPlaceholderDrawable(@DrawableRes resId: Int): Builder =
                setPlaceholderDrawable(getDrawable(resId))

            fun setPlaceholderDrawable(drawable: Drawable?): Builder {
                this.placeholderDrawable = drawable
                return this
            }

            private fun getDrawable(@DrawableRes resId: Int): Drawable? =
                AppCompatResources.getDrawable(context, resId)

            fun setScale(scale: Scale?): Builder {
                this.scale = scale
                return this
            }

            fun setSize(@Px size: Int): Builder =
                setSize(size, size)

            fun setSize(@Px width: Int, @Px height: Int): Builder =
                setSize(Size.Pixel(width, height))


            fun setSize(size: Size?): Builder {
                this.size = size
                return this
            }

            inline fun listener(
                crossinline onStart: (request: Request) -> Unit = {},
                crossinline onCancel: (request: Request) -> Unit = {},
                crossinline onError: (request: Request, throwable: Throwable) -> Unit = { _, _ -> },
                crossinline onSuccess: (request: Request) -> Unit = { _ -> }
            ) = listener(object : Listener {
                override fun onStart(request: Request) = onStart(request)
                override fun onCancel(request: Request) = onCancel(request)
                override fun onError(request: Request, throwable: Throwable) =
                    onError(request, throwable)

                override fun onSuccess(request: Request) = onSuccess(request)
            })

            fun listener(listener: Listener?): Builder {
                this.listener = listener
                return this
            }

            fun into(imageView: ImageView?): Builder {
                this.imageView = imageView
                return this
            }

            fun build(): Request {
                return Request(
                    context = context,
                    bitmapConfig = bitmapConfig ?: Bitmap.Config.ARGB_8888,
                    data = requireNotNull(data),
                    crossfade = crossfade ?: Crossfade.Disabled(),
                    errorDrawable = errorDrawable,
                    placeholderDrawable = placeholderDrawable,
                    scale = scale ?: Scale.FIT,
                    size = size ?: Size.Original,
                    imageView = requireNotNull(imageView),
                    listener = listener
                )
            }
        }

    }

    sealed interface Response {
        val drawable: Drawable?

        data class Success constructor(
            override val drawable: Drawable
        ) : Response

        data class Error constructor(
            override val drawable: Drawable?,
            val throwable: Throwable
        ) : Response
    }

    fun interface Disposable {
        fun dispose()
    }

    sealed interface Transformation {
        data class RoundedCorners constructor(
            @Px private val topLeft: Float = 0f,
            @Px private val topRight: Float = 0f,
            @Px private val bottomLeft: Float = 0f,
            @Px private val bottomRight: Float = 0f
        ) : Transformation

        data class BlurTransformation @JvmOverloads constructor(
            private val context: Context,
            private val radius: Float = 10f,
            private val sampling: Float = 1f
        ) : Transformation

        object CircleCrop : Transformation

        object Grayscale : Transformation
    }

    fun enqueue(request: Request): Disposable
    suspend fun execute(request: Request): Response

    fun dispose(imageView: ImageView)

    fun clearCache()

    interface Listener {
        @MainThread
        fun onStart(request: Request) {
        }

        /**
         * Called if the request is cancelled.
         */
        @MainThread
        fun onCancel(request: Request) {
        }

        /**
         * Called if an error occurs while executing the request.
         */
        @MainThread
        fun onError(request: Request, throwable: Throwable) {
        }

        /**
         * Called if the request completes successfully.
         */
        @MainThread
        fun onSuccess(request: Request) {
        }
    }
}