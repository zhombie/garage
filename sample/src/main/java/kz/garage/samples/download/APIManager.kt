package kz.garage.samples.download

import kz.garage.retrofit.download.addDownloadProgressInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object APIManager {

    fun <T> createService(serviceClass: Class<T>, baseUrl: String): T =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor()
                            .apply { level = HttpLoggingInterceptor.Level.HEADERS }
                    )
                    .addDownloadProgressInterceptor()
//                    .connectTimeout(1, TimeUnit.MINUTES)
//                    .readTimeout(1, TimeUnit.MINUTES)
//                    .writeTimeout(1, TimeUnit.MINUTES)
                    .build()
            )
            .build()
            .create(serviceClass)

}