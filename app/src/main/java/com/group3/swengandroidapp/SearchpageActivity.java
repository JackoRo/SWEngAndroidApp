package com.group3.swengandroidapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import com.group3.swengandroidapp.ShoppingList.Intent_Constants;
import com.group3.swengandroidapp.XMLRenderer.Recipe;
import com.group3.swengandroidapp.XMLRenderer.RemoteFileManager;

public class SearchpageActivity extends AppCompatActivity implements RecipeRecyclerViewAdaper.ItemClickListener{

    private RecipeRecyclerViewAdaper displayAdapter;
    private ImageDownloaderListener imageDownloaderListener;

    private HashMap<String, Recipe.Icon> icons = new HashMap<>();

    private ArrayList<String> recipes = new ArrayList<String>();
    // the arraylist of string names from all recipes

    private ArrayList<String> foundList = new ArrayList<String>();
    // the list to be output

    private String search;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.searchPage_recipeContainer);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        displayAdapter = new RecipeRecyclerViewAdaper(this);
        displayAdapter.setClickListener(this);
        recyclerView.setAdapter(displayAdapter);

            //When search button is click store the input string of the search bar
        final ImageButton button = findViewById(R.id.searchPage_search_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                hideKeyboard();
                final TextInputLayout searchBar = findViewById(R.id.searchPage_text_input);
                search = searchBar.getEditText().getText().toString();
                Log.d("Search icon", "Pressed! Search with <" + search + ">");
                searchRecipes(search);

                displayAdapter.clear();

                for(String id : foundList){
                    displayAdapter.addIcon(Recipe.produceDescriptor(SearchpageActivity.this, RemoteFileManager.getInstance().getRecipe(id)));
                }

                displayAdapter.notifyDataSetChanged();
                Log.d("Search icon", "Data updated");

            }

            private void hideKeyboard() {
                View view = getCurrentFocus();
                if (view != null) {
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                            hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });



        //Toggle Buttons for filters
        ToggleButton toggleSpicy = (ToggleButton) findViewById(R.id.searchPage_togglebuttonSpicy);
        toggleSpicy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Filter.getInstance().getCriteria().setSpicy(isChecked);
                if (isChecked) {
                   toggleSpicy.setBackgroundResource(R.drawable.spicy_filter);
                } else {
                    toggleSpicy.setBackgroundResource(R.drawable.spicy_filter_grey);
                }
            }
        });

        ToggleButton toggleVegetarian = (ToggleButton) findViewById(R.id.searchPage_togglebuttonVegetarian);
        toggleVegetarian.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Filter.getInstance().getCriteria().setVegetarian(isChecked);
                if (isChecked) {
                    toggleVegetarian.setBackgroundResource(R.drawable.vegetarian_filter);
                } else {
                    toggleVegetarian.setBackgroundResource(R.drawable.vegetarian_filter_grey);
                }
            }
        });


        ToggleButton toggleVegan = (ToggleButton) findViewById(R.id.searchPage_togglebuttonVegan);
        toggleVegan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Filter.getInstance().getCriteria().setVegan(isChecked);
                if (isChecked) {
                    toggleVegan.setBackgroundResource(R.drawable.vegan_filter);
                } else {
                    toggleVegan.setBackgroundResource(R.drawable.vegan_filter_grey);
                }
            }
        });

        ToggleButton toggleLactose = (ToggleButton) findViewById(R.id.searchPage_togglebuttonLactose);
        toggleLactose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Filter.getInstance().getCriteria().setLactose(isChecked);
                if (isChecked) {
                    toggleLactose.setBackgroundResource(R.drawable.lactosefree_filter);
                } else {
                    toggleLactose.setBackgroundResource(R.drawable.lactosefree_filter_grey);
                }
            }
        });

        ToggleButton toggleNut = (ToggleButton) findViewById(R.id.searchPage_togglebuttonNuts);
        toggleNut.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Filter.getInstance().getCriteria().setNuts(isChecked);
                if (isChecked) {
                    toggleNut.setBackgroundResource(R.drawable.nutfree_filter);
                } else {
                    toggleNut.setBackgroundResource(R.drawable.nutfree_filter_grey);
                }
            }
        });


        ToggleButton toggleGluten = (ToggleButton) findViewById(R.id.searchPage_togglebuttonGluten);
        toggleGluten.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Filter.getInstance().getCriteria().setGluten(isChecked);
                if (isChecked) {
                    toggleGluten.setBackgroundResource(R.drawable.glutenfree_filter);
                } else {
                    toggleGluten.setBackgroundResource(R.drawable.glutenfree_filter_grey);
                }
            }
        });

        // LOAD CURRENT FILTER SETTINGS
        toggleSpicy.setChecked(Filter.getInstance().getCriteria().getSpicy());
        toggleGluten.setChecked(Filter.getInstance().getCriteria().getGluten());
        toggleLactose.setChecked(Filter.getInstance().getCriteria().getLactose());
        toggleNut.setChecked(Filter.getInstance().getCriteria().getNuts());
        toggleVegan.setChecked(Filter.getInstance().getCriteria().getVegan());
        toggleVegetarian.setChecked(Filter.getInstance().getCriteria().getVegetarian());
    }

    @Override
    protected void onStart(){
        super.onStart();
        //String[] ids = {"0000", "0001", "0002"};
        //displayAdapter.setRecipes(ids);
    }

//    @Override
//    public void onResume(){
//        super.onResume();
//
//        imageDownloaderListener = new ImageDownloaderListener(this) {
//            @Override
//            public void onBitmapReady(String id, String absolutePath){
//                icons.get(id).setDrawable(ImageDownloaderService.fetchBitmapDrawable(absolutePath));
//                displayAdapter.notifyIconChanged(id);
//            }
//        };
//
//        for(String id : icons.keySet()){
//            requestBitmapFile(id);
//        }
//
//    }

    @Override
    public void onItemClick(String recipeId){
        Log.d("HomeActivity","Clicked on recipe " + recipeId);
        Intent intent = new Intent();
        intent.setClass(this,RecipeSelectionActivity.class);           // Set new activity destination
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);                            // Delete previous activities
        intent.putExtra(PythonClient.ID, recipeId);
        startActivityForResult(intent, Intent_Constants.INTENT_REQUEST_CODE);        // switch activities

    }

    private void searchRecipes(String search) {
        //Fetch recipes
        RemoteFileManager rfm = RemoteFileManager.getInstance();

        //Search algorithm
        if(search != null) {
            // Change to upper case to get rid of possible errors
            search = search.toUpperCase();
            Log.d("searchRecipes","Searching with " + search);

            // Remove recipes the user has not searched for
            ArrayList<String> subentries = new ArrayList<String>();

            for (Map.Entry<String, Recipe> entry : rfm.getRecipeList().entrySet()) {
                String entryText = entry.getValue().getTitle();
                if (entryText.toUpperCase().contains(search)) {
                    subentries.add(entry.getKey());
                    Log.d("searchRecipes For-Loop","Obtained: " + entry.getKey());
                }
            }
            foundList = subentries;
            System.out.format(search);
        }
        else {
            foundList =  recipes ;
        }
    }
}