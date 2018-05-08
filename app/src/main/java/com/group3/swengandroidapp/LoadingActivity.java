package com.group3.swengandroidapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.group3.swengandroidapp.ShoppingList.Intent_Constants;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Made to give the PythonClient Time to download all its' recipes
 * Created by Marco on 04/04/2018.
 */


public class LoadingActivity extends AppCompatActivity {

    BroadcastReceiver receiver;
    TextView messageOnScreen;
    Timer timer;
    TimerTask task;

    public static boolean progress = false;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        messageOnScreen = findViewById(R.id.splash_screen_message);
        messageOnScreen.setText(R.string.waitingForServer);

        timer = new Timer();

        task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setAction(PythonClient.SERVER_TIMEOUT);
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

            }
        };

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                messageOnScreen.setText("");

                String extra = intent.getStringExtra(PythonClient.ACTION);
                String action = intent.getAction();
                if(action != null){
                    switch(action){
                        case "XML-event-name":
                            if(extra.matches(PythonClient.LOAD_ALL)){
                                timer.cancel();
                                LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(receiver);
                                Intent nextIntent = new Intent();
                                nextIntent.setClass(getApplicationContext(),HomeActivity.class);                   // Set new activity destination
                                nextIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);            // Delete previous activities
                                startActivityForResult(nextIntent, Intent_Constants.INTENT_REQUEST_CODE);
                            }
                            break;
                        case PythonClient.SERVER_TIMEOUT:
                            Intent loadall = new Intent(getApplicationContext(), PythonClient.class);
                            loadall.putExtra(PythonClient.ACTION,PythonClient.LOAD_ALL);
                            startService(loadall);
                            messageOnScreen.setText(R.string.serverTimeOut);
                            break;
                    }
                }
            }
        };


    }

    @Override
    protected void onResume(){
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction("XML-event-name");
        filter.addAction(PythonClient.SERVER_TIMEOUT);
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(receiver, filter);
    }

    @Override
    public void onPause(){
        super.onPause();
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(receiver);
    }
}
