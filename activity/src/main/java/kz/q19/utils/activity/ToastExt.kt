package kz.q19.utils.activity

import android.app.Activity
import android.widget.Toast
import androidx.annotation.StringRes

fun Activity.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT): Toast {
    val toast = Toast.makeText(this, text, duration)
    toast.show()
    return toast
}

fun Activity.toast(@StringRes textId: Int, duration: Int = Toast.LENGTH_SHORT): Toast {
    val toast = Toast.makeText(this, textId, duration)
    toast.show()
    return toast
}