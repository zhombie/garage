package kz.garage.chat.core.model

import android.os.Parcelable
import kotlin.random.Random

abstract class Entity : Parcelable {

    companion object {
        fun generateId(): String =
            (System.currentTimeMillis() + Random.nextLong(Long.MIN_VALUE, Long.MAX_VALUE)).toString()
    }

    abstract val id: String

    abstract val createdAt: Long?

}