package kz.garage.samples.chat.coil

import android.content.Context
import android.content.Intent
import kz.garage.chat.ui.imageloader.ChatUiImageLoader

object ChatUI {

    data class Credentials constructor(
        val username: String,
        val password: String
    )

    var credentials: Credentials? = null
        @Synchronized get
        @Synchronized set

    private var imageLoader: ChatUiImageLoader? = null
    private var imageLoaderFactory: ChatUiImageLoader.Factory? = null

    @Synchronized
    fun getImageLoader(context: Context?): ChatUiImageLoader =
        imageLoader ?: setImageLoaderFactory(context)

    @Synchronized
    fun setImageLoader(loader: ChatUiImageLoader) {
        imageLoaderFactory = null
        imageLoader = loader
    }

    @Synchronized
    fun setImageLoader(factory: ChatUiImageLoader.Factory) {
        imageLoaderFactory = factory
        imageLoader = null
    }

    @Synchronized
    fun setImageLoaderFactory(context: Context?): ChatUiImageLoader {
        imageLoader?.let { return it }

        imageLoader = imageLoaderFactory?.getImageLoader()
            ?: (context?.applicationContext as? ChatUiImageLoader.Factory)?.getImageLoader()

        imageLoaderFactory = null

        return imageLoader ?: throw IllegalStateException()
    }

    open class Builder private constructor(private val context: Context) {

        private var baseUrl: String? = null
        private var username: String? = null
        private var password: String? = null
        private var isLoggingEnabled: Boolean = false
        private var callTopic: String? = null

        fun getContext(): Context = context

        fun getBaseUrl(): String? = baseUrl

        fun getUsername(): String? = username

        fun getLoggingEnabled(): Boolean = isLoggingEnabled

        fun getCallTopic(): String? = callTopic

        fun setBaseUrl(baseUrl: String?): Builder {
            this.baseUrl = baseUrl
            return this
        }

        fun setUsername(username: String?): Builder {
            this.username = username
            return this
        }

        fun setPassword(password: String?): Builder {
            this.password = password
            return this
        }

        fun setLoggingEnabled(isEnabled: Boolean): Builder {
            this.isLoggingEnabled = isEnabled
            return this
        }

        fun setCallTopic(callTopic: String?): Builder {
            this.callTopic = callTopic
            return this
        }

        fun build(): Intent = Intent()
//            HomeActivity.newIntent(
//                context = context,
//                params = HomeActivity.Params(
//                    baseUrl = if (baseUrl.isNullOrBlank()) {
//                        throw BaseUrlNullOrBlankException()
//                    } else {
//                        requireNotNull(baseUrl)
//                    },
//                    username = if (username.isNullOrBlank()) {
//                        throw AuthCredentialsNullOrBlankException()
//                    } else {
//                        requireNotNull(username)
//                    },
//                    password = if (password.isNullOrBlank()) {
//                        throw AuthCredentialsNullOrBlankException()
//                    } else {
//                        requireNotNull(password)
//                    },
//                    isLoggingEnabled = isLoggingEnabled,
//                    callTopic = requireNotNull(callTopic)
//                )
//            )

        fun launch(): Intent {
            val intent = build()
            context.startActivity(intent)
            return intent
        }

        class Default constructor(context: Context) : Builder(context)
    }

}