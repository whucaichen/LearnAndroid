package com.example.chance.learn_gridview.business;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.chance.learn_gridview.R;
import com.example.chance.learn_gridview.Welcome;
import com.example.chance.learn_gridview.business.transfer.FragTransInfo;

public class ProcDoTransfer extends AppCompatActivity {

    private TextView tv_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proc_do_transfer);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);  //标题栏左按钮

        tv_account = (TextView) findViewById(R.id.tv_account);
        tv_account.setText("账户："+ Welcome.cardnum);

        FragTransInfo fragTransInfo = new FragTransInfo();
        getFragmentManager().beginTransaction().replace(R.id.fl_transfer, fragTransInfo).commit();
    }

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
