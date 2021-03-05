package com.example.jetpacktest.custom_view

import android.content.Context
import android.graphics.Color
import android.graphics.Outline
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.customview.widget.ViewDragHelper

class CustomFrameLayout : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    var dragHalper: ViewDragHelper? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun addOnLayoutChangeListener(listener: OnLayoutChangeListener?) {
        super.addOnLayoutChangeListener(listener)
        val outLine = CircleOutlineProvider()
        getChildAt(0).outlineProvider = outLine

    }

    init {

        dragHalper = ViewDragHelper.create(this, 1.0f, object : ViewDragHelper.Callback() {
            override fun tryCaptureView(child: View, pointerId: Int): Boolean {
                return childCount > 0
            }

            override fun onViewPositionChanged(
                changedView: View,
                left: Int,
                top: Int,
                dx: Int,
                dy: Int
            ) {
                super.onViewPositionChanged(changedView, left, top, dx, dy)
            }

            override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
                super.clampViewPositionHorizontal(child, left, dx)
                return left
            }

            override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
                super.clampViewPositionVertical(child, top, dy)
                return top
            }

            override fun onViewCaptured(capturedChild: View, activePointerId: Int) {
                super.onViewCaptured(capturedChild, activePointerId)
                getChildAt(0).animate().translationZ(50f).duration = 100
            }

            override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
                super.onViewReleased(releasedChild, xvel, yvel)
                getChildAt(0).animate().translationZ(0f).duration = 100
            }
        })

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        dragHalper?.processTouchEvent(event)
        return true
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        val action = ev!!.actionMasked
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            dragHalper?.cancel()
            return false
        }
        return dragHalper!!.shouldInterceptTouchEvent(ev)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private class CircleOutlineProvider : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            view.setBackgroundColor(Color.RED)
            outline.setOval(0, 0, view.width, view.height)
        }
    }
}