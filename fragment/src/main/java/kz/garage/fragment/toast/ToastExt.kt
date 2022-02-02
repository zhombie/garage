package kz.garage.fragment.toast

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle

fun Fragment.toast(
    text: CharSequence,
    duration: Int = Toast.LENGTH_SHORT
): Toast? {
    if (lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)) {
        if (context == null) return null
        val toast = Toast.makeText(requireContext(), text, duration)
        toast.show()
        return toast
    }
    return null
}

fun Fragment.toast(
    @StringRes textId: Int,
    duration: Int = Toast.LENGTH_SHORT
): Toast? {
    if (lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)) {
        if (context == null) return null
        val toast = Toast.makeText(requireContext(), textId, duration)
        toast.show()
        return toast
    }
    return null
}