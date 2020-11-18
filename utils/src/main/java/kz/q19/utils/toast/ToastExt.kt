package kz.q19.utils.toast

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT): Toast {
    val toast = Toast.makeText(this, text, duration)
    toast.show()
    return toast
}

fun Context.toast(@StringRes textId: Int, duration: Int = Toast.LENGTH_SHORT): Toast {
    val toast = Toast.makeText(this, textId, duration)
    toast.show()
    return toast
}