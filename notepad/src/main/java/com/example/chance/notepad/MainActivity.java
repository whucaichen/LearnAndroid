package com.example.chance.notepad;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public ListView listView;
    public static SimpleCursorAdapter simpleCursorAdapter;
    public static SQLiteUtils sqliteUtils;
    public List<String> list;
    public static Cursor cur;
    public String newName;
    public ProgressBar progressBar;
    public CheckBox checkBox;

    private Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case 1:
                    Thread thread = new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            try {
                                progressBar.setVisibility(View.VISIBLE);
                                sleep(3000);
                                progressBar.setVisibility(View.INVISIBLE);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    thread.start();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NotepadEditor.class);
                Bundle bundle = new Bundle();
                bundle.putString("Mode", "New");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        listView = (ListView) findViewById(R.id.listView);
        this.registerForContextMenu(listView);

        sqliteUtils = new SQLiteUtils(this);
        sqliteUtils.open();
        //获取数据库note的cursor
        cur = sqliteUtils.fetchAllData();
        if (cur != null && cur.getCount() >= 0) {
            //ListAdapter是ListView和后台数据的桥梁
            simpleCursorAdapter = new SimpleCursorAdapter(this,
                    //表示每一行包含两个数据项
                    R.layout.item_note,
                    //数据库的cursor对象
                    cur,
                    //从数据库的table_title,table_text和table_date两列中取得数据
                    new String[]{SQLiteUtils.KEY_TITLE, SQLiteUtils.KEY_TEXT, SQLiteUtils.KEY_DATE},
                    //与title和date对应的Views
                    new int[]{R.id.tv_title, R.id.tv_content, R.id.tv_date});
        }

        listView.setAdapter(simpleCursorAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), NotepadEditor.class);
                Bundle bundle = new Bundle();
                bundle.putString("Mode", "Open");
                bundle.putLong("ID", id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //设置上下文菜单内容
        menu.setHeaderTitle("文件菜单");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        long id = listView.getAdapter().getItemId(menuInfo.position);

        switch (item.getItemId()) {
            case R.id.action_update:
                Log.i("Main", "**************重命名文件************");
                renameDialog(id);
                break;
            case R.id.action_delete:
                Log.i("Main", "***************删除文件*****************");
                sqliteUtils.deleteData(id);
                cur.requery();
                simpleCursorAdapter.notifyDataSetChanged();
                break;
            case R.id.action_multi:
                Log.i("Main", "***************多选操作*****************");
                for(int i=0; i<listView.getAdapter().getCount(); i++){
                    checkBox = (CheckBox) listView.getChildAt(i).findViewById(R.id.cb_item);
                    checkBox.setVisibility(View.VISIBLE);
                }
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void renameDialog(final long rowId) {
        //获取自定义AlertDialog布局文件的view
        LinearLayout change_name = (LinearLayout) getLayoutInflater()
                .inflate(R.layout.my_dialog, null);
        TextView tv_name_dialog = (TextView) change_name.findViewById(R.id.tv_name_dialog);
        //由于EditText要在内部类中对其进行操作，所以要加上final
        final EditText et_name_dialog = (EditText) change_name.findViewById(R.id.et_name_dialog);

        //设置AlertDialog中TextView和EditText显示Activity中TextView的内容
        String oldName = sqliteUtils.queryData(rowId);
        tv_name_dialog.setText(oldName);
        et_name_dialog.setText(oldName);
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("事件重命名")
                .setView(change_name)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newName = et_name_dialog.getText().toString();
                        sqliteUtils.renameFile(rowId, newName);
                        cur.requery();
                        simpleCursorAdapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "重命名成功！", Toast.LENGTH_SHORT).show();
                    }
                })
                //由于“取消”的button我们没有设置点击效果，直接设为null就可以了
                .setNegativeButton("取消", null)
                .create()
                .show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (checkBox.isChecked()) {
            for(int i=0; i<listView.getAdapter().getCount(); i++){
                checkBox = (CheckBox) listView.getChildAt(i).findViewById(R.id.cb_item);
                checkBox.setChecked(false);
                checkBox.setVisibility(View.GONE);
            }
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Toast.makeText(this, "你点击了设置", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_sync) {
            Toast.makeText(this, "正在同步数据中……", Toast.LENGTH_LONG).show();
            Message message = new Message();
            message.what = 1;
//            message.setData();
            handler.sendMessage(message);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_camera) {
            // Handle the camera action
            Toast.makeText(this, "你点击了nav_camera", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_gallery) {
            Toast.makeText(this, "你点击了nav_gallery", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_slideshow) {
            Toast.makeText(this, "你点击了nav_slideshow", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_manage) {
            Toast.makeText(this, "你点击了nav_manage", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_share) {
            Toast.makeText(this, "你点击了nav_share", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send) {
            Toast.makeText(this, "你点击了nav_send", Toast.LENGTH_SHORT).show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
