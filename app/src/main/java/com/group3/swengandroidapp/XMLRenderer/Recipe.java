package com.group3.swengandroidapp.XMLRenderer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.*;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bumptech.glide.load.engine.Resource;
import com.group3.swengandroidapp.Filter;
import com.group3.swengandroidapp.R;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * {@link Recipe}<p>
 * A class describing a recipe to be used in the app<p>
 * This class contains the following attributes:<p>
 * {@linkplain String} {@linkplain #title} Name of the recipe<p>
 * {@linkplain String} {@linkplain #author} Author of the recipe<p>
 * {@linkplain String} {@linkplain #description} A short description as provided by the author.<p>
 * {@linkplain ArrayList}<{@linkplain Ingredient}> {@linkplain #ingredients} A list of all ingredients<p>
 * @see {@link Ingredient}, {@link Slide}
 * @author mb1510 (Team Leader)
 *
 */

public class Recipe implements Serializable {
    // Meta data
    private String title = "n/a";
    private String author = "n/a";
    private String description = "n/a";
    private String id = "n/a";
    private String thumbnail = "n/a";
    private String presentationID = "n/a";
    private String time = "n/a";
    private Presentation presentation;

    public final static int THUMBNAILSIZE = 250;


    // Filters
    private Filter.Info info = new Filter.Info();

    // Ingredients
    ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();

    // CONSTRUCTORS

    public Recipe() {

    }

    public Recipe(String title, String author, String description, String id) {

        // Set meta data, checking to make sure that each has been given
        // if not, leave at default of "n/a"
        if(title != null) {
            this.title = title;
        }
        if(author != null) {
            this.author = author;
        }
        if(description != null) {
            this.description = description;
        }
        if(id != null) {
            this.id = id;
        }
        // Both ArrayLists initialised to have 0 stored objects
        // (Whenever you add to an array list, it extends the size by 1 (I think))

        //ingredients = new ArrayList<Ingredient>(0);
        //slides = new ArrayList<Slide>(0);
    }


    // METHODS
    public String getNumFavourites(){
        //TODO: Access server and figure out a way to extract the number of users that have this recipe ID as a favourite!
        return "0";
    }


    // SETTERS
    public void setTitle(String title) {
        this.title = title;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setID(String id) {
        this.id = id;
    }
    public void setThumbnail(String url) { this.thumbnail = url; }
    public void setSpicy(Boolean spicy) {
        this.info.setSpicy(spicy);
    }
    public void setLactose(Boolean lactose) {
        this.info.setLactose(lactose);
    }
    public void setNuts(Boolean nuts) {
        this.info.setNuts(nuts);
    }
    public void setVegetarian(Boolean vegetarian) {
        this.info.setVegetarian(vegetarian);
    }
    public void setVegan(Boolean vegan) {
        this.info.setVegan(vegan);
    }
    public void setGluten(Boolean gluten) {
        this.info.setGluten(gluten);
    }
    public void setPresentation(Presentation presentation) {
        this.presentation = presentation;
    }
    public void setIngredients(ArrayList<Ingredient> ingredients) {this.ingredients = ingredients;}
    public void appendIntgredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }
    public void setTime(String time){this.time = time;}

    // GETTERS
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public String getDescription() {
        return description;
    }
    public String getID() {
        return id;
    }
    public String getThumbnail() {
        return thumbnail;
    }
    public Presentation getPresentation() {
        return presentation;
    }
    public Boolean getSpicy() {
        return this.info.getSpicy();
    }
    public Boolean getLactose() {
        return this.info.getLactose();
    }
    public Boolean getNuts() {
        return this.info.getNuts();
    }
    public Boolean getVegetarian() {
        return this.info.getVegetarian();
    }
    public Boolean getVegan() {
        return this.info.getVegan();
    }
    public Boolean getGluten() {return this.info.getGluten();}
    public Filter.Info getFilterInfo(){ return this.info; }
    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }
    public String getTime(){return this.time;}


    public String generateIngredientsString(){
        StringBuilder sb = new StringBuilder();
        for(Ingredient i : this.ingredients){
            sb.append("- " + i.getName() + ": " + i.getQuantityValue()+" " + i.getQuantityUnits()+"\n");
        }
        return sb.toString();
    }

    public static class Icon {
        private String title;
        private String numFavourites;
        private String time;
        private Drawable image;
        private String id;

        public Icon(String title, Drawable image, String numFavourites, String time, String id){
            this.title = title;
            this.image = image;
            this.time = time;
            this.numFavourites = numFavourites;
            this.id = id;
        }

        public String getTitle(){return this.title;}
        public Drawable getDrawable(){return this.image;}
        public String getTime(){return this.time;}
        public String getNumFavourites(){return this.numFavourites;}
        public String getId(){return this.id;}
    }

    public static Icon produceDescriptor(Context c, Recipe recipe) {
        Drawable image = null;

        if(recipe.getThumbnail().contains("http")){
            try{
                URL url = new URL(recipe.getThumbnail());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                image = new BitmapDrawable(c.getResources(), BitmapFactory.decodeStream(input));
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            image = new BitmapDrawable(c.getResources(), recipe.getThumbnail());
        }
        return new Recipe.Icon(recipe.getTitle(), image, recipe.getNumFavourites(), recipe.getTime(), recipe.getID());
    }


}
