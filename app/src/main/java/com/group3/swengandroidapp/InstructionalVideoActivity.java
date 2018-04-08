package com.group3.swengandroidapp;


import android.os.Bundle;
import android.widget.Toast;

/**
 *
 * Created by Kevin on 12/03/2018.
 */

public class InstructionalVideoActivity extends MainActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructional_video);
        super.onCreateDrawer();

        setTitle("Instructional Videos");

    }
    @Override
    public void onBackPressed(){
        Toast.makeText(getApplicationContext(), "Back Button Pressed", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }
}
