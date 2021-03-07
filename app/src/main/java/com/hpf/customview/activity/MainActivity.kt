package com.hpf.customview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.ImageButton
import com.hpf.customview.R
import com.hpf.customview.widget.CircleProgressBar

class MainActivity : AppCompatActivity() {
    val TAG : String = this.javaClass.simpleName
    //private lateinit var mHandler : Handler
    val STOP_ANIMATION : Int = 1
    lateinit var mShutterProgressBar : CircleProgressBar
    lateinit var mShutterBtn : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mShutterProgressBar = findViewById(R.id.shutter_capture_animation)as CircleProgressBar
        mShutterBtn = findViewById(R.id.shutter_btn)as ImageButton
        mShutterBtn.setOnClickListener {
            mShutterBtn.isClickable = false
            mHandler.sendEmptyMessageDelayed(STOP_ANIMATION,5000)
            mShutterProgressBar.visibility = View.VISIBLE
            mShutterBtn.visibility = View.INVISIBLE
            mShutterProgressBar.start()
        }
    }

    private var mHandler : Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            Log.d(TAG,"handleMessage ${msg.what}")
            when(msg.what) {
                STOP_ANIMATION -> {
                    mShutterProgressBar.visibility = View.INVISIBLE
                    mShutterBtn.visibility = View.VISIBLE
                    mShutterProgressBar.stop()
                    mShutterBtn.isClickable = true
                }
            }
        }
    }
}