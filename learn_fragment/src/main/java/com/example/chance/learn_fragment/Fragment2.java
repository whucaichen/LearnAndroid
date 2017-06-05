package com.example.chance.learn_fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Chance on 2016/6/23.
 */
public class Fragment2 extends Fragment {

    private EditText et_content2;
    private EditText et_content1;
    private EditText et_message2;
    private Button bt_send2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment2, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        et_content1 = (EditText) getActivity().findViewById(R.id.et_content1);
        et_content2 = (EditText) getActivity().findViewById(R.id.et_content2);
        et_message2 = (EditText) getActivity().findViewById(R.id.et_message2);
        bt_send2 = (Button) getActivity().findViewById(R.id.bt_send2);

        bt_send2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = et_message2.getText().toString();
                if (!message.equals("")){
                    et_content2.setText(et_content2.getText()+"已发送："+message+"\n");
                    et_content1.setText(et_content1.getText()+"已接收："+message+"\n");
                    et_message2.setText("");
                }
            }
        });
    }
}
