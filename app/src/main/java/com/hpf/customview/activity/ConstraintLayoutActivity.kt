package com.hpf.customview.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.hpf.customview.R

class ConstraintLayoutActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.constraint_activity)

    }

    fun setVisible(view: View) {

        val tv = findViewById<TextView>(R.id.message3)
        if (tv.visibility == View.GONE) {
            tv.visibility = View.VISIBLE
        } else if (tv.visibility == View.VISIBLE) {
            tv.visibility = View.GONE
        }

    }

}
