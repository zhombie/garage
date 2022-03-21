package kz.garage.multimedia.store.model

import android.net.Uri
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image constructor(
    override val id: Long,
    override val uri: Uri,
    override val title: String?,
    override val displayName: String?,
    override val folder: Folder?,
    override val history: History?,
    override val resolution: Resolution?,
    override val properties: Properties?,
    override val localFile: LocalFile?,
    override val remoteAddress: RemoteAddress?
) : Media(
    id = id,
    uri = uri,
    title = title,
    displayName = displayName,
    folder = folder,
    history = history,
    properties = properties,
    localFile = localFile,
    remoteAddress = remoteAddress
), Media.Visual {

    constructor(
        uri: Uri,
        displayName: String
    ) : this(
        id = generateId(),
        uri = uri,
        title = null,
        displayName = displayName,
        folder = null,
        history = null,
        resolution = null,
        properties = null,
        localFile = null,
        remoteAddress = null
    )

    constructor(
        uri: Uri,
        title: String,
        displayName: String,
        localFile: LocalFile,
        history: History
    ) : this(
        id = generateId(),
        uri = uri,
        title = title,
        displayName = displayName,
        folder = null,
        history = history,
        resolution = null,
        properties = null,
        localFile = localFile,
        remoteAddress = null
    )

}