package com.example.chance.learn_gridview.business.cash;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chance.learn_gridview.R;
import com.example.chance.learn_gridview.Welcome;
import com.example.chance.learn_gridview.business.ProcDrawCash;
import com.example.chance.learn_gridview.kit.AtmKit;

public class FragCashSelect extends Fragment implements View.OnClickListener {

    private RadioGroup rg_cash;
    private RadioButton rb_100;
    private RadioButton rb_200;
    private RadioButton rb_300;
    private RadioButton rb_500;
    private RadioButton rb_1000;
    private RadioButton rb_2000;

    private Button bt_previous;
    private Button bt_next;
    private TextView tv_cash;
    private WebView wv_cash_select;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cash_select, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        selectCash();   //金额选择动画

        tv_cash = (TextView) getActivity().findViewById(R.id.tv_cash);
        bt_previous = (Button) getActivity().findViewById(R.id.bt_previous);
        bt_next = (Button) getActivity().findViewById(R.id.bt_next);
        bt_previous.setOnClickListener(this);
        bt_next.setOnClickListener(this);

        rg_cash = (RadioGroup) getActivity().findViewById(R.id.rg_cash);
        rb_100 = (RadioButton) getActivity().findViewById(R.id.rb_100);
        rb_200 = (RadioButton) getActivity().findViewById(R.id.rb_200);
        rb_300 = (RadioButton) getActivity().findViewById(R.id.rb_300);
        rb_500 = (RadioButton) getActivity().findViewById(R.id.rb_500);
        rb_1000 = (RadioButton) getActivity().findViewById(R.id.rb_1000);
        rb_2000 = (RadioButton) getActivity().findViewById(R.id.rb_2000);

        rg_cash.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_100:
                        ProcDrawCash.draw_cash_sum = 100;
                        break;
                    case R.id.rb_200:
                        ProcDrawCash.draw_cash_sum = 200;
                        break;
                    case R.id.rb_300:
                        ProcDrawCash.draw_cash_sum = 300;
                        break;
                    case R.id.rb_500:
                        ProcDrawCash.draw_cash_sum = 500;
                        break;
                    case R.id.rb_1000:
                        ProcDrawCash.draw_cash_sum = 1000;
                        break;
                    case R.id.rb_2000:
                        ProcDrawCash.draw_cash_sum = 2000;
                        break;
                    default:
                        ProcDrawCash.draw_cash_sum = 0;
                        break;
                }
                if(ProcDrawCash.draw_cash_sum>0){
                    tv_cash.setText("已选择金额："+ ProcDrawCash.draw_cash_sum);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_previous:
                getActivity().finish();
                break;
            case R.id.bt_next:
                if(ProcDrawCash.draw_cash_sum<=0){
                    Toast.makeText(getActivity(), "请选择取款金额！", Toast.LENGTH_LONG).show();
                    return;
                }
                confirmDialog();
                break;
            default:
                break;
        }
    }

    private void selectCash() {
        wv_cash_select = (WebView) getActivity().findViewById(R.id.wv_cash_select);

        WebSettings settings = wv_cash_select.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        wv_cash_select.loadUrl("file:///android_asset/cash_select.gif");
    }

    private void confirmDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle("业务信息提醒（取款）");
        dialogBuilder.setMessage("确认取款"+ ProcDrawCash.draw_cash_sum+ "元吗？");

        dialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialoginterface, int which) {
                if(!AtmKit.prepareCash(Welcome.cardnum, ProcDrawCash.draw_cash_sum)){
                    Toast.makeText(getActivity(), "你的账户余额不足！", Toast.LENGTH_SHORT).show();
                    return;
                }
                FragmentTransaction fragTrans = getFragmentManager().beginTransaction();
                fragTrans.replace(R.id.fl_cash, new FragCashAnima());
                fragTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragTrans.addToBackStack(null);
                fragTrans.commit();
            }
        });
        dialogBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialoginterface, int which) {
                Toast.makeText(getActivity(), "你选择了取消", Toast.LENGTH_LONG).show();
                return;
            }
        });
        dialogBuilder.create().show();
    }
}
