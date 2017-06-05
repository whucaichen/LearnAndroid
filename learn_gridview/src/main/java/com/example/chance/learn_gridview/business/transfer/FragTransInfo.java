package com.example.chance.learn_gridview.business.transfer;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chance.learn_gridview.R;
import com.example.chance.learn_gridview.Welcome;
import com.example.chance.learn_gridview.kit.AtmKit;

public class FragTransInfo extends Fragment implements View.OnClickListener {

    private EditText et_to_num;
    private EditText et_to_name;
    private EditText et_to_cash;

    private Button bt_previous;
    private Button bt_next;
    private WebView wv_trans_info;

    public static String num;
    public static String name;
    public static int cash;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trans_info, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        transInfo();   //交易信息提示动画

        et_to_num = (EditText) getActivity().findViewById(R.id.et_to_num);
        et_to_name = (EditText) getActivity().findViewById(R.id.et_to_name);
        et_to_cash = (EditText) getActivity().findViewById(R.id.et_to_cash);

        bt_previous = (Button) getActivity().findViewById(R.id.bt_previous);
        bt_next = (Button) getActivity().findViewById(R.id.bt_next);
        bt_previous.setOnClickListener(this);
        bt_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_previous:
                getActivity().finish();
                break;
            case R.id.bt_next:
                num = et_to_num.getText().toString();
                name = et_to_name.getText().toString();
                if(et_to_cash.getText().toString().equals("")){
                    cash = 0;
                }else {
                    cash = Integer.parseInt(et_to_cash.getText().toString());
                }
                if(num.equals("")|| name.equals("")|| !(cash>0)){
                    Toast.makeText(getActivity(), "请完成转款信息！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!AtmKit.verifyTransInfo(num, name)){
                    Toast.makeText(getActivity(), "收款人信息错误！", Toast.LENGTH_SHORT).show();
                    return;
                }
                confirmDialog();
                break;
            default:
                break;
        }
    }

    private void transInfo() {
        wv_trans_info = (WebView) getActivity().findViewById(R.id.wv_trans_info);

        WebSettings settings = wv_trans_info.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        wv_trans_info.loadUrl("file:///android_asset/trans_info.gif");
    }

    private void confirmDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle("业务信息提醒（转账）");
        String info = "请在转账前确认你所填写的信息是否正确："+
                "\n转账账户："+ num+ "\n转账人姓名："+ name+ "\n转账金额："+ cash;
        dialogBuilder.setMessage(info);

        dialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialoginterface, int which) {
                if(!AtmKit.prepareCash(Welcome.cardnum, cash)){
                    Toast.makeText(getActivity(), "你的账户余额不足！", Toast.LENGTH_SHORT).show();
                    return;
                }
                FragmentTransaction fragTrans = getFragmentManager().beginTransaction();
                fragTrans.replace(R.id.fl_transfer, new FragTransAnima());
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

    private void showDialog(){
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View view = factory.inflate(R.layout.dialog_password, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle("正在进行转账业务，请输入你的支付密码:");
        String info = "请在转账前确认你所填写的信息是否正确："+
                "\n转账账户："+ num+ "\n转账人姓名："+ name+ "\n转账金额："+ cash;
        dialogBuilder.setMessage(info);
        dialogBuilder.setView(view);

        TextView tv_cardNum = (TextView) view.findViewById(R.id.tv_cardNum);
        final EditText et_cardPW = (EditText) view.findViewById(R.id.et_cardPW);

        tv_cardNum.setText(Welcome.cardnum);

        dialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int whichButton){

                if(!AtmKit.verifyInfo(Welcome.cardnum, et_cardPW.getText().toString())) {
                    Toast.makeText(getActivity(), "密码输入错误！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!AtmKit.prepareCash(Welcome.cardnum, cash)){
                    Toast.makeText(getActivity(), "你的账户余额不足！", Toast.LENGTH_SHORT).show();
                    return;
                }
                FragmentTransaction fragTrans = getFragmentManager().beginTransaction();
                fragTrans.replace(R.id.fl_transfer, new FragTransAnima());
                fragTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragTrans.addToBackStack(null);
                fragTrans.commit();
            }
        });
        dialogBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int whichButton){
                dialog.dismiss();
            }
        });
        dialogBuilder.create().show();
    }
}
