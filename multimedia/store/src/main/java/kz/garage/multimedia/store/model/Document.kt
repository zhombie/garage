package kz.garage.multimedia.store.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Document constructor(
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
), Parcelable