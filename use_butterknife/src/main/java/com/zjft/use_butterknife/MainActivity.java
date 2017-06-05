package com.zjft.use_butterknife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.bt_start2)
    Button btStart2;
    @Bind(R.id.bt_start3)
    Button btStart3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_start2, R.id.bt_start3})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.bt_start2:
                intent.setClass(this, Main2Activity.class);
                break;
            case R.id.bt_start3:
                intent.setClass(this, Main3Activity.class);
                break;
        }
        startActivity(intent);
    }
}
