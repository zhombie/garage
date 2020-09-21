package kz.q19.utils.keyboard

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.showKeyboard(view: View) {
    if (view.requestFocus()) {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
}

fun View.showKeyboard(): Boolean {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    return inputMethodManager.showSoftInput(this, 0)
}

fun Activity.hideKeyboard(view: View? = null) {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    inputMethodManager?.hideSoftInputFromWindow(currentFocus?.windowToken ?: view?.windowToken, 0)
}

fun View.hideKeyboard(): Boolean {
    try {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (ignored: RuntimeException) {
    }
    return false
}