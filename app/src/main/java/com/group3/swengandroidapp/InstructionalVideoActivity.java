package com.group3.swengandroidapp;


import android.os.Bundle;

/**
 * Created by Kevin on 12/03/2018.
 */

public class InstructionalVideoActivity extends MainActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructional_video);
        super.onCreateDrawer();

        setTitle("Instructional Videos");

    }

}
