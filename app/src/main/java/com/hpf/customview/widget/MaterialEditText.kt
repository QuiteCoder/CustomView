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
val FLOAT_TEXT_X = 5.dp
val FLOAT_TEXT_Y = 20.dp

class MaterialEditText(context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs) {
    private val mPaint = Paint().apply {
        // 去除锯齿
        isAntiAlias = true
        //防抖动
        isDither = true
        textSize = TEXT_SIZE
    }
    private var floatTextShown:Boolean = true
    var floatTextFraction = 0f
        set(value) {
            field = value
            invalidate()
        }
    private val animator: ObjectAnimator by lazy {
        Log.d("lazy","floatTextShown = " + floatTextShown)
        ObjectAnimator.ofFloat(this, "floatTextFraction", 0f, 1f)
    }

    init {
        Log.d("init","floatTextShown = " + floatTextShown)
        setPadding(
            paddingLeft,
            (paddingTop + TEXT_SIZE + TEXT_MARGIN.toInt()).toInt(), paddingRight, paddingBottom
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Log.d("onDraw","floatTextShown = " + floatTextShown)
        //开始绘制悬浮副标题
        if (!hint.isNullOrEmpty()) {
            mPaint.alpha = (floatTextFraction*255).toInt()
            var dY = (1 - floatTextFraction) * FLOAT_TEXT_Y
            canvas.drawText(hint.toString(), FLOAT_TEXT_X, FLOAT_TEXT_Y + dY, mPaint)
        }
    }

    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        Log.d("onTextChanged","floatTextShown = " + floatTextShown)
        /*if (!floatTextShown && !text.isNullOrEmpty()) {
            //播放上升动画
            animator.start()
            floatTextShown = true
        } else if (text.isNullOrEmpty() && floatTextShown) {
            //播放下降动画
            animator.reverse()
            floatTextShown = false
        }*/

    }
}