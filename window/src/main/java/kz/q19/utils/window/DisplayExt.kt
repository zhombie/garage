package kz.q19.utils.window

import android.content.Context
import android.os.Build
import android.view.Display
import android.view.WindowManager
import androidx.core.content.ContextCompat

fun Context.getDisplayCompat(): Display? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        display
    } else {
        @Suppress("DEPRECATION")
        ContextCompat.getSystemService(this, WindowManager::class.java)?.defaultDisplay
    }
}