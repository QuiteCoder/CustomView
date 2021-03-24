package com.hpf.customview.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.hpf.customview.R
import com.hpf.customview.widget.CircleProgressBar

class MultilineTextActivity : AppCompatActivity() {
    val TAG : String = this.javaClass.simpleName
    val STOP_ANIMATION : Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.multiline_text_activity)
    }

}