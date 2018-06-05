package com.group3.swengandroidapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import com.group3.swengandroidapp.ShoppingList.Intent_Constants;


/**
 * Created by Kevin on 12/03/2018.
 * edited by af981/Alex
 */

public class SettingsActivity extends MainActivity{

    private static Boolean AudioMuted = false;
    private static Boolean NotifcationMuted = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        super.onCreateDrawer();

        setTitle("Settings");

        Switch audioSwitch = (Switch) findViewById(R.id.audio_switch);

        // Get audio setting and set the switch
        if (AudioPlayer.isMuted()){
            audioSwitch.setChecked(false);
        } else {
            audioSwitch.setChecked(true);
        }

        // Audio switch listener
        audioSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                // Set the settings to the switch value
                if (isChecked) {
                    AudioPlayer.mute(false);
                }
                else {
                    AudioPlayer.mute(true);
                }

                // Play sound and vibrate
                AudioPlayer.touchSound();
                if (!AudioPlayer.isVibrationOff()){
                    vibrator.vibrate(20);
                }
            }
        });

        Switch vibrationSwitch = (Switch) findViewById(R.id.vibration);

        // Get haptic vibration setting and set the switch
        if (AudioPlayer.isVibrationOff()){
            vibrationSwitch.setChecked(false);
        } else {
            vibrationSwitch.setChecked(true);
        }

        // Haptic vibration switch listener
        vibrationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                // Set the settings to the switch values
                if (isChecked) {
                    AudioPlayer.vibrationOff(false);
                }
                else {
                    AudioPlayer.vibrationOff(true);
                }

                // Play sound and vibrate
                AudioPlayer.touchSound();
                if (!AudioPlayer.isVibrationOff()){
                    vibrator.vibrate(20);
                }
            }
        });

    }

}
