package com.group3.swengandroidapp.XMLRenderer;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

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
            //MediaController mc;

            video.setVideoPath("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");
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
