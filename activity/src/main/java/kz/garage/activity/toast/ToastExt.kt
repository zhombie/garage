package kz.garage.activity.toast

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle

fun AppCompatActivity.toast(
    text: CharSequence,
    duration: Int = Toast.LENGTH_SHORT
): Toast? {
    if (lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)) {
        val toast = Toast.makeText(this, text, duration)
        toast.show()
        return toast
    }
    return null
}

fun AppCompatActivity.toast(
    @StringRes textId: Int,
    duration: Int = Toast.LENGTH_SHORT
): Toast? {
    if (lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)) {
        val toast = Toast.makeText(this, textId, duration)
        toast.show()
        return toast
    }
    return null
}