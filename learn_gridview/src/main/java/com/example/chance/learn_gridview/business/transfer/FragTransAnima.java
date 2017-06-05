package com.example.chance.learn_gridview.business.transfer;

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
import com.example.chance.learn_gridview.kit.AtmKit;

public class FragTransAnima extends Fragment implements View.OnClickListener{

    private Button bt_previous;
    private Button bt_next;

    private WebView wv_trans_anima;

    private boolean result;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tran_sum, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        bt_previous = (Button) getActivity().findViewById(R.id.bt_previous);
        bt_next = (Button) getActivity().findViewById(R.id.bt_next);
        bt_previous.setOnClickListener(this);
        bt_next.setOnClickListener(this);

        transferCash();  //转账等待动画

        MainBusiness.handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), "正在进行转账操作，请等待……", Toast.LENGTH_SHORT).show();
                result = AtmKit.doTransfer(Welcome.cardnum, FragTransInfo.num, FragTransInfo.cash);    //转账
            }
        });

        MainBusiness.handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (result) {
                    wv_trans_anima.loadUrl("file:///android_asset/biz_complete.png");
                    Toast.makeText(getActivity(), "转账完成！", Toast.LENGTH_SHORT).show();
                } else {
                    AtmKit.handlError();
                    Toast.makeText(getActivity(), "系统错误！", Toast.LENGTH_SHORT).show();
                }
            }
        }, 3000);
    }

    private void transferCash() {
        wv_trans_anima = (WebView) getActivity().findViewById(R.id.wv_trans_anima);

        WebSettings settings = wv_trans_anima.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        wv_trans_anima.loadUrl("file:///android_asset/trans_run.gif");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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

    private void returnMainDialog(){
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
