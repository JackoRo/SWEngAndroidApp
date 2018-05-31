package com.group3.swengandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.group3.swengandroidapp.XMLRenderer.Ingredient;

import java.util.ArrayList;

public class AddIngredients extends AppCompatActivity {

    EditText etIngredientName;
    EditText etQuantity;

    String ingredientName;
    String quantity;

    ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredients);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button addIngredientBtn = (Button)(findViewById(R.id.ingredient_add));
        Button exitIngredientsBtn = (Button)(findViewById(R.id.exit_ingredients_page));

        addIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                etIngredientName = (EditText)(findViewById(R.id.ingredient_name));
                etQuantity = (EditText)(findViewById(R.id.ingredient_quantity));

                ingredientName = etIngredientName.getText().toString();
                quantity = etQuantity.getText().toString();

                ingredients.add(new Ingredient(ingredientName, quantity));

                etIngredientName.setText("");
                etQuantity.setText("");

                Toast.makeText(getApplicationContext(), "Ingredient added!", Toast.LENGTH_LONG).show();

            }
        });


        exitIngredientsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnToCreateARecipe = new Intent(AddIngredients.this, CreateARecipe.class);
                returnToCreateARecipe.putExtra("ingredientlist", ingredients);
                finish();
            }
        });


    }

}
