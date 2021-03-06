package kz.garage.multimedia.store.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.File
import kotlin.random.Random

/**
 * [id] - The unique ID of the [Content]
 * [uri] - The uri path of the [Content] (usually content://...)
 * [title] - The title of the [Content]
 * [displayName] - The display name of the [Content]. For example, an [Content] stored at
 * {@code /storage/0000-0000/DCIM/Vacation/IMG1024.JPG} would
 * have a display name of {@code IMG1024.JPG}.
 * [folder] - The primary folder of this [Content]
 * [publicFile] - The public local duplicate file, which is generated by given [uri]
 * [remoteFile] - The remote file, which contains reference to url
 */
abstract class Content constructor(
    open val id: String,
    open val uri: Uri?,
    open val title: String?,
    open val displayName: String?,
    open val folder: Folder?,
    open val history: History?,
    open val properties: Properties?,
    open val publicFile: PublicFile?,
    open val remoteFile: RemoteFile?
) : Parcelable {

    companion object {
        fun generateId(): String =
            (System.currentTimeMillis() + Random.nextLong(Long.MIN_VALUE, Long.MAX_VALUE)).toString()
    }

    @JvmInline
    @Parcelize
    value class PublicFile constructor(
        val uri: Uri
    ) : Parcelable {

        constructor(file: File) : this(Uri.fromFile(file))

        fun exists(): Boolean =
            getFile()?.exists() == true

        fun getFile(): File? {
            val path = uri.path
            if (path.isNullOrBlank()) return null
            return File(path)
        }

        fun requireFile(): File {
            require(uri.scheme == "file") { "Uri lacks 'file' scheme: $this" }
            return File(requireNotNull(uri.path) { "Uri path is null: $this" })
        }

    }

    @Parcelize
    @JvmInline
    value class RemoteFile constructor(
        val uri: Uri
    ) : Parcelable {

        constructor(url: String) : this(Uri.parse(url))

        val url: String
            get() = uri.toString()

        fun addHost(host: String?): String? {
            if (host.isNullOrBlank()) return null
            val url = url
            if (url.startsWith(host)) return url
            val clearHost = host.dropLastWhile { it == '/' }
            if (url.startsWith('/')) return clearHost + url
            return "$clearHost/$url"
        }

    }

    /**
     * [addedAt] - The time the [Content] was first added (milliseconds)
     * [modifiedAt] - The time the [Content] was last modified (milliseconds)
     * [createdAt] - The time the [Content] was created.
     * If [Image] or [Video], it is as same as date taken (milliseconds)
     */
    @Parcelize
    data class History constructor(
        val addedAt: Long? = null,
        val modifiedAt: Long? = null,
        val createdAt: Long? = null,
    ) : Parcelable {

        fun isNull(): Boolean =
            addedAt == null && modifiedAt == null && createdAt == null
    }

    @Parcelize
    data class Properties constructor(
        val size: Long?,
        val mimeType: String? = null,
    ) : Parcelable

    val label: String?
        get() = when {
            !title.isNullOrBlank() -> title
            !displayName.isNullOrBlank() -> displayName
            else -> null
        }

    val availableFileUri: Uri?
        get() = when {
            isStorageFileExists() -> uri
            isPublicFileExists() -> publicFile?.uri
            isRemoteFileExists() -> remoteFile?.uri
            else -> null
        }

    fun isStorageFileExists(): Boolean = uri != null && uri != Uri.EMPTY

    fun isPublicFileExists(): Boolean = publicFile?.exists() == true

    fun isRemoteFileExists(): Boolean = !remoteFile?.url.isNullOrBlank()

    fun isAnyFileExists(): Boolean =
        isStorageFileExists() || isPublicFileExists() || isRemoteFileExists()

    open fun clone(publicFile: PublicFile?): Content {
        if (this.publicFile == null && publicFile == null) return this
        if (this.publicFile == publicFile) return this
        return clone(
            id = id,
            uri = uri,
            title = title,
            displayName = displayName,
            folder = folder,
            history = history,
            properties = properties,
            publicFile = publicFile,
            remoteFile = remoteFile
        )
    }

    open fun clone(uri: Uri?): Content =
        if (uri == null) this else clone(remoteFile = RemoteFile(uri))

    open fun clone(url: String?): Content =
        if (url.isNullOrBlank()) this else clone(remoteFile = RemoteFile(url = url))

    open fun clone(remoteFile: RemoteFile?): Content {
        if (this.remoteFile == null && remoteFile == null) return this
        if (this.remoteFile?.uri == remoteFile?.uri) return this
        return clone(
            id = id,
            uri = uri,
            title = title,
            displayName = displayName,
            folder = folder,
            history = history,
            properties = properties,
            publicFile = publicFile,
            remoteFile = remoteFile
        )
    }

    open fun clone(
        id: String? = null,
        uri: Uri? = null,
        title: String? = null,
        displayName: String? = null,
        folder: Folder? = null,
        history: History? = null,
        properties: Properties? = null,
        publicFile: PublicFile? = null,
        remoteFile: RemoteFile? = null
    ): Content {
        if (
            this.id == id &&
            this.uri == uri &&
            this.title == title &&
            this.displayName == displayName &&
            this.folder == folder &&
            this.history == history &&
            this.properties == properties &&
            this.publicFile?.uri == publicFile?.uri &&
            this.remoteFile?.uri == remoteFile?.uri
        ) {
            return this
        }
        return when (this) {
            is Image -> {
                copy(
                    id = id ?: this.id,
                    uri = uri ?: this.uri,
                    title = title ?: this.title,
                    displayName = displayName ?: this.displayName,
                    folder = folder ?: this.folder,
                    history = history ?: this.history,
                    properties = properties ?: this.properties,
                    publicFile = publicFile ?: this.publicFile,
                    remoteFile = remoteFile ?: this.remoteFile
                )
            }
            is Video -> {
                copy(
                    id = id ?: this.id,
                    uri = uri ?: this.uri,
                    title = title ?: this.title,
                    displayName = displayName ?: this.displayName,
                    folder = folder ?: this.folder,
                    history = history ?: this.history,
                    properties = properties ?: this.properties,
                    publicFile = publicFile ?: this.publicFile,
                    remoteFile = remoteFile ?: this.remoteFile
                )
            }
            is Audio -> {
                copy(
                    id = id ?: this.id,
                    uri = uri ?: this.uri,
                    title = title ?: this.title,
                    displayName = displayName ?: this.displayName,
                    folder = folder ?: this.folder,
                    history = history ?: this.history,
                    properties = properties ?: this.properties,
                    publicFile = publicFile ?: this.publicFile,
                    remoteFile = remoteFile ?: this.remoteFile
                )
            }
            is Document -> {
                copy(
                    id = id ?: this.id,
                    uri = uri ?: this.uri,
                    title = title ?: this.title,
                    displayName = displayName ?: this.displayName,
                    folder = folder ?: this.folder,
                    history = history ?: this.history,
                    properties = properties ?: this.properties,
                    publicFile = publicFile ?: this.publicFile,
                    remoteFile = remoteFile ?: this.remoteFile
                )
            }
            else -> this
        }
    }

    override fun toString(): String =
        "${Content::class.java.simpleName}(" +
            "id=$id, " +
            "uri=$uri, " +
            "title=$title, " +
            "displayName=$displayName, " +
            "folder=$folder, " +
            "history=$history, " +
            "properties=$properties, " +
            "publicFile=$publicFile," +
            "remoteAddress=$remoteFile)"

}