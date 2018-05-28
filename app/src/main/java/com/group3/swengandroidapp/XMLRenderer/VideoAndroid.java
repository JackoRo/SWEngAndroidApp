package com.group3.swengandroidapp.XMLRenderer;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.MediaController;
import android.widget.VideoView;

import com.group3.swengandroidapp.AudioPlayer;
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
            AudioPlayer.stop();
            LinearLayout layout = ((Slide) parent).getLayout();
            VideoView video = new VideoView(activity);
            String urlString;
            MediaController mc = new MediaController(activity);

            try{
                new URL(getPath());
                urlString = getPath();
            } catch (Exception e){
                urlString = "http://"+ PythonClient.IP_ADDR + ":5000/download/presentation/" + getInheritableProperty("_ID") + "/" + getPath();
            }

            video.setVideoPath(urlString);


            if (getX1() != null && getX2() != null && getY1() != null && getY2() != null) {
                int x1 = Integer.valueOf(getX1());
                int x2 = Integer.valueOf(getX2());
                int y1 = Integer.valueOf(getY1());
                int y2 = Integer.valueOf(getY2());


                LayoutParams layoutParams=new LayoutParams(x2, y2);
                layoutParams.setMargins(x1, y1, x2, y2);
                video.setLayoutParams(layoutParams);
            }
            else {
                Log.d("Draw", "No parameters set. Loading default image size");
            }


            video.start();

            video.setOnPreparedListener(new OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setOnVideoSizeChangedListener(new OnVideoSizeChangedListener() {
                        @Override
                        public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                            video.setMediaController(mc);
                        }
                    });
                }
            });

            video.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Presentation.listenerEnable = false;
                    Log.d("listenerEnable", "FALSE");

                    if (mc.isShowing()) {
                        mc.hide();
                    }
                    else {
                        mc.show();
                    }

                    return true;
                }
            });

            layout.addView(video);

        }
    }
}
