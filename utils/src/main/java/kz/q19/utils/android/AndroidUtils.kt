package kz.q19.utils.android

import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.view.Display
import android.view.WindowManager

object AndroidUtils {

    /**
     * Convert density independent pixels value (dip) into pixels value (px).
     * @param dp Value needed to convert
     * @ return Converted value in pixels.
     */
    fun dpToPx(dp: Int): Float {
        return dpToPx(dp.toFloat())
    }

    /**
     * Convert density independent pixels value (dip) into pixels value (px).
     * @param dp Value needed to convert
     * @return Converted value in pixels.
     */
    fun dpToPx(dp: Float): Float {
        return dp * Resources.getSystem().displayMetrics.density
    }

    /**
     * Returns display pixel density.
     * @return display density value in pixels (pixel count per one dip).
     */
    fun getDisplayDensity(): Float {
        return Resources.getSystem().displayMetrics.density
    }

    /**
     * Convert pixels value (px) into density independent pixels (dip).
     * @param px Value needed to convert
     * @return Converted value in pixels.
     */
    fun pxToDp(px: Int): Float {
        return pxToDp(px.toFloat())
    }

    /**
     * Convert pixels value (px) into density independent pixels (dip).
     * @param px Value needed to convert
     * @return Converted value in pixels.
     */
    fun pxToDp(px: Float): Float {
        return px / Resources.getSystem().displayMetrics.density
    }

    fun getScreenWidth(context: Context): Int {
        val windowManager: WindowManager? = ContextUtils.getWindowManager(context)
        val display: Display? = windowManager?.defaultDisplay
        val size = Point()
        display?.getSize(size)
        return size.x
    }

    fun getScreenHeight(context: Context): Int {
        val windowManager: WindowManager? = ContextUtils.getWindowManager(context)
        val display: Display? = windowManager?.defaultDisplay
        val size = Point()
        display?.getSize(size)
        return size.y
    }

}