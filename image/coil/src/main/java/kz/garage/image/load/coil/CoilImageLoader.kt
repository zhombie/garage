package kz.garage.image.load.coil

import android.content.Context
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.widget.ImageView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.VideoFrameDecoder
import coil.dispose
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.size.Precision
import coil.size.Scale
import coil.size.Size
import coil.size.ViewSizeResolver
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import coil.util.DebugLogger
import kz.garage.image.load.*

class CoilImageLoader constructor(
    private val context: Context,
    allowHardware: Boolean = true,
    crossfade: Boolean = false,
    diskCachePolicy: CachePolicy = CachePolicy.ENABLED,
    isLoggingEnabled: Boolean = false,
    memoryCache: MemoryCache? = MemoryCache.Builder(context)
        .maxSizePercent(0.25)
        .build(),
    memoryCachePolicy: CachePolicy = CachePolicy.ENABLED
) : ImageLoader, DefaultLifecycleObserver {

    companion object {
        private val TAG = CoilImageLoader::class.java.simpleName
    }

    private val imageLoader by lazy(LazyThreadSafetyMode.NONE) {
        // Video frame
        coil.ImageLoader.Builder(context)
            .allowHardware(allowHardware)
            .components {
                // Video frame
                add(VideoFrameDecoder.Factory())

                // GIF
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .crossfade(crossfade)
            .diskCachePolicy(diskCachePolicy)
            .logger(if (isLoggingEnabled) DebugLogger() else null)
            .memoryCache { memoryCache }
            .memoryCachePolicy(memoryCachePolicy)
            .build()
    }

    private var circularProgressDrawable: CircularProgressDrawable? = null

    private fun getCircularProgressDrawable(): CircularProgressDrawable? {
        if (circularProgressDrawable == null) {
            circularProgressDrawable = CircularProgressDrawable(context).apply {
                setStyle(CircularProgressDrawable.LARGE)
                arrowEnabled = false
                centerRadius = 60F
                strokeCap = Paint.Cap.ROUND
                strokeWidth = 11F
                setColorSchemeColors(context.getColor(android.R.color.white))
            }
        }
        return circularProgressDrawable
    }

    private fun startProgress() {
        if (circularProgressDrawable?.isRunning == false) {
            circularProgressDrawable?.start()
        }
    }

    private fun stopProgress() {
        if (circularProgressDrawable?.isRunning == true) {
            circularProgressDrawable?.stop()
        }
    }

    override fun enqueue(request: Request): Disposable {
//        Log.d(TAG, "enqueue() -> request: $request")
        val imageRequest = request.map()
//        Log.d(TAG, "enqueue() -> imageRequest: $imageRequest")
        val disposable = imageLoader.enqueue(imageRequest)
//        Log.d(TAG, "enqueue() -> disposable: $disposable")
        return Disposable { disposable.dispose() }
    }

    override suspend fun execute(request: Request): Response {
//        Log.d(TAG, "enqueue() -> request: $request")
        return when (val result = imageLoader.execute(request.map())) {
            is SuccessResult ->
                Response.Success(result.drawable)
            is ErrorResult ->
                Response.Error(result.drawable, result.throwable)
            else ->
                Response.Error(null, UnsupportedOperationException())
        }
    }

    private fun Request.map(): ImageRequest =
        ImageRequest.Builder(context)
            .bitmapConfig(bitmapConfig)
            .data(data)
            .apply {
                if (placeholderDrawable != null) {
                    placeholder(placeholderDrawable)
                }

                if (crossfade.isEnabled) {
                    crossfade(crossfade.isEnabled)
                    crossfade(crossfade.duration)
                }

                if (errorDrawable == null) {
                    error(ColorDrawable(context.getColor(android.R.color.black)))
                } else {
                    error(errorDrawable)
                }

                when (scale) {
                    Request.Scale.FILL ->
                        scale(Scale.FILL)
                    Request.Scale.FIT ->
                        scale(Scale.FIT)
                }

                when (size) {
                    Request.Size.Inherit -> {
                        precision(Precision.AUTOMATIC)
                        size(ViewSizeResolver(imageView))
                    }
                    Request.Size.Original -> {
                        precision(Precision.AUTOMATIC)
                        size(Size.ORIGINAL)
                    }
                    is Request.Size.Pixel -> {
                        with(size) {
                            if (this is Request.Size.Pixel) {
                                precision(Precision.EXACT)
                                size(width, height)
                            }
                        }
                    }
                }

                val transformations = transformations?.mapNotNull {
                    when (val transformation = it) {
                        is Transformation.RoundedCorners -> {
                            RoundedCornersTransformation(
                                topLeft = transformation.topLeft,
                                topRight = transformation.topRight,
                                bottomLeft = transformation.bottomLeft,
                                bottomRight = transformation.bottomRight
                            )
                        }
                        is Transformation.BlurTransformation -> {
                            null
                        }
                        is Transformation.CircleCrop -> {
                            CircleCropTransformation()
                        }
                        is Transformation.Grayscale -> {
                            null
                        }
                        else -> {
                            null
                        }
                    }
                }
                if (!transformations.isNullOrEmpty()) {
                    transformations(transformations)
                }
            }
            .listener(
                onStart = { _ ->
                    startProgress()
                    listener?.onStart(this)
                },
                onCancel = { _ ->
                    stopProgress()
                    listener?.onCancel(this)
                },
                onError = { _, result ->
                    stopProgress()
                    listener?.onError(this, result.throwable)
                },
                onSuccess = { _, result ->
                    stopProgress()
                    listener?.onSuccess(this, result.drawable)
                }
            )
            .target(imageView)
            .build()

    override fun dispose(imageView: ImageView) {
        with(imageView) {
            dispose()
            setImageDrawable(null)
        }
    }

    override fun clearCache() {
        stopProgress()

        imageLoader.memoryCache?.clear()
    }

    /**
     * [androidx.lifecycle.DefaultLifecycleObserver] implementation
     */

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)

        clearCache()
    }

}
