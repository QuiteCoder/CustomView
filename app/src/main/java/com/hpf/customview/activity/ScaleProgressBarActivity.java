package com.hpf.customview.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hpf.customview.R;
import com.hpf.customview.widget.DashBoardProgressBar;

import java.util.Queue;

public class ScaleProgressBarActivity extends AppCompatActivity {

    private TextView tv;
    private DashBoardProgressBar pb;
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progressbar);
        tv = findViewById(R.id.tv_index);
        pb = findViewById(R.id.pb);
        pb.setOnIndexChangeListener(new DashBoardProgressBar.OnIndexChangeListener() {
            @Override
            public void onIndexChange(int index) {
                Log.d(TAG, "HPF[onIndexChange] index = " + index);
                tv.setText("index = " + index);
            }
        });
        pb.setValue("300");
    }


}
