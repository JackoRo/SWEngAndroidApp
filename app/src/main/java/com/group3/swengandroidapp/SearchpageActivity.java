package com.group3.swengandroidapp;

import java.util.ArrayList;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;

public class SearchpageActivity extends AppCompatActivity {

    public ArrayList<String> recipes = new ArrayList<String>();
    // the arraylist of string names from all recipes

    public ArrayList<String> FoundList = new ArrayList<String>();
    // the list to be output

    public String search;

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
}