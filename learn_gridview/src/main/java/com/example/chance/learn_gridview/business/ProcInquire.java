package com.example.chance.learn_gridview.business;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.chance.learn_gridview.R;
import com.example.chance.learn_gridview.Welcome;
import com.example.chance.learn_gridview.business.inquire.FragInfoBase;
import com.example.chance.learn_gridview.business.inquire.FragInfoDetail;

public class ProcInquire extends AppCompatActivity {

    final static String TAG = "LOG_ProcInquire";

    private TabLayout tl_type;
    private TextView tv_account;

    private FragInfoBase fragInfoBase;
    private FragInfoDetail fragInfoDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proc_inquire);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);  //标题栏左按钮

//        //读取Fragment状态
//        if (savedInstanceState != null) {
//            fragInfoDetail = (FragInfoDetail) getFragmentManager().findFragmentByTag("fragInfoDetail");
//            fragInfoBase = (FragInfoBase) getFragmentManager().findFragmentByTag("fragInfoBase");
//        }else {
        fragInfoDetail = new FragInfoDetail();
        fragInfoBase = new FragInfoBase();
        getFragmentManager().beginTransaction().add(R.id.fl_query, fragInfoBase).commit();
//        }

        //设置选项卡
        tl_type = (TabLayout) findViewById(R.id.tl_type);
        tl_type.setTabMode(TabLayout.MODE_FIXED);
        tl_type.addTab(tl_type.newTab().setText("基本信息"));
        tl_type.addTab(tl_type.newTab().setText("账户详情"));
        tl_type.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
//                        getFragmentManager().beginTransaction().
//                                hide(fragInfoDetail).show(fragInfoBase).commit();
                        getFragmentManager().beginTransaction().replace(R.id.fl_query, fragInfoBase).commit();
                        break;
                    case 1:
//                        if (!fragInfoDetail.isAdded()) {
//                            // 隐藏当前的fragment，add下一个到Activity中
//                            getFragmentManager().beginTransaction().
//                                    hide(fragInfoBase).add(R.id.fl_query, fragInfoDetail).commit();
//                        } else {
//                            // 隐藏当前的fragment，显示下一个
//                            getFragmentManager().beginTransaction().
//                                    hide(fragInfoBase).show(fragInfoDetail).commit();
//                        }
                        getFragmentManager().beginTransaction().replace(R.id.fl_query, fragInfoDetail).commit();
                        break;
                    default:
                        break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        tv_account = (TextView) findViewById(R.id.tv_account);
        tv_account.setText("账户：" + Welcome.cardnum);

        FragInfoBase fragInfoBase = new FragInfoBase();
        getFragmentManager().beginTransaction().replace(R.id.fl_query, fragInfoBase).commit();
    }

//    @Override
//    public void onAttachFragment(Fragment fragment) {
//        super.onAttachFragment(fragment);
//        Log.e(TAG, "onAttachFragment");
//        if (fragInfoBase == null && fragment instanceof FragInfoBase) {
//            fragInfoBase = (FragInfoBase)fragment;
//        }
//        if (fragInfoDetail == null && fragment instanceof FragInfoDetail) {
//            fragInfoDetail = (FragInfoDetail)fragment;
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:// 点击返回图标事件
                this.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
