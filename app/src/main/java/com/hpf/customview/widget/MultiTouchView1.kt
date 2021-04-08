package com.hpf.customview.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.hpf.customview.dp
import com.hpf.customview.getAvatar

class MultiTouchView1(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    var mBitmap = getAvatar(resources, 200.dp.toInt())
    private var paint: Paint = Paint().apply {
        isAntiAlias = true
        //strokeWidth = 2f
        style = Paint.Style.STROKE
    }
    init {

    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(mBitmap,0f, 0f, paint)
    }
}