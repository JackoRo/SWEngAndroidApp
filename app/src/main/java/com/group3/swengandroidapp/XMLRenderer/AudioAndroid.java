package com.group3.swengandroidapp.XMLRenderer;

import android.app.Activity;
import android.media.MediaPlayer;
import android.util.Log;

import com.group3.swengandroidapp.AudioPlayer;
import com.group3.swengandroidapp.PythonClient;

import java.net.URL;

/**
 * Created by Jack on 26/05/2018.
 */

public class AudioAndroid extends Audio {

    public AudioAndroid(XmlElement parent) {
        super(parent);
    }

    @Override
    public void draw(Activity activity) {
        AudioPlayer.stop();
        MediaPlayer player = new MediaPlayer();
        String urlString;

        try {

            try {
                new URL(getPath());
                urlString = getPath();
            } catch (Exception e) {
                urlString = "http://"+ PythonClient.IP_ADDR + ":5000/download/presentation/" + getInheritableProperty("_ID") + "/" + getPath();
            }

            player.setDataSource(urlString);
            player.prepare();
            player.start();

        } catch (Exception e) {
            Log.d("AudioAndroid","Exception");
        }

    }
}
