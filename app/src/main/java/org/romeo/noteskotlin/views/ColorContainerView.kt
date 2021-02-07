package org.romeo.noteskotlin.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import androidx.annotation.Dimension.DP
import androidx.annotation.Dimension.PX
import androidx.core.content.ContextCompat
import org.romeo.noteskotlin.R
import org.romeo.noteskotlin.dip


@Dimension(unit = DP)
private const val defRadiusDP = 20

@Dimension(unit = DP)
private const val defStrokeWidthDP = 1

class ColorContainerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val fillPaint = Paint(ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private val strokePaint = Paint(ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }

    private var center: Pair<Float, Float> = Pair(0f, 0f)

    @Dimension(unit = PX)
    private var radiusPX: Float = context.dip(defRadiusDP).toFloat()

    @Dimension(unit = PX)
    private var strokeWidthPX: Float = context.dip(defStrokeWidthDP).toFloat()


    @ColorInt
    var fillColor: Int = Color.WHITE
        set(value) {
            field = value
            fillPaint.color = value
        }

    @ColorInt
    var strokeColor: Int = Color.BLACK
        set(value) {
            field = value
            strokePaint.color = value
        }

    init {
        val typedArray = context.obtainStyledAttributes(
            attrs, R.styleable.ColorContainerView
        )

        strokeColor = typedArray.getColor(
            R.styleable.ColorContainerView_stroke_color,
            strokeColor
        )

        fillColor = typedArray.getColor(
            R.styleable.ColorContainerView_fill_color,
            fillColor
        )

        strokeWidthPX = typedArray.getDimension(
            R.styleable.ColorContainerView_stroke_with,
            strokeWidthPX
        )

        radiusPX = typedArray.getDimension(
            R.styleable.ColorContainerView_radius,
            radiusPX
        )

        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val height = (radiusPX * 2 + paddingTop + paddingBottom).toInt()
        val width = (radiusPX * 2 + paddingStart + paddingEnd).toInt()

        setMeasuredDimension(width, height)

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        center = measuredWidth / 2f to measuredHeight / 2f

        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(
            center.first,
            center.second,
            radiusPX,
            strokePaint
        )

        canvas.drawCircle(
            center.first,
            center.second,
            radiusPX - strokeWidthPX,
            fillPaint
        )

        super.onDraw(canvas)
    }
}