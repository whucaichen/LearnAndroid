package io.whucaichen.verifyhybridappstart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView wv_jscall;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wv_jscall = (WebView) findViewById(R.id.wv_jscall);
        WebSettings webSettings = wv_jscall.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wv_jscall.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public void hello(String name) {
                Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();
            }

            @JavascriptInterface
            public void startApp(String app) {
                Intent intent = getPackageManager().getLaunchIntentForPackage(app);
                if (intent != null) {
                    intent.putExtra("data", "chance");
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Error: [" + app + "] not installed!", Toast.LENGTH_LONG).show();
                }
            }
        }, "egos");
        wv_jscall.setWebViewClient(new WebViewClient());
//        wv_jscall.loadUrl("file:///android_asset/index.html");
        wv_jscall.loadUrl("http://10.34.10.233/index.html");
    }
}
