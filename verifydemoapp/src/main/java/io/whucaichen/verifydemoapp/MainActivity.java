package io.whucaichen.verifydemoapp;

import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.zjft.mvtm.AuthVarify;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "AuthVarify";

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 110) {
                Log.e(TAG, "AuthVarify Successfully.");
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }
    };

    private Thread mThread = new Thread() {
        @Override
        public void run() {
            super.run();
            Uri uri = getIntent().getData();
//            if (uri != null) {//禁止直接点击应用启动
            Log.e(TAG, String.valueOf(new Date()));
            String authVersion = uri.getQueryParameter("authVersion");
            String authData = uri.getQueryParameter("authData");
            boolean verify = AuthVarify.mVTMAuthVerifyData(authData, authVersion);
            Log.e(TAG, "[Verify Result]:" + verify + " [Verify Info]:" + authData + " - " + authVersion);
            if (!verify) {
                Message msg = new Message();
                msg.what = 110;
                mHandler.sendMessage(msg);
                Toast.makeText(MainActivity.this, "AuthVarify Failed.", Toast.LENGTH_SHORT).show();
            }
//            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mThread.start();
    }

}