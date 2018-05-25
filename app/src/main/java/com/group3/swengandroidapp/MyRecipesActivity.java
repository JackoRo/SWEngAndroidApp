package com.group3.swengandroidapp;

import android.os.Bundle;

/**
 * Created by Kevin on 12/03/2018.
 */

public class MyRecipesActivity extends MainActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recipes);
        super.onCreateDrawer();

        setTitle("Settings");
    }
}
