package kz.q19.utils.file

import android.os.Environment
import android.util.Log
import java.io.File

/**
 * Checks if external storage is available to at least read
 */
fun Environment.isExternalStorageReadable(): Boolean {
    val state = Environment.getExternalStorageState()
    return Environment.MEDIA_MOUNTED == state || Environment.MEDIA_MOUNTED_READ_ONLY == state
}


/**
 * Checks if external storage is available for read and write
 */
fun Environment.isExternalStorageWritable(): Boolean {
    val state = Environment.getExternalStorageState()
    return Environment.MEDIA_MOUNTED == state
}


/**
 * Remove file or directory with all content
 */
fun File.deleteRecursively(): Boolean {
    if (deleteRecursivelyDirs()) {
        return true
    }
    Log.e("FileExt", "Failed to delete directory: $absolutePath")
    return false
}


/**
 * Recursively remove file or directory with children.
 */
private fun File?.deleteRecursivelyDirs(): Boolean {
    var ok = true
    if (this?.exists() == true) {
        if (isDirectory) {
            val children = list() ?: return false
            for (i in children.indices) {
                ok = ok and File(this, children[i]).deleteRecursivelyDirs()
            }
        }
        if (ok && delete()) {
            Log.d("FileExt", "File deleted: $absolutePath")
        }
    }
    return ok
}
