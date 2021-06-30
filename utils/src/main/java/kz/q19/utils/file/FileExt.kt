package kz.q19.utils.file

import android.net.Uri
import java.io.File

/**
 * Creates a Uri from the given encoded URI string.
 *
 * @see Uri.parse
 */
fun String.toUri(): Uri = Uri.parse(this)

/**
 * Creates a Uri from the given file.
 *
 * @see Uri.fromFile
 */
fun File.toUri(): Uri = Uri.fromFile(this)

/**
 * Creates a [File] from the given [Uri]. Note that this will throw an
 * [IllegalArgumentException] when invoked on a [Uri] that lacks `file` scheme.
 */
fun Uri.toFile(): File {
    require(scheme == "file") { "Uri lacks 'file' scheme: $this" }
    return File(requireNotNull(path) { "Uri path is null: $this" })
}