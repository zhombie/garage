package kz.garage.samples.chat.coil

import android.content.Context

internal object Preconditions {
    @JvmStatic
    fun checkNotNull(context: Context?): Context = requireNotNull(context)
}