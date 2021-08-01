package com.hpf.customview.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageButton
import androidx.core.os.TraceCompat
import com.hpf.customview.LaunchTimer
import com.hpf.customview.R
import com.hpf.customview.widget.CircleProgressBar

class MainActivity : AppCompatActivity() {
    val TAG : String = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_CustomView)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG,"MainActivity create")
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

    fun scaleProgressBar(view: View) {
        val intent = Intent(this, ScaleProgressBarActivity::class.java)
        startActivity(intent)
    }
}