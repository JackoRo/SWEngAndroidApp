package com.group3.swengandroidapp.XMLRenderer;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.group3.swengandroidapp.Filter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

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
    public final static int THUMBNAILSIZE = 250;
    public final static String ID = "Recipe_ID"; // Used with broadcast

    // Meta data
    private String title = "n/a";
    private String author = "n/a";
    private String description = "n/a";
    private String id = "n/a";
    private String thumbnail = "n/a";
    private String presentationID = "n/a";
    private String time = "n/a";
    private Presentation presentation;
    private Filter.Info info = new Filter.Info();
    private ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
    private HashSet<String> tags = new HashSet<>(0);

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
        //TODO: Access server and figure out a way to extract the number of users that have this recipe UPDATED_RECIPE_ID as a favourite!
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
    public void appendIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }
    public void setTime(String time){this.time = time;}
    public void setFilterInfo(Filter.Info info){this.info = info;}
    public void addKeyword(String keyword){
        this.tags.add(keyword);
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
    public String getTime(){return this.time;}
    public HashSet<String> getTags(){return this.tags;}

    public String generateIngredientsString(){
        StringBuilder sb = new StringBuilder();
        for(Ingredient i : this.ingredients){
            sb.append("- " + i.getName() + ": " + i.getQuantityValue()+" " + i.getQuantityUnits()+"\n");
        }
        return sb.toString();
    }

    public Recipe clone(){
        Recipe r = new Recipe(this.getTitle(), this.getAuthor(), this.getDescription(), this.getID());
        r.setThumbnail(this.getThumbnail());
        r.setFilterInfo(this.getFilterInfo());
        r.setPresentation(this.presentation);
        r.setTime(this.time);
        r.setIngredients(this.ingredients);
        return r;
    }

    public static class Icon{
        public final static String ICON_CHANGED = "Recipe.Icon_iconChanged"; // Used with broadcast

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

        public void setDrawable(Drawable d){
            this.image = d;
        }

        public void setTitle(String title){
            this.title = title;
        }
    }


    /**
     * Produce a foundational icon to draw to screen.<br>
     * Be sure when you use this that you use the ImageDownloaderService to download the thumbnail image.
     * @param c
     * @param recipe
     * @return
     */
    public static Icon produceDescriptor(Context c, Recipe recipe){
        return new Recipe.Icon(recipe.getTitle(), null, recipe.getNumFavourites(), recipe.getTime(), recipe.getID());
    }

    public static int tagSimilarity(Recipe recipe1, Recipe recipe2){
        int score = 0;
        for(String s1 : recipe1.getTags()){
            for(String s2 : recipe2.getTags()){
                if(s1.toUpperCase().matches(s2.toUpperCase())){
                    score++;
                }
            }
        }
        return score;
    }

    public static int tagSimilarity(String id1, String id2){
        Recipe r1 = RemoteFileManager.getInstance().getRecipe(id1);
        Recipe r2 = RemoteFileManager.getInstance().getRecipe(id2);
        return tagSimilarity(r1, r2);
    }
}
