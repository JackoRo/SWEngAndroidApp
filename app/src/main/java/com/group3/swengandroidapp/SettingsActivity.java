package com.group3.swengandroidapp;


import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;


/**
 * Created by Kevin on 12/03/2018.
 */

public class SettingsActivity extends MainActivity{


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        super.onCreateDrawer();

        setTitle("Settings");

        Switch audioSwitch = (Switch) findViewById(R.id.audio_switch);

        audioSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){

            }
        });
    }

    public void onClick(View view)
    {
        boolean audioSwitchOn = ((Switch) view).isChecked();

        if (audioSwitchOn) {
            AudioPlayer.mute(false);
        }
        else {
            AudioPlayer.mute(true);
        }
    }





}
