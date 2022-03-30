package kz.garage.retrofit.download

import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.util.concurrent.CancellationException

class FileWriter constructor(
    val outputFile: File,
    val bufferSize: Int = 4 * 1024
) {

    @Volatile
    var isActive: Boolean = false
        private set

    private var inputStream: InputStream? = null
    private var outputStream: OutputStream? = null

    fun write(responseBody: ResponseBody?): File? =
        write(responseBody?.byteStream())

    fun write(inputStream: InputStream?): File? {
        if (inputStream == null) return null
        this.inputStream = inputStream
        runCatching {
            isActive = true

            var totalBytes = 0
            outputStream = FileOutputStream(outputFile)
            outputStream?.use { buffer ->
                inputStream.use { inputStream ->
                    val bytes = ByteArray(bufferSize)
                    var length: Int
                    while (inputStream.read(bytes).also { length = it } >= 0) {
                        if (!isActive) {
                            closeQuietly()
                            throw CancellationException("User cancelled IO streams!")
                        }
                        totalBytes += length
                        buffer.write(bytes, 0, length)
                    }
                    buffer.flush()
                }
            }
        }
            .onSuccess {
                isActive = false
                return outputFile
            }
            .onFailure {
                isActive = false
                if (it is CancellationException) {
                    throw it
                }
            }
        return null
    }

    fun cancel() {
        isActive = false
    }

    private fun closeQuietly() {
        try {
            outputStream?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            outputStream = null
        }

        try {
            inputStream?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            inputStream = null
        }

        try {
            if (outputFile.canRead()) {
                // Ignored
            } else {
                outputFile.delete()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}