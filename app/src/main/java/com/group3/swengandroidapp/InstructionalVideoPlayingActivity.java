package com.group3.swengandroidapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import com.group3.swengandroidapp.XMLRenderer.InstructionalVideo;
import com.group3.swengandroidapp.XMLRenderer.RemoteFileManager;

public class InstructionalVideoPlayingActivity extends AppCompatActivity {

    private InstructionalVideo instructionalVideo;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructional_video_playing);

        videoView  = (VideoView)findViewById(R.id.myVideoview);
        videoView.setMediaController(new MediaController(this));

        Intent receivedIntent = getIntent();

        Log.d("InstructionalVideoPlayingActivity","received video " + receivedIntent.getStringExtra(PythonClient.ID));

        videoView.setVideoURI(Uri.parse(String.format("http://%s:5000/download/instruvid/%s" + ".mpg",
                PythonClient.IP_ADDR,
                receivedIntent.getStringExtra(PythonClient.ID))));
        videoView.requestFocus();
        videoView.start();


    }
}
