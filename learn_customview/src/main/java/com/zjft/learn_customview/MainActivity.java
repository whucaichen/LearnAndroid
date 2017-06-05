package com.zjft.learn_customview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button bt_self_paint;
    private Button bt_combine;
    private Button bt_inherit;
    private Button bt_rotate;
    private Button bt_anima;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_self_paint = (Button) findViewById(R.id.bt_self_paint);
        bt_self_paint.setOnClickListener(this);
        bt_combine = (Button) findViewById(R.id.bt_combine);
        bt_combine.setOnClickListener(this);
        bt_inherit = (Button) findViewById(R.id.bt_inherit);
        bt_inherit.setOnClickListener(this);
        bt_rotate = (Button) findViewById(R.id.bt_rotate);
        bt_rotate.setOnClickListener(this);
        bt_anima = (Button) findViewById(R.id.bt_anima);
        bt_anima.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.bt_self_paint:
                intent.setClass(this, CounterActivity.class);
                break;
            case R.id.bt_combine:
                intent.setClass(this, TitleActivity.class);
                break;
            case R.id.bt_inherit:
                intent.setClass(this, ListActivity.class);
                break;
            case R.id.bt_rotate:
                intent.setClass(this, RotateActivity.class);
                break;
            case R.id.bt_anima:
                intent.setClass(this, AnimaActivity.class);
                break;
            default:
                intent.setClass(this, ErrorActivity.class);
        }
        startActivity(intent);
    }
}
