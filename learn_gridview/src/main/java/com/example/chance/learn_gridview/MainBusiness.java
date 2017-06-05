package com.example.chance.learn_gridview;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chance.learn_gridview.business.ProcDoTransfer;
import com.example.chance.learn_gridview.business.ProcDrawCash;
import com.example.chance.learn_gridview.business.ProcInquire;
import com.example.chance.learn_gridview.business.ProcOther;
import com.example.chance.learn_gridview.kit.AtmKit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainBusiness extends AppCompatActivity {

    final static String TAG = "LOG_MainBusiness";
    public static Handler handler;  //后台ATM线程

    private TextView tv_account;
    private GridView gv_business;
    private SimpleAdapter simpleAdapter;
    private MenuItem menuItem;

    private int[] icon = {R.drawable.pic_search, R.drawable.pic_draw, R.drawable.pic_transfer,
            R.drawable.pic_deposit, R.drawable.pic_man, R.drawable.pic_info,
            R.drawable.pic_seurity, R.drawable.pic_charge1, R.drawable.pic_other};
    private String[] iconName = {"查询", "取款", "转账", "存款", "个人", "明细", "安全", "充值", "其它"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);  //标题栏左按钮

        handler = new Handler();

        gv_business = (GridView) findViewById(R.id.gv_business);
        tv_account = (TextView) findViewById(R.id.tv_account);
        tv_account.setText("账户：" + Welcome.cardnum);

        String[] from = {"image", "text"};
        int[] to = {R.id.iv_item_pic, R.id.tv_item_name};
        simpleAdapter = new SimpleAdapter(this, getData(), R.layout.item_grid, from, to);

        gv_business.setAdapter(simpleAdapter);
        gv_business.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch (position) {
                    case 0:
                        intent = new Intent(MainBusiness.this, ProcInquire.class);
                        break;
                    case 1:
                        intent = new Intent(MainBusiness.this, ProcDrawCash.class);
                        break;
                    case 2:
                        intent = new Intent(MainBusiness.this, ProcDoTransfer.class);
                        break;
                    default:
                        intent = new Intent(MainBusiness.this, ProcOther.class);
                }
                startActivity(intent);
            }
        });
    }

    public List<Map<String, Object>> getData() {
        List<Map<String, Object>> data_list = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            data_list.add(map);
        }
        return data_list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.card_out, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_card_out:
                Toast.makeText(this, "请取回你的卡片……", Toast.LENGTH_SHORT).show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AtmKit.popoutCard();    //退卡
                        finish();
                    }
                }, 3000);
                return true;
            case android.R.id.home:// 点击返回图标事件
                this.finish();
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
