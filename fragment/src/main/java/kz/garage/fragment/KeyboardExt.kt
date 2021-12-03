package kz.garage.fragment

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment

fun Fragment.isKeyboardVisible(): Boolean {
    val view = view ?: return false
    return ViewCompat.getRootWindowInsets(view)
        ?.isVisible(WindowInsetsCompat.Type.ime()) == true
}

fun Fragment.showKeyboard(): Boolean {
    val activity = activity ?: return false
    WindowInsetsControllerCompat(activity.window, activity.window.decorView)
        .show(WindowInsetsCompat.Type.ime())
    return true
}

fun Fragment.hideKeyboard(): Boolean {
    val activity = activity ?: return false
    WindowInsetsControllerCompat(activity.window, activity.window.decorView)
        .hide(WindowInsetsCompat.Type.ime())
    return true
}