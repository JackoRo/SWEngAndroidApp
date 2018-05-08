package com.group3.swengandroidapp;


import android.os.Bundle;

/**
 * Created by Kevin on 12/03/2018.
 */

public class SettingsActivity extends MainActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        super.onCreateDrawer();

        setTitle("Settings");
    }
}
