package com.example.chance.learn_4component;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class LearnService extends AppCompatActivity implements View.OnClickListener{

    private Button bt_startS;
    private Button bt_stopS;
    private Button bt_bindS;
    private Button bt_unbindS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_service);

        bt_startS = (Button) findViewById(R.id.bt_startS);
        bt_stopS = (Button) findViewById(R.id.bt_stopS);
        bt_bindS = (Button) findViewById(R.id.bt_bindS);
        bt_unbindS = (Button) findViewById(R.id.bt_unbindS);

        bt_startS.setOnClickListener(this);
        bt_stopS.setOnClickListener(this);
        bt_bindS.setOnClickListener(this);
        bt_unbindS.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_startS:
//                Intent intent1 = new Intent();
//                intent1.setClass(this, MyService);
//                Intent intent2
                startService(new Intent(this, MyService.class));
                break;
            case R.id.bt_stopS:
                stopService(new Intent(this, MyService.class));
                break;
            case R.id.bt_bindS:
                bindService(new Intent(this, MyService.class), serviceConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.bt_unbindS:
                unbindService(serviceConnection);
                break;
            default:
                break;
        }
    }

    //监听连接的状态
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("Log_MyService", "MyService onServiceConnected……");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("Log_MyService", "MyService onServiceDisconnected……");
        }
    };
}
