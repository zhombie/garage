package kz.garage.retrofit.download

import okhttp3.ResponseBody
import java.io.File

fun ResponseBody?.write(
    outputFile: File,
    bufferSize: Int = 4 * 1024
): File? {
    if (this == null) return null
    return byteStream().write(outputFile = outputFile, bufferSize = bufferSize)
}