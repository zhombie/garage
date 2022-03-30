package kz.garage.retrofit.download

import okhttp3.Interceptor
import okhttp3.Response

class DownloadProgressInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val response = chain.proceed(request)

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