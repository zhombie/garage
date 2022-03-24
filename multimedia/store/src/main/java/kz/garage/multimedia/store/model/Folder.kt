package kz.garage.multimedia.store.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

@Parcelize
data class Folder constructor(
    val id: Long,
    val displayName: String? = null,
    val items: List<Content> = emptyList()
) : Parcelable {

    companion object {
        fun generateId(): Long =
            System.currentTimeMillis() + Random.nextLong(Long.MIN_VALUE, Long.MAX_VALUE)
    }

    constructor(
        displayName: String?,
        items: List<Content>
    ) : this(id = generateId(), displayName = displayName, items = items)

    val cover: Uri?
        get() = if (items.isEmpty()) null else items.first().uri

    val itemsCount: Int
        get() = items.size

}
