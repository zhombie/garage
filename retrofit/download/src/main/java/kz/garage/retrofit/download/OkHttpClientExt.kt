package kz.garage.retrofit.download

import okhttp3.OkHttpClient

fun OkHttpClient.Builder.addDownloadProgressInterceptor(): OkHttpClient.Builder {
    if (networkInterceptors().find { it is DownloadProgressInterceptor } == null) {
        return addNetworkInterceptor(DownloadProgressInterceptor())
    }
    return this
}
