package com.zjft.learn_service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {

    private EditText et_data;
    private TextView tv_data;
    private Button bt_start_service;
    private Button bt_stop_service;
    private Button bt_bind_service;
    private Button bt_unbind_service;
    private Button bt_sync_data;
    private Button bt_start_app;

    private MyService.MyBinder myBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_data = (EditText) findViewById(R.id.et_data);
        tv_data = (TextView) findViewById(R.id.tv_data);
        bt_start_service = (Button) findViewById(R.id.bt_start_service);
        bt_start_service.setOnClickListener(this);
        bt_stop_service = (Button) findViewById(R.id.bt_stop_service);
        bt_stop_service.setOnClickListener(this);
        bt_bind_service = (Button) findViewById(R.id.bt_bind_service);
        bt_bind_service.setOnClickListener(this);
        bt_unbind_service = (Button) findViewById(R.id.bt_unbind_service);
        bt_unbind_service.setOnClickListener(this);
        bt_sync_data = (Button) findViewById(R.id.bt_sync_data);
        bt_sync_data.setOnClickListener(this);
        bt_start_app = (Button) findViewById(R.id.bt_start_app);
        bt_start_app.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MyService.class);
        switch (v.getId()) {
            case R.id.bt_start_service:
                intent.putExtra("DATA", et_data.getText().toString());
                startService(intent);
                break;
            case R.id.bt_stop_service:
                stopService(new Intent(this, MyService.class));
                break;
            case R.id.bt_bind_service:
                bindService(intent, this, BIND_AUTO_CREATE);
                break;
            case R.id.bt_unbind_service:
                unbindService(this);
                break;
            case R.id.bt_sync_data:
                if (myBinder != null) {
                    myBinder.setData(et_data.getText().toString());
                }
                break;
            case R.id.bt_start_app:
                startApp();
                break;
        }
    }

    private void startApp() {
        Intent intent = getPackageManager().getLaunchIntentForPackage("com.example.chance.learn_4component");
        if (intent != null) {
            intent.putExtra("data", "chance");
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "应用未安装", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {

        myBinder = (MyService.MyBinder) service;
        myBinder.getService().setCallBack(new MyService.CallBack() {
            @Override
            public void onDataChange(String data) {
                Message msg = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("DATA", data);
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        });
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            String data = msg.getData().getString("DATA");
            tv_data.setText(data);
        }
    };
}
