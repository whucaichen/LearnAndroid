package com.example.learn_videoview;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        Log.d("TAG", "Max memory is " + maxMemory + "KB");

        mVideoView = (VideoView) findViewById(R.id.vv_video);
//        mVideoView.setVideoURI(Uri.parse("android.resource://com.example.learn_videoview.readCard.gif"));
//        mVideoView.setVideoURI(Uri.parse("file:///android_asset/earth.mp4"));
        mVideoView.setVideoURI(Uri.parse("http://10.34.10.233/earth.mp4"));
        // videoView.setVideoPath();
        // 设置播放器的控制条
        mVideoView.setMediaController(new MediaController(this));
        // 开始播放视频
        mVideoView.start();
    }
}
