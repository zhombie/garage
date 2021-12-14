package kz.garage.popup.tooltip

import android.view.View

fun interface DisplayListener {
    fun onDisplay(view: View, show: Boolean)
}