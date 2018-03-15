package com.group3.swengandroidapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Kevin on 15/03/2018.
 */

public class RecipeStepActivity extends Activity implements
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {

    private GestureDetectorCompat mDetector;

    int numberOfSteps;
    int currentStep;
    String[] steps;
    TextView tv;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);

        // Instantiate the gesture detector with the application context and an implementation of GestureDetector.OnGestureListener
        mDetector = new GestureDetectorCompat(this, this);
        // Set the gesture detector as the double tap listener.
        mDetector.setOnDoubleTapListener(this);

        numberOfSteps = getResources().getStringArray(R.array.steps).length;
        currentStep = 0;

        steps  = getResources().getStringArray(R.array.steps);

        tv = (TextView)findViewById(R.id.textView);
        tv.setText(steps[currentStep]);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if (this.mDetector.onTouchEvent(event)) {
            return true;
        }


        return super.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        currentStep ++;
        if (currentStep > numberOfSteps - 1){
            Intent intent = new Intent();
            intent.setClass(this,HomeActivity.class);                 // Set new activity destination
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  // Delete previous activities
            startActivityForResult(intent, IntentConstants.INTENT_REQUEST_CODE);            // switch activities
        }else{
            tv.setText(steps[currentStep]);
        }



        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        currentStep --;
        if (currentStep < 0){
            Intent intent = new Intent();
            intent.setClass(this,HomeActivity.class);                 // Set new activity destination
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  // Delete previous activities
            startActivityForResult(intent, IntentConstants.INTENT_REQUEST_CODE);            // switch activities
        } else{
            tv.setText(steps[currentStep]);
        }
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {

        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    };

}
