package kz.q19.audio

import android.content.Context
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.util.Log
import kz.q19.utils.android.AndroidUtils
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

internal object Utils {

    private const val TAG = "Utils"

    fun getLongWaveformSampleCount(context: Context): Int {
        val screenWidthDp = AndroidUtils.pxToDp(AndroidUtils.getScreenWidth(context))
        return (Constants.WAVEFORM_WIDTH * screenWidthDp).toInt()
    }


    fun isSupportedExtension(extension: String?): Boolean {
        for (i in Constants.SUPPORTED_EXT.indices) {
            if (Constants.SUPPORTED_EXT[i].equals(extension, ignoreCase = true)) {
                return true
            }
        }
        return false
    }

    fun isDelExtension(extension: String?): Boolean {
        return Constants.TRASH_MARK_EXTENSION.equals(extension, ignoreCase = true)
    }

    /**
     * Remove file extension from file name;
     * @param name File name with extension;
     * @return File name without extension or unchanged String if extension was not identified.
     */
    fun removeFileExtension(name: String): String {
        if (name.contains(Constants.EXTENSION_SEPARATOR)) {
            val extIndex: Int = name.lastIndexOf(Constants.EXTENSION_SEPARATOR)
            val isRemovable = (isSupportedExtension(name.substring(extIndex + 1))
                    || isDelExtension(name.substring(extIndex + 1)))
            if (extIndex >= 0 && extIndex + 1 < name.length && isRemovable) {
                return name.substring(0, name.lastIndexOf(Constants.EXTENSION_SEPARATOR))
            }
        }
        return name
    }

    /**
     * Create file.
     * If it is not exists, than create it.
     * @param path Path to file.
     * @param fileName File name.
     */
    fun createFile(path: File?, fileName: String): File? {
        return if (path != null) {
            createDir(path)
            Log.d(TAG, "createFile path = " + path.absolutePath + " fileName = " + fileName)
            val file = File(path, fileName)
            // Create file if need.
            if (!file.exists()) {
                try {
                    if (file.createNewFile()) {
                        Log.i(TAG, "The file was successfully created! - " + file.absolutePath)
                    } else {
                        Log.i(TAG, "The file exist! - " + file.absolutePath)
                    }
                } catch (e: IOException) {
                    Log.e(TAG, "Failed to create the file.", e)
                    return null
                }
            } else {
                Log.e(TAG, "File already exists!! Please rename file!")
                Log.i(TAG, "Renaming file")
                return createFile(path, "${fileName}_${System.currentTimeMillis()}")
            }
            if (!file.canWrite()) {
                Log.e(TAG, "The file can not be written.")
            }
            file
        } else {
            null
        }
    }

    private fun createDir(dir: File?): File? {
        if (dir != null) {
            if (!dir.exists()) {
                try {
                    if (dir.mkdirs()) {
                        Log.d(TAG, "Dirs are successfully created")
                        return dir
                    } else {
                        Log.e(TAG,
                            "Dirs are NOT created! Please check permission write to external storage!")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, e.toString())
                }
            } else {
                Log.d(TAG, "Dir already exists")
                return dir
            }
        }
        Log.e(TAG, "File is null or unable to create dirs")
        return null
    }

    fun generateRecordNameMills(): String {
        return System.currentTimeMillis().toString()
    }

    fun addExtension(name: String, extension: String): String {
        return name + Constants.EXTENSION_SEPARATOR + extension
    }

    @Throws(FileNotFoundException::class)
    fun getPrivateRecordsDir(context: Context): File {
        return getPrivateMusicStorageDir(context, Constants.RECORDS_DIR) ?: throw FileNotFoundException()
    }

    private fun getPrivateMusicStorageDir(context: Context, albumName: String): File? {
        val file = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
        if (file != null) {
            val f = File(file, albumName)
            if (!f.exists() && !f.mkdirs()) {
                Log.e(TAG, "Directory not created")
            } else {
                return f
            }
        }
        return null
    }

    @Throws(IllegalArgumentException::class)
    fun getAvailableInternalMemorySize(context: Context): Long {
        val file = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
        return if (file != null) {
            val fsi = StatFs(file.absolutePath)
            fsi.blockSizeLong * fsi.availableBlocksLong
        } else {
            0
        }
    }
}