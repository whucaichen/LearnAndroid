package com.example.chance.learn_native;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final static String TAG = "TAG_CALL_NATIVE";
    final static String IMAGE_FILE = "/mnt/sdcard/test.jpg";

    private Button bt_dial;
    private Button bt_contacts;
    private Button bt_pick_people;
    private Button bt_sms;
    private Button bt_search;
    private Button bt_email;
    private Button bt_camera;
    private Button bt_record;
    private Button bt_application;
    private Button bt_handle_people;
    private Button bt_pick_img;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_dial = (Button) findViewById(R.id.bt_dial);
        bt_contacts = (Button) findViewById(R.id.bt_contacts);
        bt_pick_people = (Button) findViewById(R.id.bt_pick_people);
        bt_sms = (Button) findViewById(R.id.bt_sms);
        bt_search = (Button) findViewById(R.id.bt_search);
        bt_email = (Button) findViewById(R.id.bt_email);
        bt_camera = (Button) findViewById(R.id.bt_camera);
        bt_record = (Button) findViewById(R.id.bt_record);
        bt_application = (Button) findViewById(R.id.bt_application);
        bt_handle_people = (Button) findViewById(R.id.bt_handle_people);
        bt_pick_img = (Button) findViewById(R.id.bt_pick_img);
        imageView = (ImageView) findViewById(R.id.imageView);

        bt_dial.setOnClickListener(this);
        bt_contacts.setOnClickListener(this);
        bt_pick_people.setOnClickListener(this);
        bt_sms.setOnClickListener(this);
        bt_search.setOnClickListener(this);
        bt_email.setOnClickListener(this);
        bt_camera.setOnClickListener(this);
        bt_record.setOnClickListener(this);
        bt_application.setOnClickListener(this);
        bt_handle_people.setOnClickListener(this);
        bt_pick_img.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        Uri uri;

        switch (v.getId()) {
            case R.id.bt_dial:
                Log.e(TAG, "bt_dial");
//                intent =new Intent();
//                //intent.setAction("android.intent.action.CALL_BUTTON");
//                intent.setAction(Intent.ACTION_CALL_BUTTON);

                uri = Uri.parse("tel:10086");
                intent = new Intent(Intent.ACTION_DIAL, uri);

//                //此方式不同的Android版本可能不同
//                Intent intent= new Intent("android.intent.action.DIAL");
//                intent.setClassName("com.android.contacts", "com.android.contacts.DialtactsActivity");

                startActivityForResult(intent, 0);
                break;
            case R.id.bt_contacts:
                Log.e(TAG, "bt_contacts");
                intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Contacts.People.CONTENT_URI);

//                intent= new Intent("com.android.contacts.action.LIST_STREQUENT");
//                intent.setClassName("com.android.contacts","com.android.contacts.DialtactsActivity");

                startActivityForResult(intent, 1);
                break;
            case R.id.bt_pick_people:
                Log.e(TAG, "bt_pick_people");
                intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setData(Contacts.People.CONTENT_URI);

                startActivityForResult(intent, 2);
                break;
            case R.id.bt_sms:
                Log.e(TAG, "bt_sms");
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setType("vnd.android-dir/mms-sms");
                //intent.setData(Uri.parse("content://mms-sms/conversations/"));//此为号码

//                intent = new Intent("android.intent.action.CONVERSATION");

                startActivityForResult(intent, 3);
                break;
            case R.id.bt_search:
                Log.e(TAG, "bt_search");
                intent = new Intent();
                intent.setAction(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, "乌尔奇奥拉");

                startActivityForResult(intent, 4);
                break;
            case R.id.bt_email:
                Log.e(TAG, "bt_email");
//                //No Activity found to handle Intent
//                uri = Uri.parse("mailto:xxx@abc.com");
//                intent = new Intent(Intent.ACTION_SENDTO, uri);
//                startActivity(intent);

                intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, "me@abc.com");
                intent.putExtra(Intent.EXTRA_TEXT, "The email body text");
                intent.setType("text/plain");

                startActivityForResult(Intent.createChooser(intent, "Choose Email Client"), 5);
                break;
            case R.id.bt_camera:
                Log.e(TAG, "bt_camera");
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                uri = Uri.fromFile(new File(IMAGE_FILE));// /mnt/sdcard/test.jpg 是照片存储目录
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                startActivityForResult(intent, 6);
                break;
            case R.id.bt_record:
                Log.e(TAG, "bt_record");
                intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);

                startActivity(intent);
                break;
            case R.id.bt_application:
                Log.e(TAG, "bt_application");
//                //No Activity found to handle Intent
//                uri = Uri.parse("market://details?id=<packagename>");
//                intent = new Intent(Intent.ACTION_VIEW, uri);
//
//                startActivity(intent);
                break;
            case R.id.bt_handle_people:
                Log.e(TAG, "bt_handle_people");
                intent = new Intent();
                intent.setClassName("com.example.chance.learn_4component",
                        "com.example.chance.learn_4component.LearnContentProvider");

                startActivityForResult(intent, 7);
                break;
            case R.id.bt_pick_img:
                Log.e(TAG, "bt_pick_img");
                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");

                startActivityForResult(intent, 8);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case 0:
                Log.e(TAG, data.getDataString());
                break;
            case 6:
                if (resultCode == RESULT_OK) {
                    Log.e(TAG, "照相完成");
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2;
                    Bitmap bitmap = BitmapFactory.decodeFile(IMAGE_FILE, options);
                    imageView.setImageBitmap(bitmap);
                }
                break;
            default:
                break;
        }
    }
}
