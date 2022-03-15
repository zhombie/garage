@file:Suppress("NOTHING_TO_INLINE")

package kz.garage.view

import android.view.View

inline fun View.setVisible() {
    visibility = View.VISIBLE
}

inline fun View.setInvisible() {
    visibility = View.INVISIBLE
}

inline fun View.setGone() {
    visibility = View.GONE
}


inline fun View.tryToSetVisible(): Boolean = tryToSetVisibility(View.VISIBLE)

inline fun View.tryToSetInvisible(): Boolean = tryToSetVisibility(View.INVISIBLE)

inline fun View.tryToSetGone(): Boolean = tryToSetVisibility(View.GONE)

inline fun View.tryToSetVisibility(visibility: Int): Boolean =
    if (this.visibility != visibility) {
        this.visibility = visibility
        this.visibility == visibility
    } else false
