package com.chance.learnandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.chance.learnandroid.R;

public class Bt1Activity extends AppCompatActivity {

    private WebView wv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bt1);

        wv1 = (WebView) findViewById(R.id.wv1);
//        String htmlString = "<h1>Title</h1><p>This is HTML text<br /><i>Formatted in italics</i><br />Anothor Line</p>";
        String htmlString = "Test";
        // 载入这个html页面
        wv1.loadData(htmlString, "text/html", "utf-8");
    }
}
