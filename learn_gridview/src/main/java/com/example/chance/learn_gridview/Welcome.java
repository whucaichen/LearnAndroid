package com.example.chance.learn_gridview;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chance.learn_gridview.kit.AtmKit;
import com.oushangfeng.marqueelayout.MarqueeLayout;
import com.oushangfeng.marqueelayout.MarqueeLayoutAdapter;

import java.util.ArrayList;
import java.util.List;

public class Welcome extends AppCompatActivity {

    private MarqueeLayout ml_ads;
    private WebView wv_card_wait;

    private Handler handler;
    public static String cardnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        AtmKit.initATM();   //初始化ATM
        handler = new Handler();
        showAds();  //轮播广告
        waitCard();  //等待插卡动画
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
            case R.id.action_card_in:
                Toast.makeText(this, "正在读卡中，请等待……", Toast.LENGTH_SHORT).show();
                wv_card_wait.loadUrl("file:///android_asset/card_read.gif");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        while (true){   //等待插卡
                            if(AtmKit.receiveCard()){   //模拟接收到了插卡信号
                                cardnum = AtmKit.getCardNum();
                                showDialog();  //输入密码
                                break;
                            }
                        }
                    }
                }, 3000);
                return true;
            case R.id.action_about:
                Toast.makeText(this, "ZJ-ATM, written by Chance.", Toast.LENGTH_SHORT).show();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialog(){
        LayoutInflater factory = LayoutInflater.from(this);
        final View view = factory.inflate(R.layout.dialog_password, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("请输入你的银行卡密码:");
        dialogBuilder.setView(view);

        TextView tv_cardNum = (TextView) view.findViewById(R.id.tv_cardNum);
        final EditText et_cardPW = (EditText) view.findViewById(R.id.et_cardPW);
        tv_cardNum.setText(cardnum);

        dialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int whichButton){

                if(AtmKit.verifyInfo(cardnum, et_cardPW.getText().toString())){
                    Toast.makeText(Welcome.this, "输入完成", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Welcome.this, MainBusiness.class);
//                    intent.putExtra("Account", cardnum);    //向下传递账户
                    startActivity(intent);
                    wv_card_wait.loadUrl("file:///android_asset/card_wait.gif");
                }else {
                    Toast.makeText(Welcome.this, "密码输入错误！", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();   //可设置重输次数，本次简化
                }
            }
        });
        dialogBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int whichButton){
                dialog.dismiss();
            }
        });
        dialogBuilder.create().show();
    }

    private void showAds(){
        ml_ads = (MarqueeLayout) findViewById(R.id.ml_ads);

        final List<String> imgs = new ArrayList<>();    //广告位
        imgs.add("http://www.zjft.com/cn/images/banner/155.jpg");
        imgs.add("http://www.zjft.com/cn/images/banner/152.jpg");
        imgs.add("http://www.zjft.com/cn/images/banner/154.jpg");
        imgs.add("http://www.zjft.com/cn/images/banner/151.jpg");
        imgs.add("http://www.zjft.com/cn/images/banner/153.jpg");
        MarqueeLayoutAdapter<String> adapter1 = new MarqueeLayoutAdapter<>();
        adapter1.setCustomView(ml_ads, R.layout.item_simple_image, imgs, new MarqueeLayoutAdapter.InitViewCallBack<String>() {
            @Override
            public void init(View view, String item) {
                Glide.with(view.getContext()).load(item).into((ImageView) view);
            }
        });
        ml_ads.setAdapter(adapter1);
        ml_ads.start();
    }

    private void waitCard(){
        wv_card_wait = (WebView) findViewById(R.id.wv_card_wait);

        WebSettings settings = wv_card_wait.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        wv_card_wait.loadUrl("file:///android_asset/card_wait.gif");
    }
}
