package com.hpf.customview.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.hpf.customview.R
import com.hpf.customview.dp

class MultilineTextView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    var textSample =
        "Lorem Ipsum，也称乱数假文或者哑元文本， 是印刷及排版领域所常用的虚拟文字。" +
                "由于曾经一台匿名的打印机刻意打乱了一盒印刷字体从而造出一本字体样品书，" +
                "Lorem Ipsum从西元15世纪起就被作为此领域的标准文本使用。它不仅延续了五个世纪，" +
                "还通过了电子排版的挑战，其雏形却依然保存至今。在1960年代，”Leatraset”公司发布了印刷着Lorem " +
                "Ipsum段落的纸张，从而广泛普及了它的使用。最近，计算机桌面出版软件”Aldus PageMaker”也通过同样的方式使Lorem Ipsum落入大众的视野。"
    var mTextPaint = TextPaint().apply {
        isAntiAlias = true
        textSize = 15.dp
    }
    var mPaint = TextPaint().apply {
        isAntiAlias = true
        textSize = 15.dp
    }

    var mImagePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    var mBitmap = getAvatar(120.dp.toInt())
    val fontMetrics  =  Paint.FontMetrics()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //val staticLayout =
        //   StaticLayout(textSample, mTextPaint, width, Layout.Alignment.ALIGN_NORMAL, 1f, 0f, false)
        //staticLayout.draw(canvas)
        canvas.drawBitmap(mBitmap,width-120.dp,50.dp ,mImagePaint)
        //FontMetrics类有四个属性，分别是top, ascent, descent, bottom，是与base的相差的距离
        //详情可看FrontMetrics.png
        mTextPaint.getFontMetrics(fontMetrics)
        canvas.drawText(textSample,0f,-fontMetrics.top,mPaint)
    }

    fun getAvatar(width : Int) : Bitmap{
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.mipmap.image, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.mipmap.image, options)
    }
}