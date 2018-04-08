/*This class will receive arraylist of favourite recipes from home activity class and
 *display the recipes (currently in ListView).*/

package com.group3.swengandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.group3.swengandroidapp.XMLRenderer.Recipe;
import com.group3.swengandroidapp.XMLRenderer.RemoteFileManager;

import java.util.HashMap;

public class HistoryActivity extends MainActivity implements RecipeRecyclerViewAdaper.ItemClickListener{

    RecipeRecyclerViewAdaper displayAdapter;
    HashMap<String, Recipe.Icon> icons;
    ImageDownloaderListener imageDownloaderListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        super.onCreateDrawer();

        // SETUP RECIPE CONTAINER
        RecyclerView recyclerView = findViewById(R.id.history_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        displayAdapter = new RecipeRecyclerViewAdaper(this);
        displayAdapter.setClickListener(this);
        recyclerView.setAdapter(displayAdapter);

        icons = new HashMap<>();
    }

    @Override
    protected void onStart(){
        super.onStart();

        setTitle("History");
        String[] history = HistoryHandler.getInstance().getHistory();
        if(history != null){
            for(String id : history){
                if(!icons.containsKey(id)){
                    icons.put(id, Recipe.produceDescriptor(this, RemoteFileManager.getInstance().getRecipe(id)));
                }
                displayAdapter.addIcon(icons.get(id));
            }
            displayAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onResume(){
        super.onResume();

        imageDownloaderListener = new ImageDownloaderListener(this) {
            @Override
            public void onBitmapReady(String id, String absolutePath){
                icons.get(id).setDrawable(ImageDownloaderService.fetchBitmapDrawable(absolutePath));
                displayAdapter.notifyIconChanged(id);
            }
        };

        for(String id : icons.keySet()){
            requestBitmapFile(id);
        }

    }

    @Override
    public void onItemClick(View view, int position){
        Log.d("HomeActivity","Clicked on recipe " + position + "!: " + displayAdapter.getItem(position).getTitle() + ". UPDATED_RECIPE_ID: "+ displayAdapter.getItem(position).getId());

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

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
