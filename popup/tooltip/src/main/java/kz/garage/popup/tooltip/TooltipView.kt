package kz.garage.popup.tooltip

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.core.view.ViewCompat

class TooltipView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private lateinit var parent: View
    private lateinit var parentRect: Rect
    private lateinit var rect: Rect

    internal val childView: ChildView

    private val bubblePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    internal val borderPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var bubblePath: Path? = null
    private var hasInverted = false

    internal var corner: Int = 0
    internal var paddingTop: Int = 0
    internal var paddingBottom: Int = 0
    internal var paddingEnd: Int = 0
    internal var paddingStart: Int = 0
    internal var shadowPadding: Float = 0F
    internal var distanceWithView: Int = 0
    internal var position: Position = Position.BOTTOM
    internal var minHeight: Int = 0
    internal var minWidth: Int = 0
    internal var leftMargin: Int = 0
    internal var arrowHeight: Float = 0F
    internal var arrowWidth: Float = 0F

    init {
        setWillNotDraw(false)

        childView = ChildView(context, attrs, defStyleAttr)
        childView.textView.setTextColor(Color.WHITE)
        addView(childView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val resources = context.resources
        val padding = resources.getDimensionPixelSize(R.dimen.popup_tooltip_padding)

        paddingStart = padding
        paddingTop = padding
        paddingEnd = padding
        paddingBottom = padding

        corner = resources.getDimensionPixelSize(R.dimen.popup_tooltip_corner)
        arrowHeight = resources.getDimensionPixelSize(R.dimen.popup_tooltip_arrow_height).toFloat()
        arrowWidth = resources.getDimensionPixelSize(R.dimen.popup_tooltip_arrow_width).toFloat()
        shadowPadding = resources.getDimensionPixelSize(R.dimen.popup_tooltip_shadow_padding).toFloat()
        leftMargin = resources.getDimensionPixelSize(R.dimen.popup_tooltip_screen_border_margin)
        minWidth = resources.getDimensionPixelSize(R.dimen.popup_tooltip_min_width)
        minHeight = resources.getDimensionPixelSize(R.dimen.popup_tooltip_min_height)

        bubblePaint.style = Paint.Style.FILL
        borderPaint.style = Paint.Style.STROKE

        setShadow(resources.getDimensionPixelSize(R.dimen.popup_tooltip_shadow_width).toFloat())
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var wms = widthMeasureSpec
        var hms = heightMeasureSpec
        val wSpec = MeasureSpec.getSize(wms)
        val hSpec = MeasureSpec.getSize(hms)
        val wCalculate = calculateWidth(wSpec)
        val hCalculate = calculateHeight(hSpec)
        val margin = distanceWithView + leftMargin + borderPaint.strokeWidth

        if (!hasInverted && (wCalculate < minWidth + margin || hCalculate < minHeight + margin)) {
            invertCurrentPosition()
            hasInverted = true
        } else {
            wms = MeasureSpec.makeMeasureSpec(wCalculate, MeasureSpec.AT_MOST)
            hms = MeasureSpec.makeMeasureSpec(hCalculate, MeasureSpec.AT_MOST)
        }

        setPadding()
        super.onMeasure(wms, hms)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setupPosition(rect, w, h)

        bubblePath = drawBubble(w.toFloat(), h.toFloat())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        bubblePath?.let { bubblePath ->
            canvas.drawPath(bubblePath, bubblePaint)
            canvas.drawPath(bubblePath, borderPaint)
        }
    }

    fun setShadow(r: Float, @ColorInt color: Int = 0xffaaaaaa.toInt()) {
        bubblePaint.setShadowLayer(r, 0f, 0f, if (r == 0f) Color.TRANSPARENT else color)
    }

    private fun invertCurrentPosition() {
        position = when (position) {
            Position.START -> Position.END
            Position.TOP -> Position.BOTTOM
            Position.END -> Position.START
            Position.BOTTOM -> Position.TOP
        }
    }

    private fun setPadding() {
        val extraDistance = (borderPaint.strokeWidth + arrowHeight + distanceWithView).toInt()

        when (getRelativePosition()) {
            Position.START -> setPadding(
                paddingStart,
                paddingTop,
                paddingEnd + extraDistance,
                paddingBottom
            )
            Position.TOP -> setPadding(
                paddingStart,
                paddingTop,
                paddingEnd,
                paddingBottom + extraDistance
            )
            Position.END -> setPadding(
                paddingStart + extraDistance,
                paddingTop,
                paddingEnd,
                paddingBottom
            )
            Position.BOTTOM -> setPadding(
                paddingStart,
                paddingTop + extraDistance,
                paddingEnd,
                paddingBottom
            )
        }
    }

    private fun drawBubble(w: Float, h: Float): Path {
        val path = Path()
        val corner = if (corner < 0) 0f else corner.toFloat()
        val distance = distanceWithView + borderPaint.strokeWidth
        val arrowDistance = arrowHeight + distance

        val position = getRelativePosition()

        val start = if (position == Position.END) arrowDistance else distance
        val top = if (position == Position.TOP) arrowDistance else distance
        val end = if (position == Position.START) arrowDistance else distance
        val bottom = if (position == Position.BOTTOM) arrowDistance else distance

        path.moveTo(start, corner + bottom)

        if (position == Position.END) {
            path.lineTo(start, (h - arrowWidth) / 2)
            path.lineTo(0f, h / 2)
            path.lineTo(start, (h + arrowWidth) / 2)
        }

        path.lineTo(start, h - corner - top)
        path.quadTo(start, h - top, start + corner, h - top)

        if (position == Position.TOP) {
            path.lineTo((w - arrowWidth) / 2, h - top)
            path.lineTo(w / 2, h)
            path.lineTo((w + arrowWidth) / 2, h - top)
        }

        path.lineTo(w - corner - end, h - top)
        path.quadTo(w - end, h - top, w - end, h - corner - top)

        if (position == Position.START) {
            path.lineTo(w - end, (h + arrowWidth) / 2)
            path.lineTo(w, h / 2)
            path.lineTo(w - end, (h - arrowWidth) / 2)
        }

        path.lineTo(w - end, bottom + corner)
        path.quadTo(w - end, bottom, w - corner - end, bottom)

        if (position == Position.BOTTOM) {
            path.lineTo((w + arrowWidth) / 2, bottom)
            path.lineTo(w / 2, 0f)
            path.lineTo((w - arrowWidth) / 2, bottom)
        }

        path.lineTo(corner + start, bottom)
        path.quadTo(start, bottom, start, corner + bottom)
        path.close()

        return path
    }

    private fun getRelativePosition(): Position {
        val rtl = ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL

        if (!rtl) {
            return position
        }

        return when (position) {
            Position.END -> Position.START
            Position.START -> Position.END
            else -> position
        }
    }

    private fun getOffset(myLength: Int, hisLength: Int): Int {
        return (hisLength - myLength) / 2
    }

    private fun calculatePosition(offset: Int, size: Int, begin: Int, maxVal: Int): Int {
        val pos = begin + offset

        return if (pos < 0 && begin + size < maxVal) {
            begin
        } else if (pos < 0 || pos + size > maxVal) {
            maxVal - size
        } else {
            pos
        }
    }

    private fun setupPosition(rect: Rect, width: Int, height: Int) {
        val distance = (distanceWithView + borderPaint.strokeWidth).toInt()
        val x: Int
        val y: Int

        if (position == Position.START || position == Position.END) {
            x = if (position == Position.START) {
                rect.left - width - distance
            } else {
                rect.right + distance
            }

            y = calculatePosition(
                getOffset(height, rect.height()),
                height,
                if (rect.top < +leftMargin) leftMargin else rect.top,
                calculateHeight(parent.height) + leftMargin
            )
        } else {
            y = if (position == Position.BOTTOM) {
                rect.bottom + distance
            } else {  // top
                rect.top - height - distance
            }

            x = calculatePosition(
                getOffset(width, rect.width()),
                width,
                if (rect.left < leftMargin) leftMargin else rect.left,
                calculateWidth(parent.width) + leftMargin
            )
        }

        val rtl = ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL

        translationX = (x - parentRect.left).toFloat() * if (rtl) -1 else 1
        translationY = (y - parentRect.top).toFloat()
    }

    fun setup(viewRect: Rect, tooltipParent: View) {
        this.parent = tooltipParent
        this.parentRect = Rect()
        parent.getGlobalVisibleRect(parentRect)
        this.rect = viewRect
    }

    private fun calculateWidth(width: Int): Int {
        val parentLP = parent.layoutParams as? MarginLayoutParams
        val maxWidth = parent.width - (parentLP?.leftMargin ?: 0) -
                (parentLP?.rightMargin ?: 0) - parent.paddingLeft - parent.paddingRight

        return if (position == Position.START &&
            width > rect.left - leftMargin - distanceWithView
        ) {
            rect.left - leftMargin - distanceWithView
        } else if (position == Position.END && rect.right + parentRect.left + width >
            maxWidth - rect.right + parentRect.left - leftMargin - distanceWithView
        ) {
            maxWidth - rect.right + parentRect.left - leftMargin - distanceWithView
        } else if ((position == Position.TOP || position == Position.BOTTOM)
            && width > maxWidth - (leftMargin * 2)
        ) {
            maxWidth - (leftMargin * 2)
        } else {
            width
        }
    }

    private fun calculateHeight(height: Int): Int {
        val parentLP = parent.layoutParams as? MarginLayoutParams
        val maxHeight = parent.height - (parentLP?.topMargin ?: 0) -
                (parentLP?.bottomMargin ?: 0) - parent.paddingTop - parent.paddingBottom

        return if (position == Position.TOP && height > rect.top - leftMargin - distanceWithView) {
            rect.top - leftMargin - distanceWithView
        } else if (position == Position.BOTTOM && rect.bottom + parentRect.top + height > maxHeight - rect.bottom + parentRect.top - leftMargin - distanceWithView) {
            maxHeight - rect.bottom + parentRect.top - leftMargin - distanceWithView
        } else if ((position == Position.START || position == Position.END) && height > maxHeight - (leftMargin * 2)) {
            maxHeight - (leftMargin * 2)
        } else {
            height
        }
    }

    fun setColor(color: Int) {
        bubblePaint.color = color
        postInvalidate()
    }

}