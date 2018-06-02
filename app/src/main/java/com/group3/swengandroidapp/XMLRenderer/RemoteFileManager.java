package com.group3.swengandroidapp.XMLRenderer;

import android.content.Intent;
import android.util.Log;

import com.group3.swengandroidapp.HistoryHandler;
import com.group3.swengandroidapp.MainActivity;
import com.group3.swengandroidapp.PythonClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

/**
 * Created by Jack on 11/02/2018.
 */

public class RemoteFileManager {
    private static final RemoteFileManager ourInstance = new RemoteFileManager();

    public static RemoteFileManager getInstance() {
        return ourInstance;
    }

    public Recipe myRecipe;

    private static HashMap<String, Recipe> recipes;
    private static HashMap<String, Recipe> myRecipes;
    private static HashMap<String, InstructionalVideo> instructionalVideos;
    private static HashMap<String, Presentation> presentations;
    private static HashMap<String, Presentation> myPresentations;
    private static HashMap<String, Integer> suggestions;
    private static HashMap<String, Integer> orderedSuggestions;


    private RemoteFileManager() {
        myRecipes = new HashMap<>();
        recipes = new HashMap<>();
        presentations = new HashMap<>();
        myPresentations = new HashMap<>();
        instructionalVideos = new HashMap<>();
        suggestions = new HashMap<>();
        orderedSuggestions = new HashMap<>();
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

    public String[] getSuggestedRecipes(int size, String[] historyArray){
        int suggestionSize = 6;
        String[] ids = new String[recipes.size()] ;
        String[] histories = historyArray;
        int historySize =size;
        int similarityValue;
        Log.d("history", "recieved history size :" +historySize);
        if (historySize >= 1){
            historySize =1;
        }
        if (historySize == 0){

            Log.d("history", "entered into if history size = 0");
            Log.d("history", "adding all recipes to suggestions");
            int counter = 0;
            for(String key : RemoteFileManager.getInstance().getRecipeList().keySet()){
                ids[counter] = key;
                counter++;
            }
        }else{
            for (int i=0; i<historySize; i++){
                Log.d("history", "entered into else, history size recieved :" +historySize);
                Log.d("history", "adding suggestions based on history");
                String historyId = histories[i];
                for(String key : RemoteFileManager.getInstance().getRecipeList().keySet()){
                    //ids[counter] = key;
                    if (!historyId.equals(key)){
                        //only compare tags to different recipes
                        similarityValue = myRecipe.tagSimilarity(historyId, key);
                        int newSimilarityValue = similarityValue*(historySize-i);                 //weights the similarity value based on
                        //how recently the recipe was viewed
                        suggestions.put(key, newSimilarityValue);
                    }else{
                        suggestions.put(key, 0);
                    }
                }
            }
            orderedSuggestions = sortMapByValues(suggestions);
            Set<String> keys = orderedSuggestions.keySet();
            ids = keys.toArray(new String[suggestionSize]);
        }
        Log.d("history", "returned suggestions");
        return ids;
    }
    private static HashMap sortMapByValues(HashMap<String, Integer> aMap) {

        Set<Entry<String,Integer>> mapEntries = aMap.entrySet();

        // used linked list to sort, because insertion of elements in linked list is faster than an array list.
        List<Entry<String,Integer>> aList = new LinkedList<Entry<String,Integer>>(mapEntries);

        // sorting the List
        Collections.sort(aList, new Comparator<Entry<String,Integer>>() {

            @Override
            public int compare(Entry<String, Integer> ele1,
                               Entry<String, Integer> ele2) {

                return ele2.getValue().compareTo(ele1.getValue());
            }
        });

        // Storing the list into Linked HashMap to preserve the order of insertion.
        HashMap<String,Integer> aMap2 = new LinkedHashMap<String, Integer>();
        for(Entry<String,Integer> entry: aList) {
            aMap2.put(entry.getKey(), entry.getValue());
        }

        return aMap2;
    }

    public void setMyRecipe(String id, Recipe recipe){
        myRecipes.put(id,recipe);
    }

    public Recipe getMyRecipe(String id){
        return myRecipes.get(id);
    }

    public String[] getMyRecipes(){
        String[] ids = new String[myRecipes.size()];
        int counter = 0;
        for(String key : RemoteFileManager.getInstance().getMyRecipeList().keySet()){
            ids[counter] = key;
            counter++;
        }
        return ids;
    }


    public InstructionalVideo getInstructionalVideo(String id) {
        return instructionalVideos.get(id);
    }

    public String[] getInstructionalVideos(){
        String[] ids = new String[instructionalVideos.size()];
        int counter = 0;
        for(String key : RemoteFileManager.getInstance().getInstructionalVideosList().keySet()){
            ids[counter] = key;
            counter++;
        }
        return ids;
    }

    public void setPresentation(String id, Presentation presentation) {
        presentation.setProperty("_ID", id);
        presentations.put(id, presentation);
    }

    public void setMyPresentation(String id, Presentation myPresentation) {
        myPresentation.setProperty("_ID", id);
        myPresentations.put(id, myPresentation);
    }

    public void setInstructionalVideo(String id, InstructionalVideo instructionalVideo) {
        instructionalVideo.setID(id);
        instructionalVideos.put(id, instructionalVideo);
    }

    public Presentation getPresentation(String id) { return presentations.get(id); }

    public Presentation getMyPresentation(String id) { return myPresentations.get(id); }

    public HashMap<String, Recipe> getRecipeList() { return recipes; }

    public HashMap<String, Recipe> getMyRecipeList() { return myRecipes; }

    public HashMap<String, InstructionalVideo> getInstructionalVideosList() { return instructionalVideos; }

}
