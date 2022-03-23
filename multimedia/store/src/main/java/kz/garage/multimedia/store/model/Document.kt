package kz.garage.multimedia.store.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Document constructor(
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
        properties = null,
        publicFile = null,
        remoteFile = remoteFile
    )

}