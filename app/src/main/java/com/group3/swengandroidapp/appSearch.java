package com.group3.swengandroidapp;

/**
 * Created by Me on 01/03/2018.
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class appSearch {

    static ObservableList<String> recipes = FXCollections.observableArrayList();
    static ListView<String> FoundList = new ListView<String>();

    public static void main(String[] args) {

    }

    public void recipeSearcher(String oldVal, String newVal) {

        // If the number of characters in the text box is less than last time
        // it must be because the user removed a char in the string

        if ( oldVal != null && (newVal.length() < oldVal.length()) ) {
            // Restore the lists original set of entries
            // and start from the beginning

            FoundList.setItems( recipes );
        }

        // Change to upper case to get rid of possible errors
        newVal = newVal.toUpperCase();

        // Remove recipes the user has not searched for
        ObservableList<String> subentries = FXCollections.observableArrayList();
        for ( Object entry: FoundList.getItems() ) {
            String entryText = (String)entry;
            if ( entryText.toUpperCase().contains(newVal) ) {
                subentries.add(entryText);
            }
        }
        FoundList.setItems(subentries);
    }
}
