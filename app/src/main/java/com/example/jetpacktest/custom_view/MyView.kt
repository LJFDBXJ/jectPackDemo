package com.example.jetpacktest.custom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.example.jetpacktest.R

/**
 */
class MyView : View {

    private var _exampleString: String? = null

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.MyView, defStyle, 0
        )


        a.recycle()

    }

    val columnWidth = 100
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val widthMode = MeasureSpec.EXACTLY
        val heightMode = MeasureSpec.EXACTLY

        val measureWidth = MeasureSpec.makeMeasureSpec(columnWidth * 3, widthMode)
        val measureHeight = MeasureSpec.makeMeasureSpec(columnWidth * 3, heightMode)
        setMeasuredDimension(measureWidth, measureHeight)
    }

    val paint = Paint()
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.color = Color.BLUE
        canvas.drawCircle(width / 2f, height / 2f, width.toFloat(), paint)
    }
}