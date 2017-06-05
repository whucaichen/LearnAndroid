package com.zjft.use_circularanim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button bt_show;
    private Button bt_hide;
    private Button bt_start;
    private Button bt_start2;
    private ImageView iv_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_show = (Button) findViewById(R.id.bt_show);
        bt_hide = (Button) findViewById(R.id.bt_hide);
        bt_start = (Button) findViewById(R.id.bt_start);
        bt_start2 = (Button) findViewById(R.id.bt_start2);
        iv_img = (ImageView) findViewById(R.id.iv_img);
        bt_show.setOnClickListener(this);
        bt_hide.setOnClickListener(this);
        bt_start.setOnClickListener(this);
        bt_start2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        float r = (float) Math.sqrt(iv_img.getWidth()*iv_img.getWidth()
                + iv_img.getHeight()*iv_img.getHeight())/2;
        switch (v.getId()){
            case R.id.bt_show:
                iv_img.setVisibility(View.VISIBLE);
//                CircularAnimUtil.show(iv_img);
                CircularAnimUtil.show(iv_img, 0, 3000);
                break;
            case R.id.bt_hide:
                CircularAnimUtil.hide(iv_img, 0, 3000);
//                iv_img.setVisibility(View.INVISIBLE);
                break;
            case R.id.bt_start:
                CircularAnimUtil.startActivity(this, Main2Activity.class, bt_start, R.color.colorPrimary);
                break;
            case R.id.bt_start2:
                CircularAnimUtil.startActivity(this, Main2Activity.class, bt_start, R.mipmap.ic_launcher);
                break;
        }
    }
}
