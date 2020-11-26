package kz.q19.utils.file

import android.net.Uri
import java.io.File

/**
 * Creates a Uri from the given file.
 *
 * @see Uri.fromFile
 */
fun File.toUri(): Uri = Uri.fromFile(this)


/** Creates a [File] from the given [Uri]. */
fun Uri.toFile(): File {
    require(scheme == "file") { "Uri lacks 'file' scheme: $this" }
    return File(requireNotNull(path) { "Uri path is null: $this" })
}