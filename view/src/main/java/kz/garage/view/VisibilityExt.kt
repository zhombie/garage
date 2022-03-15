package kz.garage.view

import android.view.View

fun View.tryToSetVisibility(visibility: Int): Boolean =
    if (this.visibility != visibility) {
        this.visibility = visibility
        this.visibility == visibility
    } else {
        false
    }
