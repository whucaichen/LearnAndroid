package com.example.chance.notepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chance on 2016/6/7.
 */

public class SQLiteUtils {
    //用于打印log
    private static final String TAG = "SQLiteUtils";
    //表中一条事件的id
    public static final String KEY_ID = "_id";
    //表中一条事件的标题
    public static final String KEY_TITLE = "_title";
    //表中一条事件的创建日期和时间等
    public static final String KEY_DATE = "_date";
    //表中一条事件的内容
    public static final String KEY_TEXT = "_text";
    //数据库的名称为Notepad.db
    private static final String DB_NAME = "Notepad.db";
    //数据库表名
    private static final String DB_TABLE = "Notepad";
    //数据库的版本
    private static final int DB_VERSIOIN = 1;
    //本地context对象
    private Context mContext = null;
    //创建一个事件表的sql语句
    private static final String DB_CREATE = "CREATE TABLE " +
            DB_TABLE + " (" +
            KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_TITLE + " TEXT," +
            KEY_DATE + " DATE," +
            KEY_TEXT + " TEXT)";
    //执行open()打开数据库时，保存返回的数据库对象
    private SQLiteDatabase mSQLiteDatabase = null;
    //由SQLiteOpenHelper继承过来
    private DatabaseHelper mDatabaseHelper = null;

    //构建DatabaseHelper类
    private static class DatabaseHelper extends SQLiteOpenHelper {
        /*构造函数-创建一个数据库*/
        public DatabaseHelper(Context context) {
            /*当调用getWritableDatabase()或getReadableDatabase()方法时则创建一个数据库*/
            super(context, DB_NAME, null, DB_VERSIOIN);
        }

        //创建一个表
        @Override
        public void onCreate(SQLiteDatabase db) {
            // 数据库没有表时创建一个
            db.execSQL(DB_CREATE);
        }

        //升级数据库
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS notes");
            onCreate(db);
        }
    }

    //构造函数-取得context
    public SQLiteUtils(Context context) {
        mContext = context;
    }

    //打开数据库，返回数据库对象
    public void open() throws SQLException {
        mDatabaseHelper = new DatabaseHelper(mContext);
        mSQLiteDatabase = mDatabaseHelper.getWritableDatabase();
    }

    //关闭数据库
    public void close() {
        mDatabaseHelper.close();
    }

    //插入一条数据
    public long insertData(String _title, String _date, String _text) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TITLE, _title);
        initialValues.put(KEY_DATE, _date);
        initialValues.put(KEY_TEXT, _text);
        return mSQLiteDatabase.insert(DB_TABLE, KEY_ID, initialValues);
    }

    //删除一条数据
    public boolean deleteData(long rowId) {
        return mSQLiteDatabase.delete(DB_TABLE, KEY_ID + "=" + rowId, null) > 0;
    }

    //通过cursor查询所有数据
    public Cursor fetchAllData() {
        return mSQLiteDatabase.query(DB_TABLE,
                new String[]{KEY_ID, KEY_TITLE, KEY_DATE, KEY_TEXT},
                null, null, null, null, null);
    }

    //查询指定 事件标题和时间的数据（用在主界面）
    public Cursor fetchAllTitleAndDate(long rowId) throws SQLException {
        Cursor mCursor = mSQLiteDatabase.query(true, DB_TABLE,
                new String[]{KEY_ID, KEY_TITLE, KEY_DATE, KEY_TEXT},
                KEY_ID + "=" + rowId,
                null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //查询指定事件 标题和内容的数据(用在事件查看界面)
    public Cursor fetchAllTitleAndText(long rowId) throws SQLException {
        Cursor mCursor = mSQLiteDatabase.query(true, DB_TABLE,
                new String[]{KEY_ID, KEY_TITLE, KEY_DATE, KEY_TEXT},
                KEY_ID + "=" + rowId,
                null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //更新一条数据
    public boolean updateData(long rowId, String _title, String _date, String _text) {
        ContentValues args = new ContentValues();
        args.put(KEY_TITLE, _title);
        args.put(KEY_DATE, _date);
        args.put(KEY_TEXT, _text);
        return mSQLiteDatabase.update(DB_TABLE, args, KEY_ID + "=" + rowId, null) > 0;
    }

    //重命名
    public boolean renameFile(long rowId, String _title) {
        ContentValues args = new ContentValues();
        args.put(KEY_TITLE, _title);
        return mSQLiteDatabase.update(DB_TABLE, args, KEY_ID + "=" + rowId, null) > 0;
    }

    //查询单挑数据
    public String queryData(long rowId) {
        Cursor cursor = mSQLiteDatabase.query(true, DB_TABLE,
                new String[]{KEY_ID, KEY_TITLE, KEY_DATE, KEY_TEXT},
                KEY_ID + "=" + rowId,
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor.getString(cursor.getColumnIndex("_title"));
    }

    //给显示界面用来更新的指定数据（事件标题和事件内容）
    public List<String> UpdataText(long rowId){
        List<String> list = new ArrayList<String>();
        //获取数据库的cursor
        Cursor cur = fetchAllTitleAndText(rowId);
        if(cur != null && cur.getCount() >= 0 ){
            String title = cur.getString(cur.getColumnIndex("_title"));
            String text = cur.getString(cur.getColumnIndex("_text"));
            list.add(title);
            list.add(text);
            //取得数据成功返回List对象
            return list;
        }
        //取得数据失败返回空值
        return null;
    }
}
