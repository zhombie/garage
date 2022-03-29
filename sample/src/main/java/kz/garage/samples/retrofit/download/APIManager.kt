package kz.garage.samples.retrofit.download

import kz.garage.retrofit.download.addDownloadProgressInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object APIManager {

    fun <T> createService(
        serviceClass: Class<T>,
        baseUrl: String,
        loggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
            .apply { level = HttpLoggingInterceptor.Level.HEADERS }
    ): T =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .addDownloadProgressInterceptor()
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(1, TimeUnit.MINUTES)
                    .writeTimeout(1, TimeUnit.MINUTES)
                    .build()
            )
            .build()
            .create(serviceClass)

}