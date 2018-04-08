package com.group3.swengandroidapp;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.group3.swengandroidapp.XMLRenderer.RemoteFileManager;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Made to give the PythonClient Time to download all its' recipes
 * Created by Marco on 04/04/2018.
 */


public class LoadingActivity extends MainActivity {

    public static boolean progress = false;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_screen);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                progress = true;
            }
        };
        timer.schedule(task, 1000);

    }

    @Override
    protected void onStart(){
        super.onStart();
        try{
            while(!progress){
                TimeUnit.MILLISECONDS.sleep(1);
            };
        }catch(InterruptedException e){}
        startActivity(new Intent(LoadingActivity.this, HomeActivity.class));
        finish();
    }

    public void progress(){

    }
}
