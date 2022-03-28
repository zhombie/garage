package kz.garage.retrofit.download

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.internal.headersContentLength

class DownloadProgressInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val response = chain.proceed(request)

        Log.d("DownloadProgressInterceptor", "intercept() -> response.headersContentLength(): ${response.headersContentLength()}")

        val listener = request.tag(DownloadStateListener::class.java)
        if (listener is DownloadStateListener) {
            response.body?.let {
                return response.newBuilder()
                    .body(DownloadResponseBody(it, listener))
                    .build()
            }
        }

        return response
    }

}