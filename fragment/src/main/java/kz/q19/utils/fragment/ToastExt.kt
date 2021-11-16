package kz.q19.utils.fragment

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT): Toast {
    val toast = Toast.makeText(requireContext(), text, duration)
    toast.show()
    return toast
}

fun Fragment.toast(@StringRes textId: Int, duration: Int = Toast.LENGTH_SHORT): Toast {
    val toast = Toast.makeText(requireContext(), textId, duration)
    toast.show()
    return toast
}