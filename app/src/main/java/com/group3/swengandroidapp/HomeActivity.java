package com.group3.swengandroidapp;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.group3.swengandroidapp.XMLRenderer.Image;
import com.group3.swengandroidapp.XMLRenderer.Recipe;
import com.group3.swengandroidapp.XMLRenderer.RemoteFileManager;

/**
 * Created by Kevin on 12/03/2018.
 */

public class HomeActivity extends MainActivity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        super.onCreateDrawer();


        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("XML-event-name"));

        setTitle("Home");

        ImageButton imagebuttonServerExample = (ImageButton)findViewById(R.id.imageButton6);
        imagebuttonServerExample.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PythonClient.class);
                intent.putExtra(PythonClient.ACTION,PythonClient.FETCH_RECIPE);
                intent.putExtra(PythonClient.ID,"0000");
                startService(intent);
            }
        });

        ImageButton imagebuttonLocalExample = (ImageButton)findViewById(R.id.imageButton7);
        imagebuttonLocalExample.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PythonClient.class);
                intent.putExtra(PythonClient.ACTION,PythonClient.FETCH_RECIPE);
                intent.putExtra(PythonClient.ID,"0001");
                startService(intent);
            }
        });



    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            Log.d("receiver", "Got message: " + message);

            if (intent.getStringExtra(PythonClient.ACTION) == PythonClient.FETCH_RECIPE) {
                Intent newIntent = new Intent(context, RecipeSelectionActivity.class);
                newIntent.putExtra(PythonClient.ID, intent.getStringExtra(PythonClient.ID));
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
