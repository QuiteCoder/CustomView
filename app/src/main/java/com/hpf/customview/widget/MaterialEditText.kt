package com.hpf.customview.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText;

const val TEXT_SIZE = 12f
const val TEXT_MARGIN = 10f
class MaterialEditText(context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs) {
    private val mPaint = Paint().apply {
        // 去除锯齿
        isAntiAlias = true
        //防抖动
        isDither = true
        textSize = TEXT_SIZE
    }

    init {
        setPadding(paddingLeft,
            (paddingTop+TEXT_SIZE+TEXT_MARGIN.toInt()).toInt(),paddingRight,paddingBottom)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //开始绘制悬浮副标题
        if (!hint.isNullOrEmpty()) {
            canvas.drawText(hint.toString(),10f,15f,paint)
        }
    }
}