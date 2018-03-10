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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("XML-event-name"));

        Intent intent = new Intent(this, PythonClient.class);
        intent.putExtra(PythonClient.ACTION,PythonClient.FETCH_PRESENTATION);
        intent.putExtra(PythonClient.ID,"example");
        startService(intent);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra(PythonClient.ACTION);
            Log.d("SAASDASD", intent.getStringExtra(PythonClient.ACTION));

            if (message == PythonClient.FETCH_PRESENTATION) {
                Presentation presentation = RemoteFileManager.getInstance().getPresentation("example");
                presentation.draw(PresentationActivity.this);
            }
            //fragmentManager.beginTransaction().replace(presentation.getLayout().getId(),fragment).commit();

        }
    };

}
