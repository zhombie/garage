package kz.garage.chat.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

internal fun ViewGroup.inflate(
    @LayoutRes layoutRes: Int,
    isAttachedToRoot: Boolean = false
): View =
    LayoutInflater.from(context)
        .inflate(layoutRes, this, isAttachedToRoot)
