package kz.garage.image.load

import android.graphics.drawable.Drawable

sealed interface Response {

    val drawable: Drawable?

    data class Success constructor(
        override val drawable: Drawable
    ) : Response

    data class Error constructor(
        override val drawable: Drawable?,
        val throwable: Throwable
    ) : Response

}
