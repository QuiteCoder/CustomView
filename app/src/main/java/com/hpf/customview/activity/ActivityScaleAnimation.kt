package com.hpf.customview.activity

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.hpf.customview.R

class ActivityScaleAnimation : AppCompatActivity() {
    lateinit var mPreviewFrame : FrameLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scale_animation)
        initView()
    }

    private fun initView() {
        mPreviewFrame = findViewById(R.id.peview_frame) as FrameLayout
    }
}