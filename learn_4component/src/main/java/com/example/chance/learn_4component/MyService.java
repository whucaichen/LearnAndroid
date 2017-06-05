package com.example.chance.learn_4component;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by Chance on 2016/6/22.
 */
public class MyService extends Service{

    /**
     * onBind 是 Service 的虚方法，因此我们不得不实现它。
     * 返回 null，表示客服端不能建立到此服务的连接。
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("Log_MyService", "MyService onCreate……");
        Toast.makeText(this, "MyService onCreate……", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.e("Log_MyService", "MyService onStart……");
        Toast.makeText(this, "MyService onStart……", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("Log_MyService", "MyService onDestroy……");
        Toast.makeText(this, "MyService onDestroy……", Toast.LENGTH_SHORT).show();
    }

//    Thread thread = new Thread(){
//        @Override
//        public void run() {
//            super.run();
//            while (true){
//                try {
//                    System.out.println("MyService is running at "+ new Date());
//                    sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    };
}
