package com.hpf.customview.widget

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.View

const val OPEN_ANGLE = 120f
const val RADIUS = 300f

class DashBoardView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    val TAG: String = "DashBoardView"
    var screenWidth: Int = 0
    var screenHeight: Int = 0
    var paint : Paint = Paint().apply {
        // 去除锯齿
        isAntiAlias = true
        //线宽
        strokeWidth = 2f
        //实心、空心
        style = Paint.Style.STROKE
    }


    init {
        val displayMetrics = Resources.getSystem().displayMetrics;
        screenWidth = displayMetrics.widthPixels;// 表示屏幕的像素宽度，单位是px（像素）
        screenHeight =
            displayMetrics.heightPixels;// 表示屏幕的像素高度，单位是px（像素），获取到的高度不包含navigationBar和StatusBar
        Log.d(TAG, "init  screenWidth = $screenWidth  screenHeight = $screenHeight")
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawArc(screenWidth / 2f - RADIUS,
            screenHeight / 2 - RADIUS,
            screenWidth / 2 + RADIUS,
            screenHeight / 2 + RADIUS,
            90 + OPEN_ANGLE / 2f,
            360 - OPEN_ANGLE,
            false,
            paint
        )
        //canvas.drawLine(100f,100f,200f,200f,paint)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }
}