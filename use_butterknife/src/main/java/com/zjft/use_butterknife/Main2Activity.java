package com.zjft.use_butterknife;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Main2Activity extends BaseActivity {

    final static String TAG = "Log_Main2";

    @Bind(R.id.bt1)
    Button bt1;
    @Bind(R.id.bt2)
    Button bt2;
    @Bind(R.id.bt3)
    Button bt3;
    @Bind(R.id.tv)
    TextView tv;

    @Override
    public int getContentViewId() {
        return R.layout.activity_main2;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        Log.e(TAG, "initAllMembersView");
    }

    @OnClick({R.id.bt1, R.id.bt2, R.id.bt3, R.id.tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt1:
                tv.setText(String.valueOf(bt1.getId()));
                break;
            case R.id.bt2:
                tv.setText(String.valueOf(bt2.getId()));
                break;
            case R.id.bt3:
                tv.setText(String.valueOf(bt3.getId()));
                break;
            case R.id.tv:
                tv.setText(String.valueOf(tv.getId()));
                break;
        }
    }

    @OnClick(R.id.bt1)
    public void submit(Button button){
        button.setText("Submitted");
    }

    @OnClick(R.id.bt2)
    public void submit(View view){
        ((Button)view).setText("Converted");
    }
}
