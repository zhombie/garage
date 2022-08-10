package kz.garage.chat.ui

import android.net.Uri
import kz.garage.multimedia.store.model.Content

fun interface ContentSourceProvider {
    sealed interface Source {
        val uri: Uri

        data class LocalFile constructor(
            override val uri: Uri
        ) : Source

        data class RemoteFile constructor(
            override val uri: Uri
        ) : Source
    }

    fun provide(content: Content): Source?
}