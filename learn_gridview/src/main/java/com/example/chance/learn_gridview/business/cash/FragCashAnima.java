package com.example.chance.learn_gridview.business.cash;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.example.chance.learn_gridview.MainBusiness;
import com.example.chance.learn_gridview.R;
import com.example.chance.learn_gridview.Welcome;
import com.example.chance.learn_gridview.business.ProcDrawCash;
import com.example.chance.learn_gridview.kit.AtmKit;

public class FragCashAnima extends Fragment implements View.OnClickListener {

    private Button bt_previous;
    private Button bt_next;
    private Button bt_fetch;

    private WebView wv_cash_anima;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cash_animation, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        bt_fetch = (Button) getActivity().findViewById(R.id.bt_fetch);
        bt_previous = (Button) getActivity().findViewById(R.id.bt_previous);
        bt_next = (Button) getActivity().findViewById(R.id.bt_next);
        bt_fetch.setOnClickListener(this);
        bt_previous.setOnClickListener(this);
        bt_next.setOnClickListener(this);

        prepareCash();  //取款等待动画

        MainBusiness.handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), "正在配钞中，请等待……", Toast.LENGTH_SHORT).show();
                AtmKit.prepareCash(Welcome.cardnum, ProcDrawCash.draw_cash_sum);    //配钞
            }
        });

        MainBusiness.handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), "正在出钞中，请等待……", Toast.LENGTH_SHORT).show();
                wv_cash_anima.loadUrl("file:///android_asset/cash_out.gif");
                AtmKit.popoutCash(Welcome.cardnum, ProcDrawCash.draw_cash_sum);   //后台ATM取款业务
            }
        }, 3000);

        MainBusiness.handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                wv_cash_anima.loadUrl("file:///android_asset/cash_fetch.gif");
                bt_fetch.setVisibility(View.VISIBLE);   //出钞完成，打开出钞口取钞
            }
        }, 6000);
    }

    private void prepareCash() {
        wv_cash_anima = (WebView) getActivity().findViewById(R.id.wv_cash_anima);

        WebSettings settings = wv_cash_anima.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        wv_cash_anima.loadUrl("file:///android_asset/cash_prepare.gif");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_fetch:
                if (AtmKit.fetchCash()) {
                    wv_cash_anima.loadUrl("file:///android_asset/biz_complete.png");
                    Toast.makeText(getActivity(), "取款完成！", Toast.LENGTH_SHORT).show();
                } else {
                    AtmKit.handlError();
                    Toast.makeText(getActivity(), "系统错误！", Toast.LENGTH_SHORT).show();
                }
                bt_fetch.setVisibility(View.INVISIBLE);
                break;
            case R.id.bt_previous:
                getFragmentManager().popBackStack();
                break;
            case R.id.bt_next:
                returnMainDialog();
                break;
            default:
                break;
        }
    }

    private void returnMainDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle("信息提示：");
        dialogBuilder.setMessage("确认将返回主业务菜单！");
        dialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialoginterface, int which) {
//                Toast.makeText(getActivity(), "你选择了确定", Toast.LENGTH_LONG).show();
                getActivity().finish();
            }
        });
        dialogBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialoginterface, int which) {
                Toast.makeText(getActivity(), "你选择了取消", Toast.LENGTH_LONG).show();
            }
        });
        dialogBuilder.create().show();
    }
}
