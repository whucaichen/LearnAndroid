package com.example.chance.learn_gridview.business.inquire;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chance.learn_gridview.MainBusiness;
import com.example.chance.learn_gridview.R;
import com.example.chance.learn_gridview.Welcome;
import com.example.chance.learn_gridview.kit.AtmKit;

public class FragInfoBase extends Fragment {

    private TextView tv_info;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info_base, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tv_info = (TextView) getActivity().findViewById(R.id.tv_info);
        MainBusiness.handler.post(new Runnable() {
            @Override
            public void run() {
                String info = AtmKit.getInfo(Welcome.cardnum);  //拿到基本账户信息
                tv_info.setText(info);
            }
        });
    }
}
