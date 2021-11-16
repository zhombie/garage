package kz.q19.utils.view.outline

import android.graphics.Outline
import android.os.Build
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.RequiresApi

/**
 * [ViewOutlineProvider] witch works with [RoundMode]
 * @param outlineRadius corner radius
 * @param roundMode mode for corners
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class RoundOutlineProvider constructor(
    var outlineRadius: Float = NO_OUTLINE_RADIUS,
    var roundMode: RoundMode = RoundMode.NONE
) : ViewOutlineProvider() {

    companion object {
        const val NO_OUTLINE_RADIUS: Float = 0F
    }

    private val leftOffset: Int
        get() = when (roundMode) {
            RoundMode.BOTTOM_RIGHT -> cornerRadius.toInt()
            else -> 0
        }

    private val topOffset: Int
        get() = when (roundMode) {
            RoundMode.ALL, RoundMode.TOP -> 0
            else -> cornerRadius.toInt()
        }

    private val rightOffset: Int
        get() = when (roundMode) {
            RoundMode.BOTTOM_LEFT -> cornerRadius.toInt()
            else -> 0
        }

    private val bottomOffset: Int
        get() = when (roundMode) {
            RoundMode.NONE, RoundMode.TOP -> cornerRadius.toInt()
            else -> 0
        }

    private val cornerRadius: Float
        get() = if (roundMode == RoundMode.NONE) {
            0F
        } else {
            outlineRadius
        }

    override fun getOutline(view: View, outline: Outline) {
        outline.setRoundRect(
            0 - leftOffset,
            0 - topOffset,
            view.width + rightOffset,
            view.height + bottomOffset,
            cornerRadius
        )
    }
}