package kz.q19.utils.android

import android.content.Context
import android.view.WindowManager
import androidx.core.content.ContextCompat

object ContextUtils {

    fun getWindowManager(context: Context): WindowManager? {
        return ContextCompat.getSystemService(context, WindowManager::class.java)
    }

}