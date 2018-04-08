package com.group3.swengandroidapp;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.group3.swengandroidapp.XMLRenderer.Recipe;
import com.group3.swengandroidapp.XMLRenderer.RemoteFileManager;

/**
 * The home screen of the app.
 * <p>
 *     Displays a history of recently viewed recipes (as contained within HistoryHandler) and a
 *     "recommended" list of recipes.
 * </p>
 * Created by Kevin on 12/03/2018 and edited by mb1510 (Team Leader).
 */

//public class HomeActivity extends MainActivity  {

public class HomeActivity extends MainActivity implements RecipeRecyclerViewAdaper.ItemClickListener{

    private RecipeRecyclerViewAdaper recipeAdapter;
    private RecipeRecyclerViewAdaper historyAdapter;

    /**
     * Method called when a recipe is clicked from the home screen either in the history or main section
     */
    @Override
    public void onItemClick(View view, int position){
        Log.d("HomeActivity","Clicked on recipe " + position + "!: " + recipeAdapter.getItem(position).getTitle() + ". ID: "+ recipeAdapter.getItem(position).getId());
        Intent intent;
        intent = new Intent();
        intent.setClass(this,RecipeSelectionActivity.class);                 // Set new activity destination
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  // Delete previous activities
        intent.putExtra(PythonClient.ID, recipeAdapter.getItem(position).getId());
        startActivityForResult(intent, IntentConstants.INTENT_REQUEST_CODE);            // switch activities
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        super.onCreateDrawer();

        //Setup RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.home_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recipeAdapter = new RecipeRecyclerViewAdaper(this){
            @Override
            public void notifyActivity(String id){
                historyAdapter.notifyRecipeChanged(id);
            }
        };
        recipeAdapter.setClickListener(new RecipeRecyclerViewAdaper.ItemClickListener(){
            @Override
            public void onItemClick(View view, int position){
                Log.d("HomeActivity","Clicked on recipe " + position + "!: " + recipeAdapter.getItem(position).getTitle() + ". ID: "+ recipeAdapter.getItem(position).getId());
                Intent intent;
                intent = new Intent();
                intent.setClass(getApplicationContext(),RecipeSelectionActivity.class);                 // Set new activity destination
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  // Delete previous activities
                intent.putExtra(PythonClient.ID, recipeAdapter.getItem(position).getId());
                startActivityForResult(intent, IntentConstants.INTENT_REQUEST_CODE);            // switch activities
            }
        });
        recyclerView.setAdapter(recipeAdapter);

        // Setup History
        RecyclerView historyView = (RecyclerView)findViewById(R.id.home_history_bar);
        historyAdapter = new RecipeRecyclerViewAdaper(this){
            @Override
            public void notifyActivity(String id){
                recipeAdapter.notifyRecipeChanged(id);
                historyAdapter.notifyRecipeChanged(id); // incase it's the recipe of the day
            }
        };
        historyView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        historyAdapter.setClickListener(new RecipeRecyclerViewAdaper.ItemClickListener(){
            @Override
            public void onItemClick(View view, int position){
                Log.d("HomeActivity","Clicked on recipe " + position + "!: " + recipeAdapter.getItem(position).getTitle() + ". ID: "+ recipeAdapter.getItem(position).getId());
                Intent intent;
                intent = new Intent();
                intent.setClass(getApplicationContext(),RecipeSelectionActivity.class);                 // Set new activity destination
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  // Delete previous activities
                intent.putExtra(PythonClient.ID, historyAdapter.getItem(position).getId());
                startActivityForResult(intent, IntentConstants.INTENT_REQUEST_CODE);            // switch activities
            }
        });
        historyView.setAdapter(historyAdapter);
    }

    @Override
    public void onStart(){
        super.onStart();

        Log.d("TEST","Home start - Start");
        setTitle("Home");

        // STEP 1: Generate Recipe IDs to view! (Suggested & History)
        String[] histories = HistoryHandler.getInstance().getHistory();
        if(histories != null){
            Recipe[] history = new Recipe[histories.length];
            int counter = 0;

            Log.d("TEST","Loading histories");
            for(String s : histories){
                history[counter] = RemoteFileManager.getInstance().getRecipe(s);
                counter++;
            }
        }

        // STEP 2: Add Recipes in containers
        // 2.1: Suggested recipes
        int numSuggested = RemoteFileManager.getInstance().getRecipeList().size();
        if(!RemoteFileManager.getInstance().getRecipeList().isEmpty()){
            int counter = 0;
            for(String key : RemoteFileManager.getInstance().getRecipeList().keySet()){
                recipeAdapter.addRecipe(key);
                Intent downloadreq = new Intent(this, ImageDownloaderService.class);
                downloadreq.setAction(ImageDownloaderService.DOWNLOAD_TO_RECIPE);
                downloadreq.putExtra(ImageDownloaderService.RECIPE_ID, key);
                downloadreq.setData(Uri.parse(key));
                startService(downloadreq);
            }
        }

        // 2.2 History
        // Recipe of the day
        Recipe recipeOfTheDay = RemoteFileManager.getInstance().getRecipeOfTheDay();
        if(RemoteFileManager.getInstance().getRecipeOfTheDay()!=null){
            historyAdapter.addRecipe(recipeOfTheDay);
        }
        // History
        if(histories!=null){
            Log.d("HISTORY","Adding items");
            for(String s : histories){
                // Go through each item and make sure duplicate recipe isnt added to history display
                if(recipeOfTheDay.getID() != s){
                    historyAdapter.addRecipe(s);
                    Intent downloadreq = new Intent(this, ImageDownloaderService.class);
                    downloadreq.setAction(ImageDownloaderService.DOWNLOAD_TO_RECIPE);
                    downloadreq.putExtra(ImageDownloaderService.RECIPE_ID, s);
                    downloadreq.setData(Uri.parse(s));
                    startService(downloadreq);
                }
            }
        }
    }


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            if(message != null) Log.d("receiver", "Got message: " + message);

            if (intent.getStringExtra(PythonClient.ACTION).matches(PythonClient.FETCH_RECIPE)) {
                Intent newIntent = new Intent(context, RecipeSelectionActivity.class);
                startActivity(newIntent);
            }else {
                Log.d("ASDLKA", intent.getStringExtra(PythonClient.ACTION));
            }
        }
    };

    @Override
    public void onResume(){
        super.onResume();
        recipeAdapter.onResume();
        historyAdapter.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
        recipeAdapter.onPause();
        historyAdapter.onPause();
    }


    @Override
    protected void onStop(){
        // When activity is no longer visible
        Log.d("TEST", "Stopping...");
        super.onStop();
        //recipeAdapter.clearView();
        //historyAdapter.clearView()
        //
    }

    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        Log.d("TEST", "Destroying...");
        super.onDestroy();
    }

}
