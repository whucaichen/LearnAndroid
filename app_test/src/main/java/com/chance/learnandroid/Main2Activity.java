package com.chance.learnandroid;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.chance.learnandroid.R;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FrameLayout fl_test = (FrameLayout) findViewById(R.id.fl_test);
                fl_test.setLayoutParams(new RelativeLayout.LayoutParams(16, 16));
            }
        }, 3000);
    }
}
