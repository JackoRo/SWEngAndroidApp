package com.group3.swengandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.group3.swengandroidapp.XMLRenderer.Ingredient;
import com.group3.swengandroidapp.XMLRenderer.Recipe;
import com.group3.swengandroidapp.XMLRenderer.RemoteFileManager;

import java.util.ArrayList;

public class CreateARecipe extends AppCompatActivity {

        private Recipe temporaryRecipe = new Recipe();

        private Button addIngredientBtn;
        private Button selectThumbnail;
        private Button createRecipeBtn;

        private EditText etTitle;
        private EditText etDescription;
        private EditText etTime;
        private EditText etIngredientName;
        private EditText etQuantity;

        private String title;
        private String description;
        private String author = "User";
        private String time;
        private String ingredientName;
        private String quantity;
        private ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();

        private int idFlag = 0;
        private int id_integer;
        private String id_String;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_create_arecipe);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            setTitle("Create A Recipe");

            addIngredientBtn = findViewById(R.id.add_ingredients);
            selectThumbnail = findViewById(R.id.select_thumbnail);
            createRecipeBtn = findViewById(R.id.create_recipe);

            etTitle = findViewById(R.id.title_input);
            etDescription = findViewById(R.id.description_input);
            etTime = findViewById(R.id.time_input);
            etIngredientName = findViewById(R.id.ingredient_name);
            etQuantity = findViewById(R.id.ingredient_quantity);

            //Disable the recipe creation button if the title editText is empty.
            etTitle.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    if(etTitle.getText().toString().isEmpty()){
                        createRecipeBtn.setClickable(false);
                        createRecipeBtn.setEnabled(false);
                    }
                    else{
                        createRecipeBtn.setClickable(true);
                        createRecipeBtn.setEnabled(true);
                    }
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(etTitle.getText().toString().isEmpty()){
                        createRecipeBtn.setClickable(false);
                        createRecipeBtn.setEnabled(false);
                    }
                    else{
                        createRecipeBtn.setClickable(true);
                        createRecipeBtn.setEnabled(true);
                    }
                }
            });

            selectThumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openGallery();
                }
            });

            createRecipeBtn.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    title = etTitle.getText().toString();
                    description = etDescription.getText().toString();
                    time = etTime.getText().toString();

                    //Assign recipe an ID and increment.
                    id_integer = configureID(idFlag);
                    id_String = convertID(id_integer);

                    /**Set recipe object to the contents of the editTexts and store
                    * via RemoteFileManager. and store recipe using entered information.**/
                    settingRecipe(title, author, description, id_String, time, ingredients);
                    RemoteFileManager userRecipe = RemoteFileManager.getInstance();
                    userRecipe.setRecipe(id_String, temporaryRecipe);
                }
            });

            addIngredientBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ingredientName = etIngredientName.getText().toString();
                    quantity = etQuantity.getText().toString();
                    ingredients.add(new Ingredient(ingredientName, quantity));
                    /**Clear so another ingredient can be added, and notify user ingredient
                     * has been added.**/
                    etIngredientName.setText("");
                    etQuantity.setText("");
                    Toast.makeText(getApplicationContext(), "Ingredient added!", Toast.LENGTH_LONG).show();
                }
            });
        }

    private void openGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1);
    }

    /**Select ID number to assign to the recipe that has not been previously assigned, based on
     * the assumption that only the user currently accesses these recipes.
     */

        private int configureID(int idFlag){
                if(idFlag == 0){
                    id_integer = 13;
                }
                else{
                    id_integer++;
                }

                return id_integer;
        }

        //Converts the ID number into a string in order to assign it to the recipe.
        private String convertID(int id_integer){
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

        //Set the recipe object to the strings in the editTexts and assign an ID.
        private void settingRecipe(String title, String author, String description, String id_String, String time, ArrayList<Ingredient> ingredients){
                temporaryRecipe.setTitle(title);
                temporaryRecipe.setDescription(description);
                temporaryRecipe.setTime(time);
                temporaryRecipe.setAuthor(author);
                temporaryRecipe.setID(id_String);
                temporaryRecipe.setIngredients(ingredients);
                idFlag++;
        }

}
