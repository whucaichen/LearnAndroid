package com.example.chance.learn_hybrid;

import android.content.Context;
import android.util.Log;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by Chance on 2016/7/1.
 */
public class MyWebChromeClient extends WebChromeClient {

    private Context context;

    public MyWebChromeClient(Context context) {
        this.context = context;
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        Log.e(MainActivity.TAG, "js call native onJsPrompt");
        Log.e(MainActivity.TAG, "url:"+url);
        Log.e(MainActivity.TAG, "message:"+message);
        Log.e(MainActivity.TAG, "defaultValue:"+defaultValue);
        Log.e(MainActivity.TAG, "result:"+String.valueOf(result));
        return super.onJsPrompt(view, url, message, defaultValue, result);
    }
    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        Log.e(MainActivity.TAG, "js call native onJsAlert");
//        callbackWebView();
        return super.onJsAlert(view, url, message, result);
    }
    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        Log.e(MainActivity.TAG, "js call native onJsConfirm");
//        callbackWebView();
        return super.onJsConfirm(view, url, message, result);
    }
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
//        MainActivity.this.setTitle("Loading...");
//        MainActivity.this.setProgress(newProgress * 100);
//        if(newProgress == 100)
//            MainActivity.this.setTitle(R.string.app_name);
    }

    public Context getContext() {
        return context;
    }
}
