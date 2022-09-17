package kz.garage.image.load

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.MainThread
import androidx.annotation.Px
import androidx.appcompat.content.res.AppCompatResources

data class Request internal constructor(
    val context: Context,
    val data: Any,
    val bitmapConfig: Bitmap.Config,
    val crossfade: Crossfade,
    val errorDrawable: Drawable?,
    val placeholderDrawable: Drawable?,
    val scale: Scale,
    val size: Size,
    val transformations: List<Transformation>?,
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
        private var data: Any? = null

        private var bitmapConfig: Bitmap.Config? = null

        private var crossfade: Crossfade? = null

        private var errorDrawable: Drawable? = null
        private var placeholderDrawable: Drawable? = null

        private var scale: Scale? = null
        private var size: Size? = null

        private var transformations: List<Transformation>? = null

        private var imageView: ImageView? = null

        private var listener: Listener? = null

        fun setData(data: Any?): Builder {
            var parsed: Any? = data
            if (parsed is String) {
                parsed = Uri.parse(parsed)
            }
            this.data = parsed
            return this
        }

        fun setBitmapConfig(config: Bitmap.Config?): Builder {
            this.bitmapConfig = config
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

        fun setTransformations(transformations: List<Transformation>?): Builder {
            this.transformations = transformations
            return this
        }

        fun setTransformations(vararg transformations: Transformation): Builder =
            setTransformations(transformations.asList())

        inline fun listener(
            crossinline onStart: (request: Request) -> Unit = {},
            crossinline onCancel: (request: Request) -> Unit = {},
            crossinline onError: (request: Request, throwable: Throwable) -> Unit = { _, _ -> },
            crossinline onSuccess: (request: Request, drawable: Drawable?) -> Unit = { _, _ -> }
        ) = listener(object : Listener {
            override fun onStart(request: Request) = onStart(request)
            override fun onCancel(request: Request) = onCancel(request)
            override fun onError(request: Request, throwable: Throwable) =
                onError(request, throwable)

            override fun onSuccess(request: Request, drawable: Drawable?) =
                onSuccess(request, drawable)
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
                transformations = transformations,
                imageView = requireNotNull(imageView),
                listener = listener
            )
        }
    }

    interface Listener {
        @MainThread
        fun onStart(request: Request) {
        }

        /**
         * Called if the request is cancelled
         */
        @MainThread
        fun onCancel(request: Request) {
        }

        /**
         * Called if an error occurs while executing the request
         */
        @MainThread
        fun onError(request: Request, throwable: Throwable) {
        }

        /**
         * Called if the request completes successfully
         */
        @MainThread
        fun onSuccess(request: Request, drawable: Drawable?) {
        }
    }

}