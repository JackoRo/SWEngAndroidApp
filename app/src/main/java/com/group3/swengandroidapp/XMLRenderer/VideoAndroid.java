package com.group3.swengandroidapp.XMLRenderer;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import com.group3.swengandroidapp.PythonClient;

import java.net.URL;

/**
 * Created by Jack on 25/05/2018.
 */

public class VideoAndroid extends Video {

    public VideoAndroid(XmlElement parent) {
        super(parent);
    }

    @Override
    public void draw(Activity activity) {

        if (parent instanceof Slide) {
            LinearLayout layout = ((Slide) parent).getLayout();
            VideoView video = new VideoView(activity);
            String urlString;

            try{
                new URL(getPath());
                urlString = getPath();
            } catch (Exception e){
                urlString = "http://"+ PythonClient.IP_ADDR + ":5000/download/presentation/" + getInheritableProperty("_ID") + "/" + getPath();
            }

            video.setVideoPath(urlString);
            video.start();

            video.setOnPreparedListener(new OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setOnVideoSizeChangedListener(new OnVideoSizeChangedListener() {
                        @Override
                        public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                            MediaController mc = new MediaController(activity);
                            video.setMediaController(mc);

                        }
                    });
                }
            });

            layout.addView(video);


        }
    }
}
