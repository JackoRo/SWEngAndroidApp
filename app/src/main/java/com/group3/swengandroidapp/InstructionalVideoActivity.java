package com.group3.swengandroidapp;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.group3.swengandroidapp.ShoppingList.Intent_Constants;
import com.group3.swengandroidapp.XMLRenderer.InstructionalVideo;
import com.group3.swengandroidapp.XMLRenderer.RemoteFileManager;

import java.util.HashMap;

/**
 * The instructional videos screen of the app.
 *
 * Displays a list of videos of cooking tips and instructions
 *
 */
public class InstructionalVideoActivity extends MainActivity implements InstructionalVideoRecyclerViewAdapter.ItemClickListener{

    private InstructionalVideoRecyclerViewAdapter instructionalVideosAdapter;          // adapter to Instructional Videos Adapter recyclerview
    private ImageDownloaderListener imageDownloaderListener;    // Listens for BITMAP_READY messages from ImageDownloaderService
    HashMap<String, InstructionalVideo.Icon> icons = new HashMap<>();       // Contains all icons that are to be deplyed on this page

    /**
     * Method called when a video icon is clicked
     */
    @Override
    public void onItemClick(String videoID){
        AudioPlayer.touchSound();
        if (!AudioPlayer.isVibrationOff()){
            vibrator.vibrate(20);
        }

        Log.d("InstructionalActivity","Clicked on video " + videoID);

        Intent intent = new Intent();
        intent.setClass(this,InstructionalVideoPlayingActivity.class);                   // Set new activity destination
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);                                    // Delete previous activities
        intent.putExtra(PythonClient.ID, videoID);       // Set  video id
        startActivityForResult(intent, Intent_Constants.INTENT_REQUEST_CODE);                // switch activities
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructional_video);
        super.onCreateDrawer();

        setTitle("Instructional Videos");

        // Setup Instructional videos view
        RecyclerView recyclerView = findViewById(R.id.instructional_videos_view);                // Get instructional videos view
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));        // Set as a 2-column grid
        instructionalVideosAdapter = new InstructionalVideoRecyclerViewAdapter(this);                     // Initialise the adapter for the view
        instructionalVideosAdapter.setClickListener(this);                                          // Set the click listener for the adapter
        recyclerView.setAdapter(instructionalVideosAdapter);                                        // Assign adapter to the view
    }

    @Override
    public void onStart(){
        super.onStart();

        String[] instructionalVideos = RemoteFileManager.getInstance().getInstructionalVideos();

        // Process the instructional videos view
        for(String id : instructionalVideos){
            if(!icons.containsKey(id)){
                icons.put(id, InstructionalVideo.produceDescriptor(this, RemoteFileManager.getInstance().getInstructionalVideo(id)));
            }
            instructionalVideosAdapter.addIcon(icons.get(id));
        }

        // Assign thumbnail pictures for the instructional videos
        Drawable frying_onions = new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.frying_onions));
        Drawable cutting_onions = new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.cutting_onions));
        Drawable grilling_meat = new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.grilling_meat));
        Drawable frying_egg = new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.frying_egg));

        //Create thumbnails
        instructionalVideosAdapter.addIcon(new InstructionalVideo.Icon("Frying Onions", frying_onions, "frying_onions"));
        instructionalVideosAdapter.addIcon(new InstructionalVideo.Icon("Cutting Onions", cutting_onions, "cutting_onions"));
        instructionalVideosAdapter.addIcon(new InstructionalVideo.Icon("Grilling Meat", grilling_meat, "grilling_meat"));
        instructionalVideosAdapter.addIcon(new InstructionalVideo.Icon("Frying Eggs", frying_egg, "frying_egg"));

        // Notify the adapters to update themselves.
        instructionalVideosAdapter.notifyDataSetChanged();

    }


    @Override
    public void onResume(){
        super.onResume();

        // Startup imageDownloaderListener
        imageDownloaderListener = new ImageDownloaderListener(this) {
            @Override
            public void onBitmapReady(String id, String absolutePath){
                icons.get(id).setDrawable(ImageDownloaderService.fetchBitmapDrawable(absolutePath));
                instructionalVideosAdapter.notifyIconChanged(id);
            }

            @Override
            public void onIconChanged(String id){
                instructionalVideosAdapter.notifyIconChanged(id);
            }
        };

        // Background load the icon thumbnails
        for(String id : icons.keySet()){
            requestBitmapFile(id);
        }

    }

    @Override
    public void onPause(){
        super.onPause();
        imageDownloaderListener.unRegister();
    }

}
