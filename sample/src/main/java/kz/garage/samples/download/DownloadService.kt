package kz.garage.samples.download

import kz.garage.retrofit.download.DownloadStateListener
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Tag
import retrofit2.http.Url

interface DownloadService {

    @GET
    @Streaming
    suspend fun download(
        @Url url: String,
        @Tag listener: DownloadStateListener? = null
    ): ResponseBody

}