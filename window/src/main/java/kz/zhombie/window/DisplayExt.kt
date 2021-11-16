package kz.zhombie.window

import android.content.Context
import android.os.Build
import android.view.Display
import android.view.WindowManager
import androidx.core.content.ContextCompat

fun Context.getDisplayCompat(): Display? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        display
    } else {
        ContextCompat.getSystemService(this, WindowManager::class.java)?.defaultDisplay
    }
}