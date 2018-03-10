package com.group3.swengandroidapp.XMLRenderer;

import java.util.HashMap;

/**
 * Created by Jack on 11/02/2018.
 */

public class RemoteFileManager {
    private static final RemoteFileManager ourInstance = new RemoteFileManager();
    public static RemoteFileManager getInstance() {
        return ourInstance;
    }

    private static HashMap<String, Recipe> recipes;
    private static HashMap<String, Presentation> presentations;


    private RemoteFileManager() {
        recipes = new HashMap<>();
        presentations = new HashMap<>();
    }

    public void setRecipe(String id, Recipe recipe){
        recipes.put(id,recipe);
    }

    public Recipe getRecipe(String id){
        return recipes.get(id);
    }

    public void setPresentation(String id, Presentation presentation) { presentations.put(id, presentation); }

    public Presentation getPresentation(String id) { return presentations.get(id); }


}
