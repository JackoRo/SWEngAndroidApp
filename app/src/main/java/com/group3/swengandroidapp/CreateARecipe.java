package com.group3.swengandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.group3.swengandroidapp.XMLRenderer.Ingredient;
import com.group3.swengandroidapp.XMLRenderer.Recipe;
import com.group3.swengandroidapp.XMLRenderer.RemoteFileManager;

public class CreateARecipe extends AppCompatActivity {

    Recipe temporaryRecipe;


    EditText etTitle;
    EditText etDescription;

    String title;
    String description;

    int idFlag = 0;
    int id_integer;
    String id_String;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_arecipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button ingredientsBtn = (Button)(findViewById(R.id.add_ingredients));
        Button createRecipeBtn = (Button)(findViewById(R.id.create_recipe));
        createRecipeBtn.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){

                etTitle = (EditText)findViewById(R.id.title_input);
                etDescription = (EditText)findViewById(R.id.description_input);

                title = etTitle.getText().toString();
                description = etDescription.getText().toString();

                configureID(idFlag);
                convertID(id_integer);

                temporaryRecipe.setTitle(title);
                temporaryRecipe.setDescription(description);
                temporaryRecipe.setAuthor("User");
                temporaryRecipe.setID(id_String);
                idFlag++;

                RemoteFileManager userRecipe = RemoteFileManager.getInstance();
                userRecipe.setRecipe(id_String, temporaryRecipe);
            }

        });

        ingredientsBtn.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                Intent launchIngredientsPage = new Intent(CreateARecipe.this, AddIngredients.class);
                startActivity(launchIngredientsPage);
            }

        });}

        int configureID(int idFlag){
            if(idFlag == 0){
                id_integer = 12;
            }

            else{
                id_integer++;
            }

            return id_integer;
    }

        String convertID(int id_integer){

            if(id_integer < 100){
                id_String = "00" + id_integer;
            }
            else if(id_integer < 1000){
                id_String = "0" + id_integer;
            }
            else{
                id_String = "" + id_integer;
            }

            return id_String;
        }





}
