package com.hpf.customview.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.SystemClock
import android.util.AttributeSet
import android.util.Log
import android.view.View

/**
 * Created by huangpengfei on 20200720.
 * shutter button circle progressbar
 */
class CircleProgressBar(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val TAG = this.javaClass.simpleName

    /**
     * 画笔
     */
    private val mPaint = Paint().apply {
        // 去除锯齿
        isAntiAlias = true
        //防抖动
        isDither = true
    }

    /**
     * 进度
     */
    private var mProgress = 50f

    /**
     * 最大进度
     */
    private val mMaxProgress = 100

    /**
     * 圆心颜色
     */
    private val mShutterButtonColor = Color.parseColor("#80f5f5f5")

    /**
     * 边框颜色，也就是进度的颜色
     */
    private val mProgressBarColor = Color.parseColor("#88FFFFFF")

    /**
     * 边框的宽度，也就是进度条的宽度
     */
    private val mBorderWidth = 5

    /**
     * 自身的宽高
     */
    private var mWidth = 0
    private var mHeight = 0

    /**
     * 半径
     */
    private var mRadius = 0

    /**
     * 矩形区域
     */
    private val mContentRectF = RectF()

    /**
     * 最小弧度，进度条过度模式最小的弧度
     */
    private val mMinProgress = 5

    /**
     * 圆周的角度
     */
    private val CIRCULAR = 360f

    /**
     * 开始角度，在过度动画中使用
     */
    private var mStartAngle = -90f
    private var mEndAngle = 90f
    private var mShouldInversion = false
    private lateinit var mTrackThread: Thread
    private var mIsKilled = true

    fun start() {
        startThread()
    }

    fun stop() {
        mIsKilled = true
        try {
            mTrackThread!!.interrupt()
            mTrackThread!!.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = measuredWidth
        mHeight = measuredHeight
        mRadius = if (mWidth > mHeight) mHeight / 2 else mWidth / 2
        // 计算要绘制的区域
        /*mContentRectF[(mWidth / 2 - mRadius + mBorderWidth / 2).toFloat(), (mHeight / 2 - mRadius + mBorderWidth / 2).toFloat(), (
                mWidth / 2 + mRadius - mBorderWidth / 2).toFloat()] = (mHeight / 2 + mRadius - mBorderWidth / 2).toFloat()*/

        mContentRectF.set(
            (mWidth / 2 - mRadius + mBorderWidth / 2).toFloat(),
            (mHeight / 2 - mRadius + mBorderWidth / 2).toFloat(),
            (
                    mWidth / 2 + mRadius - mBorderWidth / 2).toFloat(),
            (mHeight / 2 + mRadius - mBorderWidth / 2).toFloat()
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawIntermediateProgress(canvas)
    }

    /**
     * 绘制过度进度条
     */
    private fun drawIntermediateProgress(canvas: Canvas) {
        // 首先画出背景圆
        mPaint.color = mShutterButtonColor
        mPaint.style = Paint.Style.FILL
        // 这里减去了边框的宽度
        canvas.drawCircle(
            (mWidth / 2).toFloat(),
            (mHeight / 2).toFloat(),
            (mRadius - mBorderWidth - 10).toFloat(),
            mPaint
        )
        // 绘制当前的进度

        // 画出进度条
        mPaint.color = mProgressBarColor
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = mBorderWidth.toFloat()
        mPaint.strokeCap = Paint.Cap.ROUND
        // 计算圆弧划过的角度
        val angle = CIRCULAR / mMaxProgress * mProgress
        if (mShouldInversion) {
            val angleInversion = CIRCULAR / mMaxProgress * mProgress
            val endAngleInversion = mEndAngle % CIRCULAR / mMaxProgress * mProgress
            val offset =
                mEndAngle % CIRCULAR / mMaxProgress * mMinProgress * (1 - mProgress / mMaxProgress)
            Log.d(TAG, "  angleInversion= $angleInversion  mEndAngle = $mEndAngle  offset = $offset")
            canvas.drawArc(
                mContentRectF,
                mStartAngle + angleInversion,
                mEndAngle - endAngleInversion + offset,
                false,
                mPaint
            )
        } else {
            canvas.drawArc(mContentRectF, mStartAngle, angle, false, mPaint)
            mEndAngle = angle
        }
    }

    private fun startThread() {
        if (!mIsKilled) return
        mIsKilled = false
        mTrackThread = Thread {
            while (!mIsKilled && !Thread.interrupted()) {
                mProgress++
                postInvalidate()
                mStartAngle += 2f
                if (mProgress == 95f) {
                    mShouldInversion = !mShouldInversion
                    mProgress = 5f
                    mStartAngle = mStartAngle - CIRCULAR / mMaxProgress * mMinProgress
                }
                if (mStartAngle == 360f) mStartAngle = 0f
                if (mEndAngle == 360f) mEndAngle = 0f
                SystemClock.sleep(10)
            }
        }
        mTrackThread.start()
    }

}