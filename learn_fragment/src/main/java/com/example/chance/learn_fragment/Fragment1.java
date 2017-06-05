package com.example.chance.learn_fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Chance on 2016/6/23.
 */
public class Fragment1 extends Fragment{

    public final static String TAG = "Log_Fragment_Life";

    private EditText et_content1;
    private EditText et_content2;
    private EditText et_message1;
    private Button bt_send1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");
        return inflater.inflate(R.layout.fragment1, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "onActivityCreated");

        et_content1 = (EditText) getActivity().findViewById(R.id.et_content1);
        et_content2 = (EditText) getActivity().findViewById(R.id.et_content2);
        et_message1 = (EditText) getActivity().findViewById(R.id.et_message1);
        bt_send1 = (Button) getActivity().findViewById(R.id.bt_send1);

        bt_send1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = et_message1.getText().toString();
                if (!message.equals("")){
                    et_content1.setText(et_content1.getText()+"已发送："+message+"\n");
                    et_content2.setText(et_content2.getText()+"已接收："+message+"\n");
                    et_message1.setText("");
                }
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.e(TAG, "onAttach");
    }

//    public void myonc

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(TAG, "onDetach");
    }
}
