package com.group3.swengandroidapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.group3.swengandroidapp.XMLRenderer.Presentation;
import com.group3.swengandroidapp.XMLRenderer.RemoteFileManager;

public class PresentationActivity extends AppCompatActivity {

    static Presentation presentation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation);

        // TODO FIX THIS
        if (presentation != null && savedInstanceState == null) {
            // This activity has been recreated, but we do not have a saved state
            // reset the presentation to the first slide. If we have opened a different
            // presentation, it will be overwritten anyway
            //presentation.restart();
        }

        Intent receivedIntent = getIntent();

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("XML-event-name"));

        Intent intent = new Intent(this, PythonClient.class);
        intent.putExtra(PythonClient.ACTION,PythonClient.FETCH_PRESENTATION);
        intent.putExtra(PythonClient.ID,receivedIntent.getStringExtra(PythonClient.ID));
        startService(intent);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        // Get extra data included in the Intent
        String message = intent.getStringExtra(PythonClient.ACTION);
        Log.d("PresentationActivity","received presentation" + intent.getStringExtra(PythonClient.ACTION));

        if (message.matches(PythonClient.FETCH_PRESENTATION)) {
            String presentationID = intent.getStringExtra(PythonClient.ID);
            presentation = RemoteFileManager.getInstance().getPresentation(presentationID);
            presentation.draw(PresentationActivity.this);
        }
        //fragmentManager.beginTransaction().replace(presentation.getLayout().getId(),fragment).commit();

        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }
}
