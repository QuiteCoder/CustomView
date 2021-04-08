package com.hpf.customview.activity

import android.app.Activity
import android.content.Intent
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    private var mHandler : Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            Log.d(TAG,"handleMessage ${msg.what}")
            when(msg.what) {

            }
        }
    }

    fun shutterButton(view: View) {
        val intent = Intent(this, ShutterButtonActivity::class.java)
        startActivity(intent)
    }

    fun gotoScaleAnimationPage(view: View) {
        val intent = Intent(this, ActivityScaleAnimation::class.java)
        startActivity(intent)
    }

    fun dashboardView(view: View) {
        val intent = Intent(this, DashBoardViewActivity::class.java)
        startActivity(intent)
    }

    fun materialEditText(view: View) {
        val intent = Intent(this, MaterialEditTextActivity::class.java)
        startActivity(intent)
    }

    fun multilineText(view: View) {
        val intent = Intent(this, MultilineTextActivity::class.java)
        startActivity(intent)
    }

    fun multitouchview(view: View) {
        val intent = Intent(this, MultiTouchViewActivity::class.java)
        startActivity(intent)
    }
}