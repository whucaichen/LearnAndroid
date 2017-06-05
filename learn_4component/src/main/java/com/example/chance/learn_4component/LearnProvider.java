package com.example.chance.learn_4component;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

public class LearnProvider extends AppCompatActivity {

    private EditText nameET;
    private EditText numberET;
    private Button insertBtn;
    private Button deleteBtn;
    private Button queryBtn;
    private ListView contentView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_content_provider);
        nameET = (EditText) findViewById(R.id.name);
        numberET = (EditText) findViewById(R.id.number);
        insertBtn = (Button) findViewById(R.id.insert);
        deleteBtn = (Button) findViewById(R.id.delete);
        queryBtn = (Button) findViewById(R.id.query);
        // 用于显示数据
        contentView = (ListView) findViewById(R.id.content);
        insertBtn.setOnClickListener(new OperateOnClickListener());
        deleteBtn.setOnClickListener(new OperateOnClickListener());
        queryBtn.setOnClickListener(new OperateOnClickListener());
    }

    class OperateOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String name = nameET.getText().toString();
            String number = numberET.getText().toString();
            Person p = new Person(name, number);
            switch (v.getId()) {
                // 插入数据
                case R.id.insert:
                    insert(p);
                    view();
                    break;
                // 删除数据
                case R.id.delete:
                    delete(name);
                    view();
                    break;
                // 查询数据
                case R.id.query:
                    view();
                    break;
                default:
                    break;
            }
        }
    }

    // 显示数据
    private void view() {
        Cursor c = query("");
        ListAdapter listAdapter = new SimpleCursorAdapter(this, R.layout.item_list,
                c, new String[] {Contacts.People.NAME, Contacts.People.NUMBER },
                new int[] {R.id.item_name, R.id.item_number });
        contentView.setAdapter(listAdapter);
    }

    // 插入联系人
    private void insert(Person p) {
//        // 获得ContentResolver对象
//        ContentResolver cr = getContentResolver();
//        ContentValues values = new ContentValues();
//        values.put(Contacts.People.NAME, p.name);
//        // 表示是否把联系人添加到收藏（加星），1表示加入，0表示不加入，这行代码注释默认是不加入。
//        values.put(Contacts.People.STARRED, 1);
//        Uri uri = Contacts.People.createPersonInMyContactsGroup(cr, values);
//        // 获得联系人People表的Uri
//        Uri url = Uri.withAppendedPath(uri,
//                Contacts.People.Phones.CONTENT_DIRECTORY);
//        values.clear();
//        values.put(Contacts.Phones.TYPE, Contacts.Phones.NUMBER);
//        values.put(Contacts.Phones.NUMBER, p.number);
//        // 插入操作
//        cr.insert(url, values);

        addContact(p.name, p.number);
    }

    public void addContact(String name, String phoneNum) {
        ContentValues values = new ContentValues();
        Uri rawContactUri = getContentResolver().insert(
                ContactsContract.RawContacts.CONTENT_URI, values);
        long rawContactId = ContentUris.parseId(rawContactUri);
        // 向data表插入数据
        if (name != "") {
            values.clear();
            values.put(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, rawContactId);
            values.put(ContactsContract.RawContacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, name);
            getContentResolver().insert(ContactsContract.Data.CONTENT_URI,
                    values);
        }
        // 向data表插入电话号码
        if (phoneNum != "") {
            values.clear();
            values.put(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, rawContactId);
            values.put(ContactsContract.RawContacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNum);
            values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
            getContentResolver().insert(ContactsContract.Data.CONTENT_URI,
                    values);
        }
    }


    // 插入联系人
    private void delete(String name) {
        // 获得ContentResolver对象
        ContentResolver cr = getContentResolver();
        Uri url = Contacts.People.CONTENT_URI;
        // 设置删除条件
        String where = Contacts.People.NAME + "=?";
        String[] selectionArgs = { name };
        cr.delete(url, where, selectionArgs);
    }

    // 查询联系人
    private Cursor query(String name) {
        // 获得ContentResolver对象
        ContentResolver cr = getContentResolver();
        Uri uri = Contacts.People.CONTENT_URI;
        // 查询对象
        String[] projection = { Contacts.People._ID, Contacts.People.NAME, Contacts.People.NUMBER };
        // 设置查询条件，这里我把selection和selectionArgs参数都设为null，表示查询全部数据
        String selection = null;
        String[] selectionArgs = null;
        if (!"".equals(name)) {
            selection = Contacts.People.NAME + "=?";
            selectionArgs = new String[] { name };
        }
        // 设置排序条件
        String sortOrder = Contacts.People._ID;
        Cursor c = cr.query(uri, projection, selection, selectionArgs,
                sortOrder);
        // if (c.moveToFirst()) {
        // for (int i = 0; i < c.getCount(); i++) {
        // c.moveToPosition(i);
        // String name = c.getString(c.getColumnIndexOrThrow(People.NAME));
        // String number = c.getString(c
        // .getColumnIndexOrThrow(People.NUMBER));
        // }
        // }
        return c;
    }

    private class Person{
        String name;
        String number;

        public Person(String name, String number) {
            this.name = name;
            this.number = number;
        }
    }
}
