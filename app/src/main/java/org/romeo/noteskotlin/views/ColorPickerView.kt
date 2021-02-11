package org.romeo.noteskotlin.views

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.Dimension
import androidx.annotation.Dimension.DP
import androidx.annotation.Dimension.PX
import androidx.core.view.children
import org.romeo.noteskotlin.R
import org.romeo.noteskotlin.colors
import org.romeo.noteskotlin.dip

private const val PALETTE_ANIMATION_DURATION = 150L
private const val SCALE = "scale"
private const val HEIGHT = "height"

@Dimension(unit = DP)
var circlesRadiusDP: Int = 20

@Dimension(unit = DP)
private val circlesPaddingDP: Int = 8

class ColorPickerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    val isActive
        get() = measuredHeight > 0

    var desiredHeight = 0
    var onColorClickListener: (colorInt: Int) -> Unit = { }

    private val animator by lazy {
        ValueAnimator().apply {
            duration = PALETTE_ANIMATION_DURATION
            addUpdateListener(updateListener)
        }
    }

    private val updateListener by lazy {
        ValueAnimator.AnimatorUpdateListener { animator ->
            layoutParams.apply {
                height = animator.getAnimatedValue(HEIGHT) as Int
            }.let {
                layoutParams = it
            }

            val scale = animator.getAnimatedValue(SCALE) as Float

            for (child in children) {
                child.apply {
                    scaleX = scale
                    scaleY = scale
                    alpha = scale
                }
            }
        }
    }

    init {
        val typedArray = context.obtainStyledAttributes(
            attrs, R.styleable.ColorPickerView
        )

        val addDefaultColors = typedArray.getBoolean(
            R.styleable.ColorPickerView_add_default_colors,
            true
        )

        circlesRadiusDP = typedArray.getDimension(
            R.styleable.ColorPickerView_circles_radius, circlesRadiusDP.toFloat()
        ).toInt()

        if (addDefaultColors) {
            val colors = colors()

            colors.forEach { color ->
                val circle = ColorContainerView(context)
                    .apply {
                        fillColor = color
                        strokeColor = Color.WHITE

                        context.dip(circlesPaddingDP).let {
                            setPadding(it, it, it, it)
                        }

                        setOnClickListener { onColorClickListener(color) }
                    }

                addView(circle)
            }
        }

        typedArray.recycle()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        layoutParams.apply {
            desiredHeight = height
            height = 0
        }.let {
            layoutParams = it
        }
    }

    fun open() {
        animator.setValues(
            PropertyValuesHolder.ofInt(HEIGHT, measuredHeight, desiredHeight),
            PropertyValuesHolder.ofFloat(SCALE, getChildAt(0).scaleX, 1f)
        )

        animator.start()
    }

    fun close() {
        animator.setValues(
            PropertyValuesHolder.ofInt(HEIGHT, measuredHeight, 0),
            PropertyValuesHolder.ofFloat(SCALE, getChildAt(0).scaleX, 0f)
        )

        animator.start()
    }
}