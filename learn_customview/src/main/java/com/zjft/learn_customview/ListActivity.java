package com.zjft.learn_customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.zjft.learn_customview.view.MyListView;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private MyListView myListView;

    private MyAdapter adapter;

    private List<String> contentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        contentList = new ArrayList<String>();
        for(int i=0; i<10; i++){
            contentList.add("Content Item "+ i);
        }
        adapter = new MyAdapter(this, 0, contentList);

        myListView = (MyListView) findViewById(R.id.my_list_view);
        myListView.setAdapter(adapter);
        myListView.setOnDeleteListener(new MyListView.OnDeleteListener() {
            @Override
            public void onDelete(int index) {
                contentList.remove(index);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
