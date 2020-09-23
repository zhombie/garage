package kz.q19.utils.file

import android.os.Environment
import android.util.Log
import java.io.File

object FileUtils {

    private const val TAG = "FileUtils"

    /**
     * Checks if external storage is available to at least read.
     */
    fun isExternalStorageReadable(): Boolean {
        val state = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state || Environment.MEDIA_MOUNTED_READ_ONLY == state
    }

    /**
     * Checks if external storage is available for read and write.
     */
    fun isExternalStorageWritable(): Boolean {
        val state = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state
    }

    /**
     * Remove file or directory with all content
     * @param file File or directory needed to delete.
     */
    fun deleteFile(file: File): Boolean {
        if (deleteRecursivelyDirs(file)) {
            return true
        }
        Log.e(TAG, "Failed to delete directory: " + file.absolutePath)
        return false
    }

    /**
     * Recursively remove file or directory with children.
     * @param file File to remove
     */
    private fun deleteRecursivelyDirs(file: File?): Boolean {
        var ok = true
        if (file?.exists() == true) {
            if (file.isDirectory) {
                val children = file.list() ?: return false
                for (i in children.indices) {
                    ok = ok and deleteRecursivelyDirs(File(file, children[i]))
                }
            }
            if (ok && file.delete()) {
                Log.d(TAG, "File deleted: " + file.absolutePath)
            }
        }
        return ok
    }

}