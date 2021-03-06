package kz.garage.multimedia.store.model

import android.net.Uri
import android.os.Parcelable

abstract class Media internal constructor(
    override val id: String,
    override val uri: Uri?,
    override val title: String?,
    override val displayName: String?,
    override val folder: Folder?,
    override val history: History?,
    override val properties: Properties?,
    override val publicFile: PublicFile?,
    override val remoteFile: RemoteFile?
) : Content(
    id = id,
    uri = uri,
    title = title,
    displayName = displayName,
    folder = folder,
    history = history,
    properties = properties,
    publicFile = publicFile,
    remoteFile = remoteFile
), Parcelable {

    interface Playable {
        /**
         * [duration] - The duration time of the [Media]
         */
        val duration: Long?

        fun hasDuration(): Boolean = (duration ?: -1L) >= 0
    }

    interface Visual {
        val resolution: Resolution?
    }

    override fun toString(): String =
        "${Media::class.java.simpleName}(" +
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