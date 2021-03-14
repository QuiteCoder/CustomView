package com.hpf.customview.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

const val OPEN_ANGLE = 120f
const val RADIUS = 300f
const val gridCount = 21
const val dashHandLong = RADIUS * 0.85f

class DashBoardView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    val TAG: String = "DashBoardView"
    var screenWidth: Int = 0
    var screenHeight: Int = 0
    private var paint: Paint = Paint().apply {
        // 去除锯齿
        isAntiAlias = true
        //线宽
        strokeWidth = 2f
        //实心、空心
        style = Paint.Style.STROKE
    }

    private val dashPath = Path()
    private val scalePath = Path()
    private var pathDashPathEffect: PathDashPathEffect

    init {
        val displayMetrics = Resources.getSystem().displayMetrics;
        screenWidth = displayMetrics.widthPixels;// 表示屏幕的像素宽度，单位是px（像素）
        screenHeight =
            displayMetrics.heightPixels;// 表示屏幕的像素高度，单位是px（像素），获取到的高度不包含navigationBar和StatusBar
        Log.d(TAG, "init  screenWidth = $screenWidth  screenHeight = $screenHeight")
        paint.strokeWidth = 5f
        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE

        //给path制定一个圆弧
        dashPath.addArc(
            screenWidth / 2f - RADIUS,
            screenHeight / 2 - RADIUS,
            screenWidth / 2 + RADIUS,
            screenHeight / 2 + RADIUS,
            90 + OPEN_ANGLE / 2f,
            360 - OPEN_ANGLE
        )
        val pathMeasure = PathMeasure(dashPath, false)//第二个参数代码要不要闭合
        //获取path的长度
        val length = pathMeasure.length
        //这是刻度的形状,Path.Direction.CCW是顺时针绘制
        scalePath.addRect(0f, 0f, 10f, 15f, Path.Direction.CCW)
        //shape是一个形状，phase是path的偏移量,advance是图形之间的间距
        pathDashPathEffect = PathDashPathEffect(
            scalePath,
            length / gridCount.toFloat(),
            0f,
            PathDashPathEffect.Style.ROTATE
        )
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        //绘制圆弧
        /*canvas.drawArc(screenWidth / 2f - RADIUS,
            screenHeight / 2 - RADIUS,
            screenWidth / 2 + RADIUS,
            screenHeight / 2 + RADIUS,
            90 + OPEN_ANGLE / 2f,
            360 - OPEN_ANGLE,
            false,
            paint
        )*/
        canvas.drawPath(dashPath, paint)
        paint.pathEffect = pathDashPathEffect
        //绘制刻度
        /*canvas.drawArc(screenWidth / 2f - RADIUS,
            screenHeight / 2 - RADIUS,
            screenWidth / 2 + RADIUS,
            screenHeight / 2 + RADIUS,
            90 + OPEN_ANGLE / 2f,
            360 - OPEN_ANGLE,
            false,
            paint
        )*/
        canvas.drawPath(dashPath, paint)
        paint.pathEffect = null
        paint.style = Paint.Style.FILL
        paint.color = Color.parseColor("#465253")
        //绘制表针托盘
        canvas.drawCircle(screenWidth / 2f, screenHeight / 2f, 20.toFloat(), paint)

        paint.color = Color.parseColor("#ff2600")
        //设置阴影效果
        /*radius：模糊半径，radius越大越模糊，越小越清晰，但是如果radius设置为0，则阴影消失不见；
        dx：阴影的横向偏移距离，正值向右偏移，负值向左偏移；
        dy：阴影的纵向偏移距离，正值向下偏移，负值向上偏移；
        color：绘制阴影的画笔颜色，即阴影的颜色（对图片阴影无效）;
        */
        paint.setShadowLayer(5.toFloat(), 15.toFloat(), 15.toFloat(), Color.parseColor("#555555"))
        //计算表针的结束坐标
        //假设表针指向第5根刻度线
        val endx =
            dashHandLong * cos(Math.toRadians(90 + OPEN_ANGLE / 2 + (360 - OPEN_ANGLE) / 20f * 4.toDouble()))
        val endy =
            dashHandLong * sin(Math.toRadians(90 + OPEN_ANGLE / 2 + (360 - OPEN_ANGLE) / 20f * 4.toDouble()))

        canvas.drawLine(
            screenWidth / 2f, screenHeight / 2f, screenWidth / 2f
                    + endx.toFloat(), screenHeight / 2f + endy.toFloat(), paint
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }
}