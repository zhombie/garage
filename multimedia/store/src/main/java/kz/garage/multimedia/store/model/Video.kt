package kz.garage.multimedia.store.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Video constructor(
    override val id: String,
    override val uri: Uri?,
    override val title: String?,
    override val displayName: String?,
    override val folder: Folder?,
    override val history: History?,
    override val duration: Long?,
    override val resolution: Resolution?,
    override val properties: Properties?,
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
), Media.Playable, Media.Visual, Parcelable {

    constructor(
        uri: Uri,
        displayName: String,
        publicFile: PublicFile
    ) : this(
        id = generateId(),
        uri = uri,
        title = null,
        displayName = displayName,
        folder = null,
        history = null,
        duration = null,
        resolution = null,
        properties = null,
        publicFile = publicFile,
        remoteFile = null
    )

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
        resolution = null,
        properties = null,
        publicFile = null,
        remoteFile = remoteFile
    )

}