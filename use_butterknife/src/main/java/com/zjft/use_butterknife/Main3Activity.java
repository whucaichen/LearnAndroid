package com.zjft.use_butterknife;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindString;

public class Main3Activity extends AppCompatActivity {

    final static String TAG = "Log_Main3";

    //绑定View
    @Bind(R.id.hello_world)
    TextView mHelloWorldTextView;
    @Bind(R.id.app_name)
    TextView mAppNameTextView;//view

    //绑定资源
    @BindString(R.string.app_name)
    String appName;//sting 有问题？
    @BindColor(R.color.red)
    int textColor;//颜色
    @BindDrawable(R.mipmap.ic_launcher)
    Drawable drawable;//drawble
    @Bind(R.id.imageview)
    ImageView mImageView;
    @Bind(R.id.checkbox)
    CheckBox mCheckBox;
    @BindDrawable(R.drawable.selector_image)
    Drawable selector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Log.e(TAG, appName.toString());
        Log.e(TAG, String.valueOf(textColor));
        Log.e(TAG, drawable.toString());
        Log.e(TAG, mImageView.toString());
        Log.e(TAG, mCheckBox.toString());
        Log.e(TAG, selector.toString());
    }
}
