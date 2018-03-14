package com.group3.swengandroidapp;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageButton;

import com.group3.swengandroidapp.XMLRenderer.Image;
import com.group3.swengandroidapp.XMLRenderer.Recipe;

import java.util.ArrayList;

/**
 * Created by Kevin on 12/03/2018.
 */

public class HomeActivity extends MainActivity implements HomeRecyclerViewAdapter.ItemClickListener{

    HomeRecyclerViewAdapter adapter;
    ArrayList<HomeRecyclerViewAdapter.ItemDescriptor> recipes;


    @Override
    public void onItemClick(View view, int position){
        System.out.println("Clicked on recipe " + position + "!: " + adapter.getItem(position).text);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recipes = new ArrayList<HomeRecyclerViewAdapter.ItemDescriptor>(0);
        recipes.add(new HomeRecyclerViewAdapter.ItemDescriptor("Test Recipe 1", null));
        recipes.add(new HomeRecyclerViewAdapter.ItemDescriptor("Test Recipe 2", null));
        recipes.add(new HomeRecyclerViewAdapter.ItemDescriptor("Test Recipe 3", null));

        //Setup RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.home_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new HomeRecyclerViewAdapter(this, recipes);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        /*      PREVIOUS CODE
        super.onCreateDrawer();



        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("XML-event-name"));

        setTitle("Home");

        GridLayout layout = (GridLayout)findViewById(R.id.home_recyclerview);

        ImageButton imagebuttonExample = (ImageButton)findViewById(R.id.imageButton6);
        imagebuttonExample.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PythonClient.class);
                intent.putExtra(PythonClient.ACTION,PythonClient.FETCH_RECIPE);
                intent.putExtra(PythonClient.ID,"0000");
                startService(intent);

            }
        });*/
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            Log.d("receiver", "Got message: " + message);

            if (intent.getStringExtra(PythonClient.ACTION) == PythonClient.FETCH_RECIPE) {
                Intent newIntent = new Intent(context, RecipeSelectionActivity.class);
                startActivity(newIntent);
            }
            else {
                Log.d("ASDLKA", intent.getStringExtra(PythonClient.ACTION));
            }
        }
    };

    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

}
