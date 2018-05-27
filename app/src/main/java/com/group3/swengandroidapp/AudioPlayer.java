package com.group3.swengandroidapp;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.provider.MediaStore;

import com.bumptech.glide.load.resource.file.FileResource;

import java.util.ArrayList;

/**
 * Created by Marco on 25/05/2018.
 */

public class AudioPlayer {
    private ArrayList<MediaPlayer> players;
    private static AudioPlayer instance = new AudioPlayer();

    private AudioPlayer(){
        players = new ArrayList<>(0);
    }

    public static AudioPlayer getInstance(){
        return instance;
    }

    public void playResource(Context context, int resourceId){
        MediaPlayer p = MediaPlayer.create(context, resourceId);
        p.setAudioStreamType(AudioManager.STREAM_MUSIC);

        p.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.stop();
                mediaPlayer.release();
                players.remove(mediaPlayer);
            }
        });
        players.add(p);
        p.start();
    }
}
