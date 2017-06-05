package com.zjft.learn_service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.koushikdutta.async.http.WebSocket;
import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.koushikdutta.async.http.server.HttpServerRequestCallback;

import java.util.ArrayList;
import java.util.List;

public class MyService extends Service {

    final static String TAG = "LOG_MyService";

    private boolean running = false;
    private String data = "Default Service Info";

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    class MyBinder extends Binder {

        public void setData(String data) {
            MyService.this.data = data;
        }

        public MyService getService() {
            return MyService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        data = intent.getStringExtra("DATA");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread() {
            @Override
            public void run() {
                super.run();
                running = true;
                int i = 0;
                while (running) {
                    i++;
                    String str = i + "：" + data;
                    Log.e(TAG, str);
                    if (callBack != null) {
                        callBack.onDataChange(str);
                    }
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                super.run();
                AsyncHttpServer server = new AsyncHttpServer();
                List<WebSocket> _sockets = new ArrayList<WebSocket>();
                server.get("/", new HttpServerRequestCallback() {
                    @Override
                    public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
                        response.send("Hello!!!");
                    }
                });
                server.listen(5000);
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        running = false;
    }

    private CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public CallBack getCallBack() {
        return callBack;
    }

    public interface CallBack {
        void onDataChange(String data);
    }
}
