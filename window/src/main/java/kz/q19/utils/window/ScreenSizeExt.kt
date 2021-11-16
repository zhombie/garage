package kz.q19.utils.window

import android.content.Context
import android.graphics.Point
import android.view.Display

val Context.screenWidth: Int
    get() {
        val display: Display? = getDisplayCompat()
        val size = Point()
        display?.getSize(size)
        return size.x
    }

val Context.screenHeight: Int
    get() {
        val display: Display? = getDisplayCompat()
        val size = Point()
        display?.getSize(size)
        return size.y
    }