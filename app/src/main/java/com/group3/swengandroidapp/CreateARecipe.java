package com.group3.swengandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.group3.swengandroidapp.XMLRenderer.Ingredient;
import com.group3.swengandroidapp.XMLRenderer.Recipe;
import com.group3.swengandroidapp.XMLRenderer.RemoteFileManager;

import java.util.ArrayList;

public class CreateARecipe extends AppCompatActivity {

    Recipe temporaryRecipe;

    EditText etTitle;
    EditText etDescription;
    EditText etIngredientName;
    EditText etQuantity;

    String title;
    String description;
    String ingredientName;
    String quantity;

    ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();

    int idFlag = 0;
    int id_integer;
    String id_String;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_arecipe);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button addIngredientBtn = findViewById(R.id.add_ingredients);
        Button createRecipeBtn = findViewById(R.id.create_recipe);


        createRecipeBtn.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){

                etTitle = findViewById(R.id.title_input);
                etDescription = findViewById(R.id.description_input);

                title = etTitle.getText().toString();
                description = etDescription.getText().toString();

                configureID(idFlag);
                convertID(id_integer);

                temporaryRecipe.setTitle(title);
                temporaryRecipe.setDescription(description);
                temporaryRecipe.setAuthor("User");
                temporaryRecipe.setID(id_String);
                temporaryRecipe.setIngredients(ingredients);
                idFlag++;

                RemoteFileManager userRecipe = RemoteFileManager.getInstance();
                userRecipe.setRecipe(id_String, temporaryRecipe);
            }

        });

        addIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etIngredientName = findViewById(R.id.ingredient_name);
                etQuantity = findViewById(R.id.ingredient_quantity);

                ingredientName = etIngredientName.getText().toString();
                quantity = etQuantity.getText().toString();

                ingredients.add(new Ingredient(ingredientName, quantity));

                etIngredientName.setText("");
                etQuantity.setText("");

                Toast.makeText(getApplicationContext(), "Ingredient added!", Toast.LENGTH_LONG).show();
            }
        });
    }

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
