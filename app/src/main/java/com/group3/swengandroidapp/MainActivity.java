package com.group3.swengandroidapp;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Serializable{
    Button b_save;
    Button b_view;
    List<String> words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        b_save = (Button) findViewById(R.id.toggleButton);

        words = new ArrayList<>();
        words.add("a");
        words.add("b");

        b_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder stringBuilder = new StringBuilder();

                for(String s: words){
                    stringBuilder.append(s);
                    stringBuilder.append(",");
                }

                SharedPreferences settings = getSharedPreferences("PREFS", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("words", stringBuilder.toString());
                editor.commit();

            }
        });

        b_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences settings = getSharedPreferences("PREFS", 0);
                String wordsString = settings.getString("words", "");
                String[] itemsWords = wordsString.split(",");
                List<String> items = new ArrayList<String>();

                for(int i = 0; i < itemsWords.length; i++){
                    items.add(itemsWords[i]);
                }

                for(int i=0; i<items.size(); i++){
                    Log.d("listItem", items.get(i));
                }

            }
        });


    }

   /* Recipe recipe_1 = new Recipe("falafel", 5);
    Intent i = new Intent(MainActivity.this, Favourites.class);
    i.putExtra("Bob", Recipe.class);
    startActivity(i);
    }*/

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_resource,menu);
        return true;
    }


}
