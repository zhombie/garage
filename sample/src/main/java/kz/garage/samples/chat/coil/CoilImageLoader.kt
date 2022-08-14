package kz.garage.samples.chat.coil

import android.content.Context
import android.graphics.Paint
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
import coil.util.DebugLogger
import kz.garage.R
import kz.garage.chat.ui.imageloader.ChatUiImageLoader

class CoilImageLoader constructor(
    private val context: Context,
    isLoggingEnabled: Boolean
) : ChatUiImageLoader, DefaultLifecycleObserver {

    companion object {
        private val TAG =  CoilImageLoader::class.java.simpleName
    }

    private val imageLoader by lazy(LazyThreadSafetyMode.NONE) {
        // Video frame
        coil.ImageLoader.Builder(context)
            .allowHardware(true)
            .memoryCache {
                MemoryCache.Builder(context)
                    .maxSizePercent(0.25)
                    .build()
            }
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
            .crossfade(false)
            .diskCachePolicy(CachePolicy.ENABLED)
            .logger(if (isLoggingEnabled) DebugLogger() else null)
            .memoryCachePolicy(CachePolicy.ENABLED)
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
                setColorSchemeColors(context.getColor(R.color.chat_ui_white))
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

    override fun enqueue(request: ChatUiImageLoader.Request): ChatUiImageLoader.Disposable {
//        Log.d(TAG, "enqueue() -> request: $request")
        val imageRequest = request.map()
//        Log.d(TAG, "enqueue() -> imageRequest: $imageRequest")
        val disposable = imageLoader.enqueue(imageRequest)
//        Log.d(TAG, "enqueue() -> disposable: $disposable")
        return ChatUiImageLoader.Disposable { disposable.dispose() }
    }

    override suspend fun execute(request: ChatUiImageLoader.Request): ChatUiImageLoader.Response {
//        Log.d(TAG, "enqueue() -> request: $request")
        return when (val result = imageLoader.execute(request.map())) {
            is SuccessResult ->
                ChatUiImageLoader.Response.Success(result.drawable)
            is ErrorResult ->
                ChatUiImageLoader.Response.Error(result.drawable, result.throwable)
            else ->
                ChatUiImageLoader.Response.Error(null, UnsupportedOperationException())
        }
    }

    private fun ChatUiImageLoader.Request.map(): ImageRequest =
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
                    error(R.drawable.chat_ui_bg_alpha_black)
                } else {
                    error(errorDrawable)
                }

                when (scale) {
                    ChatUiImageLoader.Request.Scale.FILL ->
                        scale(Scale.FILL)
                    ChatUiImageLoader.Request.Scale.FIT ->
                        scale(Scale.FIT)
                }

                when (size) {
                    ChatUiImageLoader.Request.Size.Inherit -> {
                        precision(Precision.AUTOMATIC)
                        size(ViewSizeResolver(imageView))
                    }
                    ChatUiImageLoader.Request.Size.Original -> {
                        precision(Precision.AUTOMATIC)
                        size(Size.ORIGINAL)
                    }
                    is ChatUiImageLoader.Request.Size.Pixel -> {
                        with(size) {
                            if (this is ChatUiImageLoader.Request.Size.Pixel) {
                                precision(Precision.EXACT)
                                size(width, height)
                            }
                        }
                    }
                }
            }
            .listener(
                onStart = {
                    startProgress()
                    listener?.onStart(this)
                },
                onCancel = {
                    stopProgress()
                    listener?.onCancel(this)
                },
                onError = { _, errorResult ->
                    stopProgress()
                    listener?.onError(this, errorResult.throwable)
                },
                onSuccess = { _, _ ->
                    stopProgress()
                    listener?.onSuccess(this)
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