package com.example.chance.learn_gridview.business.inquire;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chance.learn_gridview.MainBusiness;
import com.example.chance.learn_gridview.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragInfoDetail extends Fragment {

    final static String TAG = "LOG_FragInfoDetail";

    private EditText et_date;
    private Button bt_query;
    private TextView tv_info_detail;
    private ListView lv_info;

    private Calendar calendar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info_detail, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        calendar = Calendar.getInstance();

        tv_info_detail = (TextView) getActivity().findViewById(R.id.tv_info_detail);
        et_date = (EditText) getActivity().findViewById(R.id.et_date);
        bt_query = (Button) getActivity().findViewById(R.id.bt_query);
        et_date.setInputType(InputType.TYPE_NULL);  // 禁止弹出软键盘

        lv_info = (ListView) getActivity().findViewById(R.id.lv_info);

        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(), dateSetListener, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        bt_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String date = et_date.getText().toString();
                if(date.equals("")){
                    Toast.makeText(getActivity(), "日期不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                MainBusiness.handler.post(new Runnable() {
                    @Override
                    public void run() {
//                        String infoDetail = AtmKit.getInfoDetail(Welcome.cardnum, date);  //拿到基本账户信息
//                        tv_info_detail.setText(infoDetail);

                        SimpleAdapter adapter = new SimpleAdapter(getActivity(), getData(),
                                R.layout.item_list, new String[] {"date", "info" },
                                new int[] {R.id.tv_date, R.id.tv_info });
                        lv_info.setAdapter(adapter);    //hide和show重叠问题
                    }
                });
            }
        });
    }

    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // 每次保存设置的日期
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String str = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
            Log.e(TAG, "set is "+ str);

            if (et_date.isFocused()) {
                et_date.setText(str);
            }
        }
    };

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();

        for(int i=0; i<10; i++){
            map.put("date", "date-"+ i);
            map.put("info", "cash_in："+ i+ "\ncash_out："+ i);
            list.add(map);
        }
        return list;
    }
}
