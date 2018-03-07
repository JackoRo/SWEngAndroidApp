package com.group3.swengandroidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.group3.swengandroidapp.XMLRenderer.Presentation;
import com.group3.swengandroidapp.XMLRenderer.PresentationManager;

public class PresentationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation);

        String xmlFile = PresentationManager.getInstance().getXML();
        Presentation presentation = PresentationManager.getInstance().getPresentation();
        presentation.draw(PresentationActivity.this);
    }
}
