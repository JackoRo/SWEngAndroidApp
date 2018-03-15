package com.group3.swengandroidapp;

import java.util.ArrayList;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.group3.swengandroidapp.XMLRenderer.Recipe;

public class SearchpageActivity extends AppCompatActivity implements RecipeRecyclerViewAdaper.ItemClickListener{

    RecipeRecyclerViewAdaper recipeAdapter;
    ArrayList<Recipe.Icon> recipesRecycler;

    public ArrayList<String> recipes = new ArrayList<String>();
    // the arraylist of string names from all recipes

    public ArrayList<String> FoundList = new ArrayList<String>();
    // the list to be output

    public String search;

    @Override
    public void onItemClick(View view, int position){
        Log.d("HomeActivity","Clicked on recipe " + position + "!: " + recipeAdapter.getItem(position).getTitle() + ". ID: "+ recipeAdapter.getItem(position).getId());
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);

        //When search button is click store the input string of the search bar
        final ImageButton button = findViewById(R.id.imageButton3);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                hideKeyboard();
                final TextInputLayout searchBar = findViewById(R.id.textInputLayout2);
                search = searchBar.getEditText().getText().toString();
            }

            private void hideKeyboard() {
                View view = getCurrentFocus();
                if (view != null) {
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                            hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });

        //Setup RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.constraintLayout2);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recipeAdapter = new RecipeRecyclerViewAdaper(this);
        recipeAdapter.setClickListener(this);
        recyclerView.setAdapter(recipeAdapter);



        if(search != null) {
            // Change to upper case to get rid of possible errors
            search = search.toUpperCase();

            // Remove recipes the user has not searched for
            ArrayList<String> subentries = new ArrayList<String>();

            for (Object entry : FoundList) {
                String entryText = (String) entry;
                if (entryText.toUpperCase().contains(search)) {
                    subentries.add(entryText);
                }
            }
            FoundList = subentries;
            System.out.format(search);
        }
        else {
            FoundList =  recipes ;
        }
    }

    @Override
    public void onStart(){
        super.onStart();

        // Generate Recipe IDs to view!
        String[] ids = {"0000", "0001", "0002", "0003", "0004"};

        // Add them to container! (or try)
        recipeAdapter.addRecipe(ids);
    }
    
}