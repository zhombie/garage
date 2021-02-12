package kz.q19.utils.keyboard

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import kz.q19.utils.android.inputMethodManager

fun Activity.showKeyboard(view: View) {
    if (view.requestFocus()) {
        inputMethodManager?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
}

fun View.showKeyboard(): Boolean {
    requestFocus()
    return context.inputMethodManager?.showSoftInput(this, 0) == true
}

fun Activity.hideKeyboard(view: View? = null) {
    inputMethodManager?.hideSoftInputFromWindow(currentFocus?.windowToken ?: view?.windowToken, 0)
}

fun View.hideKeyboard(): Boolean {
    try {
        return context.inputMethodManager?.hideSoftInputFromWindow(windowToken, 0) == true
    } catch (ignored: RuntimeException) {
    }
    return false
}