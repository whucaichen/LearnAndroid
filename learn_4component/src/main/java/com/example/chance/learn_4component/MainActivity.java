package com.example.chance.learn_4component;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_a;
    private Button bt_s;
    private Button bt_cp;
    private Button bt_br;
    private EditText et_dataIn;
    private EditText et_dataOut;
    private MyReceiver myReceiver = new MyReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_a = (Button) findViewById(R.id.bt_a);
        bt_s = (Button) findViewById(R.id.bt_s);
        bt_cp = (Button) findViewById(R.id.bt_cp);
        bt_br = (Button) findViewById(R.id.bt_br);
        et_dataIn = (EditText) findViewById(R.id.et_dataIn);
        et_dataOut = (EditText) findViewById(R.id.et_dataOut);

        bt_a.setOnClickListener(this);
        bt_s.setOnClickListener(this);
        bt_cp.setOnClickListener(this);
        bt_br.setOnClickListener(this);

        registerReceiver(myReceiver, new IntentFilter());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_a:
                learnActivity();
                break;
            case R.id.bt_s:
                learnService();
                break;
            case R.id.bt_cp:
                learnContentProvider();
                break;
            case R.id.bt_br:
                learnBroadcastReciever();
                break;
            default:
                break;
        }
    }

    private void learnActivity() {
        Intent intent = new Intent(this, LearnActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("data", et_dataIn.getText().toString());
        intent.putExtras(bundle);
//        startActivity(intent);
        startActivityForResult(intent, 0);
    }

    private void learnService() {
        Intent intent = new Intent(this, LearnService.class);
        startActivity(intent);
    }

    private void learnContentProvider() {
        Intent intent = new Intent(this, LearnProvider.class);
        startActivity(intent);
    }

    private void learnBroadcastReciever() {
        Intent intent = new Intent("android.provider.Telephony.SMS_RECEIVED");
        intent.putExtra("data", "这是一条诈骗短信！");
        //发送一条有序广播
        sendOrderedBroadcast(intent, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 0:
                et_dataOut.setText("登录失败，无有效登录信息");
                break;
            case 1:
                et_dataOut.setText("登录成功，用户名为：" +
                        data.getExtras().getString("name") +
                        "，密码为：" + data.getExtras().getString("password"));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }
}
