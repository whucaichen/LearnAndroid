package com.example.chance.learn_fragment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button bt_static;
    private Button bt_dynamic;
    private Button bt_slider;
    private Button bt_tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_static = (Button) findViewById(R.id.bt_static);
        bt_dynamic = (Button) findViewById(R.id.bt_dynamic);
        bt_slider = (Button) findViewById(R.id.bt_slider);
        bt_tab = (Button) findViewById(R.id.bt_tab);

        bt_static.setOnClickListener(this);
        bt_dynamic.setOnClickListener(this);
        bt_slider.setOnClickListener(this);
        bt_tab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.bt_static:
                intent.setClass(this, StaticFragActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_dynamic:
                intent.setClass(this, DynamicFragActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_slider:
                intent.setClass(this, SliderActivity.class);
                startActivity(intent);
                break;
//            case R.id.bt_tab:
//                intent.setClass(this, TabActivity.class);
//                startActivity(intent);
//                break;
            default:
                break;
        }
    }
}
