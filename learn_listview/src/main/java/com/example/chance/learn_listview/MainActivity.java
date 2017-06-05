package com.example.chance.learn_listview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        checkBox = (CheckBox) findViewById(R.id.checkbox);
        initListView();
    }

    private void initListView() {

        SimpleAdapter adapter = new SimpleAdapter(this, getData(),
                R.layout.item_list, new String[] {"pic", "title", "info" },
                new int[] {R.id.iv_pic, R.id.tv_headline, R.id.tv_content });
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String search = (String) getData().get(position).get("title");

                Intent intent = new Intent(getApplicationContext(), WebInfo.class);
                intent.putExtra("search", search);
                startActivity(intent);
            }
        });
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pic", R.drawable.sishen1);
        map.put("title", "乌尔奇奥拉");
        map.put("info", "十刃中唯一可以二段归刃的人，被牛头 虚化的一护重伤，沙化消逝，确定死亡");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("pic", R.drawable.sishen2);
        map.put("title", "京乐春水");
        map.put("info", "现任总队长，名为京乐次郎总藏佐春水");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("pic", R.drawable.sishen3);
        map.put("title", "浦原喜助");
        map.put("info", "110年前第三席，智力最高的 死神，超队长级 死神");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("pic", R.drawable.sishen4);
        map.put("title", "市丸银");
        map.put("info", "离任队长，卧底蓝染身边，已故");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("pic", R.drawable.sishen5);
        map.put("title", "黑崎一护");
        map.put("info", "本作的主角， 代理死神， 尸魂界的英雄， 灵压最高，合 死神、 灭却师、 虚、 完现术四者力量于一身的完美存在");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("pic", R.drawable.sishen6);
        map.put("title", "日番谷冬狮郎");
        map.put("info", "现任队长，前任第三席");
        list.add(map);

        return list;
    }
}
