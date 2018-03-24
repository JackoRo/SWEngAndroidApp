package com.group3.swengandroidapp;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;

import com.group3.swengandroidapp.XMLRenderer.Recipe;

/**
 * Created by Kevin on 12/03/2018.
 */

//public class HomeActivity extends MainActivity  {

public class HomeActivity extends MainActivity implements RecipeRecyclerViewAdaper.ItemClickListener{

    RecipeRecyclerViewAdaper recipeAdapter;
    RecipeRecyclerViewAdaper historyAdapter;

    @Override
    /**
     * Method called when a recipe is clicked from the home screen either in the history or main section
     */
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
        recipeAdapter = new RecipeRecyclerViewAdaper(this);
        recipeAdapter.setClickListener(this);
        recyclerView.setAdapter(recipeAdapter);

        // Setup History
        RecyclerView historyView = (RecyclerView)findViewById(R.id.home_history_bar);
        historyAdapter = new RecipeRecyclerViewAdaper(this);
        historyView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        historyAdapter.setClickListener(this);
        historyView.setAdapter(historyAdapter);
    }

    @Override
    public void onStart(){
        super.onStart();
        setTitle("Home");

        // Generate Recipe IDs to view!
        String[] ids = {"0000", "0001", "0002", "0003", "0004", "0005", "0006", "0007", "0008", "0009"};
        String[] histories = HistoryHandler.getInstance().getHistory();
        // Add them to container!
        recipeAdapter.setRecipes(ids);
        historyAdapter.addRecipe(new Recipe("RECIPE OF THE DAY", "TEST", "Test Recipe Holder","0101"));
        if(histories!=null){
            Log.d("HISTORY","Adding items");
            historyAdapter.addRecipe(histories);
        }
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
