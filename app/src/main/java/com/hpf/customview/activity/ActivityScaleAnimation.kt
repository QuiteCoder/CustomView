package com.hpf.customview.activity

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.ScaleAnimation
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.hpf.customview.R
import java.util.*

class ActivityScaleAnimation : AppCompatActivity() {
    val TAG: String = "ActivityScaleAnimation"
    private var mScalAnimationEnd: Boolean = true
    lateinit var mPreviewFrame: FrameLayout
    lateinit var mCoverViewContainer: FrameLayout
    lateinit var preview_container: FrameLayout
    lateinit var mCoverView: View
    var mPreviewTop: Int = 0
    var mPreviewBottom: Int = 0
    var previewHeight: Int = 0
    var previewWidth: Int = 0
    var ScreenWidth: Int = 0
    var ScreenHeight: Int = 0
    var mIsFirstTime: Boolean = true
    var mRequestAnimation: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scale_animation)
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    fun dpToPixel(dp: Float): Int {
        val scale = resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    private fun initView() {
        ScreenWidth = dpToPixel(240.0f)
        ScreenHeight = dpToPixel(480.0f)
        previewHeight = dpToPixel(360.0f)
        previewWidth = dpToPixel(240.0f)
        mCoverView = findViewById(R.id.cover_view) as View
        mCoverView.alpha = 0.0f
        mCoverViewContainer = findViewById(R.id.cover_view_container) as FrameLayout
        preview_container = findViewById(R.id.preview_container) as FrameLayout
        mPreviewFrame = findViewById(R.id.peview_frame) as FrameLayout
        preview_container.addOnLayoutChangeListener(View.OnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            mPreviewTop = mPreviewFrame.top
            mPreviewBottom = mPreviewFrame.bottom
            previewHeight = mPreviewBottom - mPreviewTop
            Log.d(TAG, "mPreviewTop = $mPreviewTop mPreviewBottom= $mPreviewBottom")
            if (mIsFirstTime) {
                setCoverLayoutParams()
                mIsFirstTime = false
            }
            if (mRequestAnimation) {
                showScaleAnimation()
            }
        })
    }

    fun setTo1_1(view: View) {
        val layoutParams = mPreviewFrame.layoutParams as FrameLayout.LayoutParams
        layoutParams.height = ScreenWidth
        layoutParams.width = ScreenWidth
        Log.d(TAG, "setTo1_1 ScreenWidth = " + ScreenWidth + " layoutParams.height = " + ScreenWidth * 4 / 3)
        mPreviewFrame.layoutParams = layoutParams
        mRequestAnimation = true
    }

    fun setTo4_3(view: View) {
        val layoutParams = mPreviewFrame.layoutParams as FrameLayout.LayoutParams
        layoutParams.height = ScreenWidth * 4 / 3
        layoutParams.width = ScreenWidth
        layoutParams.bottomMargin = 100
        Log.d(TAG, "setTo4_3 ScreenWidth = " + ScreenWidth + " layoutParams.height = " + ScreenWidth * 4 / 3)
        mPreviewFrame.layoutParams = layoutParams
        mRequestAnimation = true
    }

    fun setTo16_9(view: View) {
        val layoutParams = mPreviewFrame.layoutParams as FrameLayout.LayoutParams
        layoutParams.height = ScreenWidth * 16 / 9
        layoutParams.width = ScreenWidth
        layoutParams.bottomMargin = 0
        Log.d(
            TAG,
            "setTo16_9 ScreenWidth = " + ScreenWidth + " layoutParams.height = " + ScreenWidth * 16 / 9
        )
        mPreviewFrame.layoutParams = layoutParams
        mRequestAnimation = true
    }

    fun setToFull(view: View) {
        val layoutParams = mPreviewFrame.layoutParams as FrameLayout.LayoutParams
        layoutParams.height = ScreenHeight
        layoutParams.width = ScreenWidth
        layoutParams.bottomMargin = 0
        Log.d(TAG, "setToFull  ScreenWidth = " + ScreenWidth + " layoutParams.height = " + ScreenWidth * 16 / 9
        )
        mPreviewFrame.layoutParams = layoutParams
        mRequestAnimation = true
    }

    fun showScaleAnimation() {
        //mCoverView.visibility = View.VISIBLE
        mCoverView.alpha = 1.0f
        val measuredHeight: Float = mCoverView.height.toFloat()
        val newPreviewHeight: Float = previewHeight.toFloat()

        val toY = newPreviewHeight / measuredHeight
        val coverTop: Int = mCoverView.top
        Log.d(
            TAG,
            "showScaleAnimation  measuredHeight = $measuredHeight newPreviewHeight = $newPreviewHeight coverTop = $coverTop mPreviewTop = $mPreviewTop"
        )
        val pivotYValue: Float =
            Math.abs(coverTop - mPreviewTop) / (Math.abs(toY * measuredHeight - measuredHeight) / 2)
        mCoverViewContainer.setBackgroundColor(Color.BLACK)
        Log.d(TAG, "toY = " + toY + " pivotYValue = " + pivotYValue)
        val scaleAnimation = ScaleAnimation(
            1.0f, 1.0f, 1.0f, toY,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, pivotYValue * 0.5f
        )
        scaleAnimation.duration = 300
        //scaleAnimation.interpolator = DecelerateInterpolator()
        scaleAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                Log.d(TAG, "onAnimationStart")
                mScalAnimationEnd = false
            }

            override fun onAnimationEnd(animation: Animation) {
                Log.d(TAG, "onAnimationEnd")
                mScalAnimationEnd = true
                //mCoverView.visibility = View.INVISIBLE
                mCoverView.alpha = 0.0f
                mCoverViewContainer.setBackgroundColor(Color.TRANSPARENT)
                setCoverLayoutParams()
                mRequestAnimation = false
            }

            override fun onAnimationRepeat(animation: Animation) {
                Log.d(TAG, "onAnimationRepeat")
                mScalAnimationEnd = true
                //mCoverView.visibility = View.INVISIBLE
                mCoverView.alpha = 0.0f
                mCoverViewContainer.setBackgroundColor(Color.TRANSPARENT)
                setCoverLayoutParams()
            }
        })
        mCoverView.startAnimation(scaleAnimation)
    }

    private fun setCoverLayoutParams() {
        Log.d(TAG, "setCoverLayoutParams")
        val lp = mCoverView.getLayoutParams() as FrameLayout.LayoutParams
        val marginBottom: Int = preview_container.bottom - mPreviewBottom
        Log.d(TAG, "setCoverLayoutParams  marginBottom = $marginBottom")
        lp.width = previewWidth
        lp.height = previewHeight
        lp.topMargin = 0
        lp.bottomMargin = marginBottom
        mCoverView.setLayoutParams(lp)
    }


}