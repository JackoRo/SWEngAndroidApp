package com.group3.swengandroidapp.XMLRenderer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.*;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.group3.swengandroidapp.Filter;
import com.group3.swengandroidapp.Filter.Info;
import com.group3.swengandroidapp.MainActivity;
import com.group3.swengandroidapp.R;

import org.xmlpull.v1.XmlPullParser;
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

public class Recipe {
    // Meta data
    private String title = "n/a";
    private String author = "n/a";
    private String description = "n/a";
    private String id = "n/a";
    private String thumbnail = null;
    private String presentationID = "n/a";
    private Presentation presentation;

    private static final String DEFAULTTHUMBNAIL = R;
    private static final String FAVOURITEICONOFF = "../../../../res/drawable/heart_off.png";
    private static final String FAVOURITEICONON = "../../../../res/drawable/heart_on.png";
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
    public int getNumFavourites(){
        //TODO: Access server and figure out a way to extract the number of users that have this recipe ID as a favourite!
        return 10;
    }

    public View createView(Context context){
        android.graphics.drawable.Drawable layers[] = new android.graphics.drawable.Drawable[2];

        // Background image

        layers[0] = new BitmapDrawable(context.getResources(), BitmapFactory.decodeResource(context.getResources(), R.drawable.thumbnail));


        // Favourites Icon
        Bitmap fav = BitmapFactory.decodeResource(context.getResources(), R.drawable.heart_off);
        fav.eraseColor(0);  // Set transparrent
        layers[1] = new BitmapDrawable(context.getResources(), fav);
        layers[1].setBounds((int)(THUMBNAILSIZE*0.9), (int)(THUMBNAILSIZE*0.9), THUMBNAILSIZE, THUMBNAILSIZE);

        LayerDrawable test = new LayerDrawable(layers);
        //Layers: Background image, favourites, timer

        ImageView view = new ImageView(context);
        view.setImageDrawable(test);
        view.setMinimumWidth(THUMBNAILSIZE);
        view.setMinimumHeight(THUMBNAILSIZE);
        view.setMaxHeight(THUMBNAILSIZE);
        view.setMaxWidth(THUMBNAILSIZE);

        return view;
    }

    public Drawable createDrawable(Context context){
        android.graphics.drawable.Drawable layers[] = new android.graphics.drawable.Drawable[2];

        // Background image
        /*try{
            //Try fetching from string location of thumbnail
            layers[0] = new BitmapDrawable(context.getResources(), BitmapFactory.decodeFile(thumbnail));
        }catch(Exception e){
            //If error, use the default thumbnail
            layers[0] = new BitmapDrawable(context.getResources(), BitmapFactory.decodeFile(DEFAULTTHUMBNAIL));
        }*/

        layers[0] = new BitmapDrawable(context.getResources(), BitmapFactory.decodeResource(context.getResources(), R.drawable.thumbnail));

        // Favourites Icon
        Bitmap fav = BitmapFactory.decodeResource(context.getResources(), R.drawable.heart_off);
        fav.eraseColor(0);  // Set transparrent
        layers[1] = new BitmapDrawable(context.getResources(), fav);
        layers[1].setBounds((int)(THUMBNAILSIZE*0.9), (int)(THUMBNAILSIZE*0.9), THUMBNAILSIZE, THUMBNAILSIZE);

        LayerDrawable test = new LayerDrawable(layers);


        return test;
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

}
