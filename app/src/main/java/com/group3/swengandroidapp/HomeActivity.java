package com.group3.swengandroidapp;


import android.os.Bundle;

/**
 * Created by Kevin on 12/03/2018.
 */

public class HomeActivity extends MainActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        super.onCreateDrawer();

        setTitle("Home");
    }
}
