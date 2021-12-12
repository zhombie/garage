package kz.garage.activity.keyboard

import android.app.Activity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

fun Activity.isKeyboardVisible(): Boolean {
    if (window == null) return false
    return ViewCompat.getRootWindowInsets(window.decorView)
        ?.isVisible(WindowInsetsCompat.Type.ime()) == true
}

fun Activity.showKeyboard(): Boolean {
    if (window == null) return false
    WindowInsetsControllerCompat(window, window.decorView)
        .show(WindowInsetsCompat.Type.ime())
    return true
}

fun Activity.hideKeyboard(): Boolean {
    if (window == null) return false
    WindowInsetsControllerCompat(window, window.decorView)
        .hide(WindowInsetsCompat.Type.ime())
    return true
}