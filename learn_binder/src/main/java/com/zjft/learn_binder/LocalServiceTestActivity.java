package com.zjft.learn_binder;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class LocalServiceTestActivity extends AppCompatActivity {

    static final String TAG = "LocalBinderTestActivity";
    ServiceConnection mSc;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSc = new ServiceConnection(){
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(TAG, "service connected");
                LocalService ss = ((LocalService.LocalBinder)service).getService();
                ss.sayHelloWorld();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d(TAG, "service disconnected");
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, this.getApplicationContext().getPackageCodePath());
        Intent service = new Intent(this.getApplicationContext(),LocalService.class);
        this.bindService(service, mSc, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //must unbind the service otherwise the ServiceConnection will be leaked.
        this.unbindService(mSc);
    }
}
