package kz.garage.retrofit.download

import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*
import java.io.IOException

// Inspired by: https://github.com/square/okhttp/blob/master/samples/guide/src/main/java/okhttp3/recipes/Progress.java
class DownloadResponseBody constructor(
    private val delegateResponseBody: ResponseBody,
    private val listener: DownloadStateListener?
) : ResponseBody() {

    private var bufferedSource: BufferedSource? = null

    override fun contentType(): MediaType? = delegateResponseBody.contentType()

    override fun contentLength(): Long = delegateResponseBody.contentLength()

    override fun source(): BufferedSource {
        if (bufferedSource == null) {
            bufferedSource = source(delegateResponseBody.source()).buffer()
        }
        return requireNotNull(bufferedSource)
    }

    private fun source(source: Source): Source {
        return object : ForwardingSource(source) {
            private val totalContentLength = contentLength()

            private var totalBytesRead = 0L

            @Throws(IOException::class)
            override fun read(sink: Buffer, byteCount: Long): Long {
                val bytesRead = super.read(sink, byteCount)
                // read() returns the number of bytes read, or -1 if this source is exhausted.
                if (listener != null) {
                    if (bytesRead > 0) {
                        totalBytesRead += bytesRead

                        listener.onState(
                            DownloadStateListener.State.Loading(
                                bytesRead = totalBytesRead,
                                contentLength = totalContentLength
                            )
                        )
                    } else {
                        listener.onState(
                            DownloadStateListener.State.Completed(
                                contentLength = totalContentLength
                            )
                        )
                    }
                }
                return bytesRead
            }
        }
    }

}