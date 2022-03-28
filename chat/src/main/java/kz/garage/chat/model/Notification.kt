package kz.garage.chat.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Notification constructor(
    override val id: String,

    override val createdAt: Long? = null,

    val body: String
) : Entity(), Parcelable {

    class Builder {
        private var id: String? = null
        private var body: String? = null
        private var createdAt: Long? = null

        fun getId(): String? = id

        fun getBody(): String? = body

        fun getCreatedAt(): Long? = createdAt

        fun setRandomId(): Builder =
            setId(generateId())

        fun setId(id: String?): Builder {
            this.id = id
            return this
        }

        fun setBody(body: String?): Builder {
            this.body = body
            return this
        }

        fun setCreatedAt(createdAt: Long?): Builder {
            this.createdAt = createdAt
            return this
        }

        fun clear(): Builder =
            setId(null)
                .setBody(null)
                .setCreatedAt(null)

        fun build(): Notification =
            Notification(
                id = requireNotNull(id) { "${Notification::class.java.simpleName} ID is not specified!" },
                body = requireNotNull(body) { "${Notification::class.java.simpleName} body is not specified!" },
                createdAt = createdAt,
            )
    }

}