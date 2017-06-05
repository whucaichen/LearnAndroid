package com.zjft.use_glide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class MainActivity extends AppCompatActivity {

    private ImageView iv_glide;
    private TextView tv_info;
    private String flag = "false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_glide = (ImageView) findViewById(R.id.iv_glide);
        tv_info = (TextView) findViewById(R.id.tv_info);
        flag = tv_info.getText().toString();

        iv_glide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag.equals("false")){
                    Glide.with(getApplicationContext()).load(R.drawable.exam)
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv_glide);
                    flag = "true";
                    tv_info.setText(flag);
                }
            }
        });
    }
}
