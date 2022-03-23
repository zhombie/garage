package kz.garage.multimedia.store.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Audio constructor(
    override val id: String,
    override val uri: Uri?,
    override val title: String?,
    override val displayName: String?,
    override val folder: Folder?,
    override val history: History?,
    override val duration: Long?,
    override val properties: Properties?,
    val album: Album?,
    override val publicFile: PublicFile?,
    override val remoteFile: RemoteFile?
) : Media(
    id = id,
    uri = uri,
    title = title,
    displayName = displayName,
    folder = folder,
    history = history,
    properties = properties,
    publicFile = publicFile,
    remoteFile = remoteFile
), Media.Playable, Parcelable {

    @Parcelize
    data class Album constructor(
        val id: Long,
        val title: String?,
        val artist: String?
    ) : Parcelable

    constructor(
        id: String?,
        title: String?,
        remoteFile: RemoteFile?
    ) : this(
        id = id ?: generateId(),
        uri = null,
        title = title,
        displayName = null,
        folder = null,
        history = null,
        duration = null,
        properties = null,
        album = null,
        publicFile = null,
        remoteFile = remoteFile
    )

}