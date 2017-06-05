package com.example.chance.learn_hybrid;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Contacts;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "*ANDROID_LOVE_JS*";
    final static String TEST_NET = "http://10.32.2.8:8080/Test/index.html";
    final static String IMAGE_FILE = "/mnt/sdcard/test.jpg";

    private WebView wv_jscall;

    private Handler handler;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();
        wv_jscall = (WebView) findViewById(R.id.wv_jscall);

        WebSettings webSettings = wv_jscall.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不加载缓存
        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放

        /**
         * 使用JS SDK注入本地方法回调
         */
        wv_jscall.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public void hello(String name) {
                Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();
            }
            @JavascriptInterface
            public void dialNum(String phoneNum){
                Log.e(TAG, "在WebView中通过JS调用拨号");
                MainActivity.this.dialNum(phoneNum);
            }
            @JavascriptInterface
            public void pickContact(){
                Log.e(TAG, "在WebView中通过JS选择联系人");
                MainActivity.this.pickContact();
            }
            @JavascriptInterface
            public void pickImg(){
                Log.e(TAG, "在WebView中通过JS挑选图片");
                MainActivity.this.pickImg();
            }
            @JavascriptInterface
            public void takePhoto() {
                Log.e(TAG, "在WebView中通过JS调用相机");
                MainActivity.this.takePhoto();
            }
        }, "egos");

        /**
         * 使用WebChromeClient自带的回调方法覆盖JS的弹出框
         */
        wv_jscall.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                Log.e(TAG, "js call native onJsPrompt");
                Log.e(TAG, "url:"+url);
                Log.e(TAG, "message:"+message);
                Log.e(TAG, "defaultValue:"+defaultValue);
                Log.e(TAG, "result:"+String.valueOf(result));
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Log.e(TAG, "js call native onJsAlert");
                return super.onJsAlert(view, url, message, result);
            }
            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                Log.e(TAG, "js call native onJsConfirm");
                return super.onJsConfirm(view, url, message, result);
            }
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                MainActivity.this.setTitle("Loading...");
                MainActivity.this.setProgress(newProgress * 100);
                if(newProgress == 100)
                    MainActivity.this.setTitle(R.string.app_name);
            }
        });

//        wv_jscall.loadUrl(TEST_NET);
        wv_jscall.loadUrl("file:///android_asset/index.html");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case 0:
                Log.e(TAG, data.getDataString());
                break;
            case 6:
                if (resultCode == RESULT_OK) {
                    Log.e(TAG, "照相完成");
//                    //如果是本地内容
//                    BitmapFactory.Options options = new BitmapFactory.Options();
//                    options.inSampleSize = 2;
//                    Bitmap bitmap = BitmapFactory.decodeFile(IMAGE_FILE, options);
//                    imageView.setImageBitmap(bitmap);
                    handler.post(new Runnable() {
                        public void run() {
//                            wv_jscall.loadUrl("javascript:changeImg('" + IMAGE_FILE+ "')");
                            wv_jscall.loadUrl("javascript:changeImg()");
                        }
                    });
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wv_jscall.canGoBack()) {
            wv_jscall.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 调用Android系统组件
     */
    private void dialNum(String phoneNum){
        Uri uri = Uri.parse("tel:"+ phoneNum);
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);

        startActivity(intent);
    }
    private void pickContact(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setData(Contacts.People.CONTENT_URI);

        startActivityForResult(intent, 0);
    }
    private void pickImg(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");

        startActivityForResult(intent, 1);
    }
    private void takePhoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = Uri.fromFile(new File(IMAGE_FILE));// /mnt/sdcard/test.jpg 是照片存储目录
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

        startActivityForResult(intent, 2);
    }

    /**
     * Android 回调 JS
     */
    private void callbackWebView(){
        handler.post(new Runnable() {
            String value = "nativeReturn";
            public void run() {
                // 此处调用 HTML 中的javaScript 函数
                Log.e(TAG, "去调用callbackjs");
                //传一个参数
                wv_jscall.loadUrl("javascript:callbackjs('" + value + "')");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Toast.makeText(this, "正在跳转设置界面……", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_callback:
                callbackWebView();
                return true;
            case R.id.action_about:
                Toast.makeText(this, "这是一个混合应用示例！", Toast.LENGTH_SHORT).show();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
