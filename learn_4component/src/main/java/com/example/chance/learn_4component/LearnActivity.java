package com.example.chance.learn_4component;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LearnActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText et_name;
    private EditText et_password;
    private Button bt_cancel;
    private Button bt_login;

    private Intent intent;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        et_name = (EditText) findViewById(R.id.et_name);
        et_password = (EditText) findViewById(R.id.et_password);
        bt_cancel = (Button) findViewById(R.id.bt_cancel);
        bt_login = (Button) findViewById(R.id.bt_login);

        bt_cancel.setOnClickListener(this);
        bt_login.setOnClickListener(this);

        intent = getIntent();
        bundle = intent.getExtras();
        String data = bundle.getString("data");
        et_name.setText(data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_cancel:
                Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
                setResult(0, intent);
                finish();
                break;
            case R.id.bt_login:
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
//                intent = new Intent();
                bundle.clear();
                bundle.putString("name", et_name.getText().toString());
                bundle.putString("password", et_password.getText().toString());
                intent.putExtras(bundle);
                setResult(1, intent);
                finish();
                break;
            default:
                break;
        }
    }


}
