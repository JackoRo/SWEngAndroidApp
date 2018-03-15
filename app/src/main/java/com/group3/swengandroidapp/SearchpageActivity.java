package com.group3.swengandroidapp;

import java.util.ArrayList;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import com.group3.swengandroidapp.XMLRenderer.Drawable;

public class SearchpageActivity extends AppCompatActivity {

    public ArrayList<String> recipes = new ArrayList<String>();
    // the arraylist of string names from all recipes

    public ArrayList<String> FoundList = new ArrayList<String>();
    // the list to be output

    public String search;

    public Boolean spicyFilter = false;
    public Boolean lactoseFilter = false;
    public Boolean nutsFilter = false;
    public Boolean vegetarianFilter = false;
    public Boolean veganFilter = false;
    public Boolean glutenFilter = false;


    @Override
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


        //Search algorithm
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



        //Toggle Buttons for filters
        ToggleButton toggleSpicy = (ToggleButton) findViewById(R.id.togglebuttonSpicy);
        toggleSpicy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    spicyFilter = true;
                   toggleSpicy.setBackgroundResource(R.drawable.spicy_filter);
                } else {
                    spicyFilter = false;
                    toggleSpicy.setBackgroundResource(R.drawable.spicy_filter_grey);
                }
            }
        });

        ToggleButton toggleVegetarian = (ToggleButton) findViewById(R.id.togglebuttonVegetarian);
        toggleVegetarian.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    vegetarianFilter = true;
                    toggleVegetarian.setBackgroundResource(R.drawable.vegetarian_filter);
                } else {
                    vegetarianFilter = false;
                    toggleVegetarian.setBackgroundResource(R.drawable.vegetarian_filter_grey);
                }
            }
        });

        ToggleButton toggleVegan = (ToggleButton) findViewById(R.id.togglebuttonVegan);
        toggleVegan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    veganFilter = true;
                    toggleVegan.setBackgroundResource(R.drawable.vegan_filter);
                } else {
                    veganFilter = false;
                    toggleVegan.setBackgroundResource(R.drawable.vegan_filter_grey);
                }
            }
        });

        ToggleButton toggleLactose = (ToggleButton) findViewById(R.id.togglebuttonLactose);
        toggleLactose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    lactoseFilter = true;
                    toggleLactose.setBackgroundResource(R.drawable.lactosefree_filter);
                } else {
                    lactoseFilter = false;
                    toggleLactose.setBackgroundResource(R.drawable.lactosefree_filter_grey);
                }
            }
        });

        ToggleButton toggleNut = (ToggleButton) findViewById(R.id.togglebuttonNut);
        toggleNut.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    nutsFilter = true;
                    toggleNut.setBackgroundResource(R.drawable.nutfree_filter);
                } else {
                    nutsFilter = false;
                    toggleNut.setBackgroundResource(R.drawable.nutfree_filter_grey);
                }
            }
        });


        ToggleButton toggleGluten = (ToggleButton) findViewById(R.id.togglebuttonGluten);
        toggleGluten.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    glutenFilter = true;
                    toggleGluten.setBackgroundResource(R.drawable.glutenfree_filter);
                } else {
                    glutenFilter = false;
                    toggleGluten.setBackgroundResource(R.drawable.glutenfree_filter_grey);
                }
            }
        });
    }
}