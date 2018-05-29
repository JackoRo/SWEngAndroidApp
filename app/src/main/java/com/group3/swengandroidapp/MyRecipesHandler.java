package com.group3.swengandroidapp;

import java.io.File;
import java.util.ArrayList;

public class MyRecipesHandler {

    private static MyRecipesHandler recipe = new MyRecipesHandler();
    private ArrayList<String> items;


    public static MyRecipesHandler getInstance(){
        return MyRecipesHandler.recipe;
    }

    public void append(String id){
        if(items.contains(id)){
            items.remove(id);
        }
        items.add(0, id);
    }


    public String[] getMyRecipes(){

        File myRecipesFolder=new File("/myRecipes");     // Pathname
        File[] myRecipesList = myRecipesFolder.listFiles();        // Array holding files in folder

        // Get number of recipes
        int numberOfRecipes = 0;
        for (File f: myRecipesList){
            String name = f.getName();
            if (name.endsWith(".xml"))
                numberOfRecipes++;
        }

        String[] fileNames = new String[numberOfRecipes];                  // Array holding file names in folder
        // Get file names in string format and store in array
        int i = 0;
        for(File f: myRecipesList){

            /*
            String path = "/myRecipes/" + f.getName();// to get full path name
            String filename=path.substring(path.lastIndexOf("/")+1);
            */

            fileNames[i] = f.getName();
            i++;
        }

        return fileNames;
    }
}
