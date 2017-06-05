package com.example.chance.learn_fragment;

import android.app.Application;
import android.content.Context;

/**
 * Created by Chance on 2016/7/1.
 */
public class MyApplication extends Application {

    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
