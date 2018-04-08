/*This class will receive arraylist of favourite recipes from home activity class and
 *display the recipes (currently in ListView).*/

package com.group3.swengandroidapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.group3.swengandroidapp.XMLRenderer.*;
import com.group3.swengandroidapp.XMLRenderer.Recipe;

import java.util.ArrayList;
import java.util.HashMap;

public class FavouritesActivity extends MainActivity implements RecipeRecyclerViewAdaper.ItemClickListener{

    RecipeRecyclerViewAdaper displayAdapter;
    HashMap<String, Recipe.Icon> icons;
    ImageDownloaderListener imageDownloaderListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        super.onCreateDrawer();

        // SETUP RECIPE CONTAINER
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.favourites_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        displayAdapter = new RecipeRecyclerViewAdaper(this);
        displayAdapter.setClickListener(this);
        recyclerView.setAdapter(displayAdapter);

        icons = new HashMap<>();
    }

    @Override
    protected void onStart(){
        setTitle("Favourites");
        super.onStart();
        ArrayList<String> favourites = FavouritesHandler.getInstance().getFavourites();
        //displayAdapter.setRecipes(FavouritesHandler.getInstance().getFavourites());
        for(String s : favourites){
            if(!icons.containsKey(s)){
                icons.put(s, Recipe.produceDescriptor(this, RemoteFileManager.getInstance().getRecipe(s)));
            }
            displayAdapter.addIcon(icons.get(s));
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        imageDownloaderListener = new ImageDownloaderListener(this) {
            @Override
            public void onReceive(Context context, Intent intent) {
                String id = intent.getStringExtra(Recipe.ID);
                if(id != null){
                    String action = intent.getAction();
                    if(action != null){
                        switch(action){
                            case ImageDownloaderService.BITMAP_READY:
                                String absolutePath = intent.getStringExtra(ImageDownloaderService.JPG_FILE_PATH);
                                Log.d("ImageDownloaderListener", "Received file path: " + absolutePath);
                                if(absolutePath != null){
                                    icons.get(id).setDrawable(ImageDownloaderService.fetchBitmapDrawable(absolutePath));
                                    displayAdapter.notifyIconChanged(id);
                                }
                                break;
                        }
                    }
                }
            }
        };

        for(String id : icons.keySet()){
            requestBitmapFile(id);
        }
    }

    @Override
    public void onItemClick(View view, int position){
        Log.d("HomeActivity","Clicked on recipe " + position + "!: " + displayAdapter.getItem(position).getTitle() + ". ID: "+ displayAdapter.getItem(position).getId());

        Intent intent;
        intent = new Intent();
        intent.setClass(this,RecipeSelectionActivity.class);                 // Set new activity destination
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  // Delete previous activities
        intent.putExtra(PythonClient.ID, displayAdapter.getItem(position).getId());
        startActivityForResult(intent, IntentConstants.INTENT_REQUEST_CODE);            // switch activities

    }

    @Override
    public void onPause(){
        super.onPause();
        imageDownloaderListener.destroy();
    }
}
