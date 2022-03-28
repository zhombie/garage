package kz.garage.retrofit.download

import java.io.*

fun InputStream?.write(
    outputFile: File,
    bufferSize: Int = 4 * 1024
): File? {
    if (this == null) return null
    try {
        var totalBytes = 0
        FileOutputStream(outputFile).use { buffer ->
            use { inputStream ->
                val bytes = ByteArray(bufferSize)
                var length: Int
                while (
                    inputStream.read(bytes)
                        .also { length = it } != -1 &&
                    !Thread.currentThread().isInterrupted
                ) {
                    totalBytes += length
                    buffer.write(bytes, 0, length)
                }
                buffer.flush()
            }
        }
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
        return null
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    } catch (e: SecurityException) {
        e.printStackTrace()
        return null
    }
    return outputFile
}