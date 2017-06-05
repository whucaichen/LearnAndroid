package io.whucaichen.permission;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        permit();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                permit();
            }
        }, 5000);
        test();
    }


    @SuppressLint("NewApi")
    @SuppressWarnings("CheckResult")
    private void permit() {
//        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            Log.e(TAG, "00000000000000000000000000000000000000000000");
//            requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
//        } else {
//            Log.e(TAG, "1111111111111111111111111111111111111111111111");
//            takePhoto();
//        }

//        Log.e(TAG, "isCameraCanUse: " + isCameraCanUse());
        Log.e(TAG, String.valueOf(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED));
//        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//        if (!isCameraCanUse()) {
//            Log.e(TAG, "00000000000000000000000000000000000000000000");
//            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
//        } else {
//            Log.e(TAG, "1111111111111111111111111111111111111111111111");
//            takePhoto();
//        }

//        try {
//            pm = getPackageManager();
//            if (pm.checkPermission(Manifest.permission.CAMERA, "io.whucaichen.permission") != PackageManager.PERMISSION_GRANTED) {
//                Log.e(TAG, "00000000000000000000000000000000000000000000");
//                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
//            } else {
//                Log.e(TAG, "1111111111111111111111111111111111111111111111");
//                takePhoto();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        if (isCameraCanUse())
            Log.e(TAG, "isCameraCanUse1");
        else
            getAppDetailSettingIntent(this);
    }

    private AlertDialog dialog = null;

    private void getAppDetailSettingIntent(Context context) {
        dialog = new AlertDialog.Builder(MainActivity.this)
                .setMessage("hahaha")
                .setPositiveButton("xixixi", null)
                .create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent localIntent = new Intent();
                localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                localIntent.setData(Uri.fromParts("package", getPackageName(), null));
//                        startActivity(localIntent);
                startActivityForResult(localIntent, 666);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 666 && isCameraCanUse() && dialog != null) {
            dialog.dismiss();
            Log.e(TAG, "isCameraCanUse2");
        }
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = Uri.fromFile(new File("chance.jpg"));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, 2);
    }

    //    @SuppressLint("NewApi")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhoto();
            } else {
                Toast.makeText(MainActivity.this, "应用需要摄像头权限", Toast.LENGTH_SHORT).show();
//                requestPermissions(new String[]{android.Manifest.permission.CAMERA}, 1);
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
            }
        }
    }

    public static boolean isCameraCanUse() {
        boolean canUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            Camera.Parameters mParameters = mCamera.getParameters();
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            canUse = false;
        }
        if (mCamera != null) {
            mCamera.release();
        }
        return canUse;
    }

    private void test() {
//        String uuid = String.valueOf(UUID.randomUUID());
//        String LOGIN_ID = uuid.substring(0, 4);
//        Log.e(TAG, LOGIN_ID);

//        String[] a = getResources().getStringArray(R.array.haha);
//        Log.e(TAG, a.length + "###");
    }
}
