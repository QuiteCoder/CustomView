package com.hpf.customview.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.*
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
    private var paint : Paint = Paint().apply {
        // 去除锯齿
        isAntiAlias = true
        //线宽
        strokeWidth = 2f
        //实心、空心
        style = Paint.Style.STROKE
    }

    private val scalePath = Path()

    init {
        val displayMetrics = Resources.getSystem().displayMetrics;
        screenWidth = displayMetrics.widthPixels;// 表示屏幕的像素宽度，单位是px（像素）
        screenHeight =
            displayMetrics.heightPixels;// 表示屏幕的像素高度，单位是px（像素），获取到的高度不包含navigationBar和StatusBar
        Log.d(TAG, "init  screenWidth = $screenWidth  screenHeight = $screenHeight")
        paint.strokeWidth = 5f
        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE
        scalePath.addRect(0f, 0f, 10f, 15f, Path.Direction.CCW)
        //paint.pathEffect = PathDashPathEffect(scalePath,50f,0f,PathDashPathEffect.Style.ROTATE)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        //绘制圆弧
        canvas.drawArc(screenWidth / 2f - RADIUS,
            screenHeight / 2 - RADIUS,
            screenWidth / 2 + RADIUS,
            screenHeight / 2 + RADIUS,
            90 + OPEN_ANGLE / 2f,
            360 - OPEN_ANGLE,
            false,
            paint
        )
        paint.pathEffect = PathDashPathEffect(scalePath,50f,0f,PathDashPathEffect.Style.ROTATE)
        //绘制刻度
        canvas.drawArc(screenWidth / 2f - RADIUS,
            screenHeight / 2 - RADIUS,
            screenWidth / 2 + RADIUS,
            screenHeight / 2 + RADIUS,
            90 + OPEN_ANGLE / 2f,
            360 - OPEN_ANGLE,
            false,
            paint
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }
}