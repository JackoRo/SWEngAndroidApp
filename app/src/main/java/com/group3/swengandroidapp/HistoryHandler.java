package com.group3.swengandroidapp;

import android.widget.MultiAutoCompleteTextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Class to handle the
 * Created by Marco on 24/03/2018.
 */
public class HistoryHandler {
    public static final int MAX_NUMBER_OF_ITEMS = 10;

    private static HistoryHandler history = new HistoryHandler();
    private ArrayList<String> items;

    private HistoryHandler(){
        items = new ArrayList<>(MAX_NUMBER_OF_ITEMS);
    }

    public static HistoryHandler getInstance(){
        return HistoryHandler.history;
    }

    public void append(String id){
        if(items.contains(id)){
            items.remove(id);
        }
        items.add(0, id);
        // Trim off any extra history items
        while(items.size()>MAX_NUMBER_OF_ITEMS){
            items.remove(MAX_NUMBER_OF_ITEMS);
        }
    }

    /**
     * Wipes History items content, and sets it to the passed array
     * @param items
     */
    public void setHistory(String[] items){
        this.items = new ArrayList<>(MAX_NUMBER_OF_ITEMS);
        for(String s : items){
            this.items.add(s);
        }
    }

    public String[] getHistory(){
        if(items.size()==0){
            return null;
        }
        String[] temp = new String[items.size()];
        int i=0;
        for(String s : items){
            temp[i] = s;
            i++;
        }
        return temp;
    }

    public String getItem(int index){
        return items.get(index);
    }

}
