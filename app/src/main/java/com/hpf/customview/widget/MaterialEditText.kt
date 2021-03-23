package com.hpf.customview.widget

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatEditText;
import com.hpf.customview.dp

val TEXT_SIZE = 10.dp
val TEXT_MARGIN = 8.dp

class MaterialEditText(context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs) {
    private val mPaint = Paint().apply {
        // 去除锯齿
        isAntiAlias = true
        //防抖动
        isDither = true
        textSize = TEXT_SIZE
    }
    private var floatTextShown:Boolean = false
    var floatTextFraction = 0f
        set(value) {
            field = value
            invalidate()
        }
    private val animator: ObjectAnimator by lazy {
        Log.d("lazy","onTextChanged  canShowUpAnimation = " + floatTextShown)
        ObjectAnimator.ofFloat(this, "floatTextFraction", 0f, 1f)
    }

    init {
        Log.d("init","onTextChanged  canShowUpAnimation = " + floatTextShown)
        setPadding(
            paddingLeft,
            (paddingTop + TEXT_SIZE + TEXT_MARGIN.toInt()).toInt(), paddingRight, paddingBottom
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Log.d("onDraw","onTextChanged  canShowUpAnimation = " + floatTextShown)
        //开始绘制悬浮副标题
        if (!hint.isNullOrEmpty()) {
            mPaint.alpha = (floatTextFraction*255).toInt()
            canvas.drawText(hint.toString(), 5.dp, 20.dp, mPaint)
        }
    }

    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        Log.d("onTextChanged","onTextChanged  canShowUpAnimation = " + floatTextShown)
        if (!floatTextShown && !text.isNullOrEmpty()) {
            //播放上升动画
            animator.start()
            floatTextShown = true
        } else if (text.isNullOrEmpty() && floatTextShown) {
            //播放下降动画
            animator.reverse()
            floatTextShown = false
        }

    }
}