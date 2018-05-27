package com.group3.swengandroidapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

public class InstructionalVideoPlayingActivity {

    protected void onCreate(Bundle savedInstanceState) {
        Intent receivedIntent = getIntent();
        String videoID = receivedIntent.getStringExtra("key");

        Uri uri = Uri.parse("../../python/instructionalVideos/test_video.MP4"); //Declare your url here.

        private VideoView mVideoView  = (VideoView)findViewById(R.id.myVideoview)
        mVideoView.setMediaController(new MediaController(this));
        mVideoView.setVideoURI(uri);
        mVideoView.requestFocus();
        mVideoView.start();

    }
}
