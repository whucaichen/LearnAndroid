package com.example.chance.learn_4component;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Chance on 2016/6/22.
 */
public class MyReceiver extends BroadcastReceiver {

    // action 名称
    String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    String NET_STATE_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";

    public void onReceive(Context context, Intent intent) {

        // 一个receiver可以接收多个action的，即可以有多个intent-filter，需要在onReceive里面对intent.getAction(action name)进行判断。
        if (intent.getAction().equals(SMS_RECEIVED)) {
            Log.e("Log_MyBroadcastReceiver", "拦截到一条诈骗短信");
            //获取上一个广播的bundle数据
            Bundle bundle = getResultExtras(true);//true：前一个广播没有结果时创建新的Bundle；false：不创建Bundle
            bundle.putString("data", "这是一条健康短信");
            //将bundle数据放入广播中传给下一个广播接收者
            setResultExtras(bundle);

            //终止广播传给下一个广播接收者
            abortBroadcast();
        }

        if (intent.getAction().equals(NET_STATE_CHANGE)) {
            if (isNetworkAvailable(context)) {
//                Toast.makeText(context, "网络可用", Toast.LENGTH_SHORT);
                Log.e("Log_MyBroadcastReceiver", "网络可用");
            } else {
//                Toast.makeText(context, "网络不可用", Toast.LENGTH_SHORT);
                Log.e("Log_MyBroadcastReceiver", "网络不可用");
            }
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info = mgr.getAllNetworkInfo();
        if (info != null) {
            for (int i = 0; i < info.length; i++) {
                if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }
}
