package com.example.chance.notepad;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NotepadEditor extends AppCompatActivity {

    public static EditText et_title;
    public static EditText et_content;
    public Button bt_done;

    public Bundle bundle;
    public String mode;
    public static long getID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_editor);

        et_title = (EditText) findViewById(R.id.et_headline);
        et_content = (EditText) findViewById(R.id.et_content);
        bt_done = (Button) findViewById(R.id.bt_done);

        bundle = getIntent().getExtras();
        mode = bundle.getString("Mode");
        getID = bundle.getLong("ID");
        if (getID != 0) gainData(getID);

        bt_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_title.getText() != null) {
                    //存入数据表，更新ListView
                    if (mode.equals("New")) {
                        MainActivity.sqliteUtils.insertData(et_title.getText().toString(), getDate(), et_content.getText().toString());
                    } else {
                        MainActivity.sqliteUtils.updateData(getID, et_title.getText().toString(), getDate(), et_content.getText().toString());
                    }

                    MainActivity.cur.requery();
                    MainActivity.simpleCursorAdapter.notifyDataSetChanged();
                    finish();
                }
            }
        });

    }

    //根据得到的ID增加数据
    public void gainData(long id) {
        List<String> list = new ArrayList<String>();
        list = MainActivity.sqliteUtils.UpdataText(id);
//        list = sqliteUtils.UpdataText(id);
        et_title.setText(list.get(0));
        et_content.setText(list.get(1));
    }

    //得到编辑框的数据和时间
    public static String getDate() {
        String date = null;
        String title = et_title.getText().toString();
        String text = et_content.getText().toString();
        if (title.equals("") || text.equals("")) {
            Log.i("Main", "***********不能保存空值**********");
        } else {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR); //获取当前年份
            int mMonth = c.get(Calendar.MONTH) + 1;//获取当前月份
            int mDay = c.get(Calendar.DAY_OF_MONTH) + 1;//获取当前月份的日期号码
            int mHour = c.get(Calendar.HOUR);//获取当前的小时数
            int mMinute = c.get(Calendar.MINUTE);//获取当前的分钟数
            date = "Date:" +
                    String.valueOf(mYear) + "-" +
                    String.valueOf(mMonth) + "-" +
                    String.valueOf(mDay) +
                    " Time:" +
                    String.valueOf(mHour) + ":" +
                    String.valueOf(mMinute);
            Log.e("date", date);
        }
        return date;
    }
}
