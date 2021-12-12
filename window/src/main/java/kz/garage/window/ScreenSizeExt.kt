package kz.garage.window

import android.content.Context
import android.graphics.Point
import android.view.Display

val Context.screenWidth: Int
    get() {
        val display: Display? = getDisplayCompat()
        val size = Point()
        // TODO: Get rid of deprecation
        display?.getSize(size)
        return size.x
    }

val Context.screenHeight: Int
    get() {
        val display: Display? = getDisplayCompat()
        val size = Point()
        // TODO: Get rid of deprecation
        display?.getSize(size)
        return size.y
    }