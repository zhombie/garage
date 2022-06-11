@file:Suppress("unused")

package kz.garage.chat.model

import android.location.Location
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kz.garage.chat.model.reply_markup.ReplyMarkup
import kz.garage.multimedia.store.model.*

@Parcelize
data class Message internal constructor(
    override val id: String,

    val direction: Direction,

    override val createdAt: Long? = null,

    val body: String? = null,
    val contents: List<Content>? = null,
    val location: Location? = null,
    val replyMarkup: ReplyMarkup? = null,
) : Entity(), Parcelable {

    enum class Direction {
        OUTGOING,
        INCOMING
    }

    class Builder {
        private var id: String? = null
        private var direction: Direction? = null
        private var createdAt: Long? = null
        private var body: String? = null
        private var contents: List<Content>? = null
        private var location: Location? = null
        private var replyMarkup: ReplyMarkup? = null

        fun getId(): String? = id

        fun getDirection(): Direction? = direction

        fun getCreatedAt(): Long? = createdAt

        fun getBody(): String? = body

        fun getContents(): List<Content>? = contents

        fun getLocation(): Location? = location

        fun getReplyMarkup(): ReplyMarkup? = replyMarkup

        fun setRandomId(): Builder =
            setId(generateId())

        fun setId(id: String?): Builder {
            this.id = id
            return this
        }

        fun setIncomingDirection(): Builder =
            setDirection(Direction.INCOMING)

        fun setOutgoingDirection(): Builder =
            setDirection(Direction.OUTGOING)

        fun setDirection(direction: Direction?): Builder {
            this.direction = direction
            return this
        }

        fun setCreatedAt(createdAt: Long?): Builder {
            this.createdAt = createdAt
            return this
        }

        fun setBody(body: String?): Builder {
            this.body = body
            return this
        }

        fun setContent(content: Content?): Builder =
            setContents(if (content == null) null else listOf(content))

        fun setContents(contents: List<Content>?): Builder {
            this.contents = contents
            return this
        }

        fun setLocation(location: Location?): Builder {
            this.location = location
            return this
        }

        fun setReplyMarkup(replyMarkup: ReplyMarkup?): Builder {
            this.replyMarkup = replyMarkup
            return this
        }

        fun clear(): Builder =
            setId(null)
                .setDirection(null)
                .setCreatedAt(null)
                .setBody(null)
                .setContents(null)
                .setLocation(null)
                .setReplyMarkup(null)

        fun build(): Message =
            Message(
                id = requireNotNull(id) { "${Message::class.java.simpleName} ID is not specified!" },
                direction = requireNotNull(direction) { "${Message::class.java.simpleName} direction is not specified!" },
                createdAt = createdAt,
                body = body,
                contents = contents,
                location = location,
                replyMarkup = replyMarkup
            )
    }

    fun isIncoming(): Boolean = direction == Direction.INCOMING

    fun isOutgoing(): Boolean = direction == Direction.OUTGOING

    fun isEmpty(): Boolean =
        body.isNullOrBlank()
                && contents.isNullOrEmpty()
                && location == null
                && replyMarkup == null

    fun isTextMessage(): Boolean =
        !body.isNullOrBlank()
                && contents.isNullOrEmpty()
                && location == null
                && replyMarkup == null

    fun isNullableTextMessageWithImages(): Boolean =
        isNullableTextMessageWithContents<Image>()

    fun isNullableTextMessageWithVideos(): Boolean =
        isNullableTextMessageWithContents<Video>()

    fun isNullableTextMessageWithAudio(): Boolean =
        isNullableTextMessageWithContents<Audio>()

    fun isNullableTextMessageWithDocuments(): Boolean =
        isNullableTextMessageWithContents<Document>()

    inline fun <reified T> isNullableTextMessageWithContents(): Boolean =
        isNullableTextMessageWithContents<T> { it.isAnyFileExists() }

    inline fun <reified T> isNullableTextMessageWithContents(
        predicate: (content: Content) -> Boolean
    ): Boolean =
        (!contents.isNullOrEmpty() && contents.all { it is T && predicate.invoke(it) })
                && location == null
                && replyMarkup == null

    fun isTextMessageWithImages(): Boolean = isTextMessageWithContents<Image>()

    fun isTextMessageWithVideos(): Boolean = isTextMessageWithContents<Video>()

    fun isTextMessageWithAudio(): Boolean = isTextMessageWithContents<Audio>()

    fun isTextMessageWithDocuments(): Boolean = isTextMessageWithContents<Document>()

    inline fun <reified T> isTextMessageWithContents(): Boolean =
        isTextMessageWithContents<T> { it.isAnyFileExists() }

    inline fun <reified T> isTextMessageWithContents(
        predicate: (content: Content) -> Boolean
    ): Boolean =
        !body.isNullOrBlank()
                && (!contents.isNullOrEmpty() && contents.all { it is T && predicate.invoke(it) })
                && location == null
                && replyMarkup == null

}