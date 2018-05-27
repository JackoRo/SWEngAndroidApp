package com.group3.swengandroidapp.XMLRenderer;

import android.content.Intent;
import android.util.Log;

import com.group3.swengandroidapp.MainActivity;
import com.group3.swengandroidapp.PythonClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Jack on 11/02/2018.
 */

public class RemoteFileManager {
    private static final RemoteFileManager ourInstance = new RemoteFileManager();
    public static RemoteFileManager getInstance() {
        return ourInstance;
    }

    private static HashMap<String, Recipe> recipes;
    private static HashMap<String, InstructionalVideo> instructionalVideos;
    private static HashMap<String, Presentation> presentations;


    private RemoteFileManager() {
        recipes = new HashMap<>();
        presentations = new HashMap<>();
        instructionalVideos = new HashMap<>();
    }

    public void setRecipe(String id, Recipe recipe){
        recipes.put(id,recipe);
    }

    public Recipe getRecipe(String id){
        return recipes.get(id);
    }

    public String getRecipeOfTheDay(){
        Random random = new Random();
        String id = new ArrayList<String>(recipes.keySet()).get(random.nextInt(recipes.size()));
        return id;
    }

    public String[] getSuggestedRecipes(){
        String[] ids = new String[recipes.size()];
        int counter = 0;
        for(String key : RemoteFileManager.getInstance().getRecipeList().keySet()){
            ids[counter] = key;
            counter++;
        }
        return ids;
    }

    public InstructionalVideo getInstructionalVideo(String id) {
        return instructionalVideos.get(id);
    }

    public String[] getInstructionalVideo(){
        String[] ids = new String[instructionalVideos.size()];
        int counter = 0;
        for(String key : RemoteFileManager.getInstance().getInstructionalVideosList().keySet()){
            ids[counter] = key;
            counter++;
        }
        return ids;
    }

    public void setPresentation(String id, Presentation presentation) { presentations.put(id, presentation); }

    public Presentation getPresentation(String id) { return presentations.get(id); }

    public HashMap<String, Recipe> getRecipeList() { return recipes; }

    public HashMap<String, InstructionalVideo> getInstructionalVideosList() { return instructionalVideos; }

}
