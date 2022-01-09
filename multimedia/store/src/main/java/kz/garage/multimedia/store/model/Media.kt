package kz.garage.multimedia.store.model

import android.net.Uri

open class Media internal constructor(
    override val id: Long,
    override val uri: Uri,
    override val title: String?,
    override val displayName: String?,
    override val folder: Folder?,
    override val history: History?,
    override val properties: Properties?,
    override val localFile: LocalFile?,
    override val remoteAddress: RemoteAddress?
) : Content(
    id = id,
    uri = uri,
    title = title,
    displayName = displayName,
    folder = folder,
    history = history,
    properties = properties,
    localFile = localFile,
    remoteAddress = remoteAddress
) {

    interface Playable {
        companion object {
            const val UNDEFINED_DURATION = -1L
        }

        /**
         * [duration] - The duration time of the [Media]
         */
        val duration: Long

        fun isDurationUndefined(): Boolean =
            duration == UNDEFINED_DURATION
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
            "localFile=$localFile," +
            "remoteAddress=$remoteAddress)"

}