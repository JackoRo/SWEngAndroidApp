package com.group3.swengandroidapp;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import static android.content.Context.VIBRATOR_SERVICE;

public class AudioPlayer{

    private static final int NUMBER_OF_TOUCH_SOUNDS = 3;

    private ArrayList<MediaPlayer> players;
    private static AudioPlayer instance = new AudioPlayer();
    private static Boolean muted = false;
    private static Boolean vibrationOff = false;

    // Used to count through files used for the same effect (see touchSound())
    private int fileCounter = 0;

    private AudioPlayer(){
        players = new ArrayList<>(0);
    }

    public static void touchSound(){
        Random r = new Random();
        int temp = 0;

        // Never repeats a number
        while((temp = r.nextInt(NUMBER_OF_TOUCH_SOUNDS))== instance.fileCounter);
        instance.fileCounter = temp;

        switch (temp) {
            case 0:
                instance.playResource(R.raw.tap1);
                break;
            case 1:
                instance.playResource(R.raw.tap2);
                break;
            case 2:
                instance.playResource(R.raw.tap3);
                break;
            default:
                instance.playResource(R.raw.tap1);
                break;
        }


    }


    public static void favouritesSound(){
        instance.playResource(R.raw.favourite_click);
    }

    public static void baguette(){
        instance.playResource(R.raw.foux_du_fafa);
    }

    public static void stop(){
        instance.stopAll();
    }

    public static Boolean isMuted(){
        return instance.muted;
    }

    public static Boolean isVibrationOff(){
        return instance.vibrationOff;
    }

    public static void mute(Boolean mute){
        instance.muted = mute;
    }

    public static void vibrationOff(boolean vibrationOff){
        instance.vibrationOff = vibrationOff;
    }

    public static void play(int resourceID){
        instance.playResource(resourceID);
    }

    private void playResource(int resourceId){
        if(!instance.muted){

            MediaPlayer p = MediaPlayer.create(MainActivity.getAppContext(), resourceId);
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

    private void stopAll(){
        for(MediaPlayer p : players){
            p.stop();
        }
    }
}
