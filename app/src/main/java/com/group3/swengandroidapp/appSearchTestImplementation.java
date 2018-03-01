

/**
 * Created by Alex F on 25/02/2018.
 */


package com.group3.swengandroidapp;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


// this is a test of the search method in basic gui
// recipes is the total collection of recipes
// FoundList is what is output
public class appSearchTestImplementation extends Application {
    ObservableList<String> recipes = FXCollections.observableArrayList();
    ListView<String> FoundList = new ListView<String>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Simple Search");
        Button btn = new Button();
        btn.setText("Choose");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                System.exit(0);
            }
        });

        TextField txt = new TextField();
        txt.setPromptText("Search");
        txt.textProperty().addListener(
                new ChangeListener<Object>() {
                    public void changed(ObservableValue<?> observable,
                                        Object oldVal, Object newVal) {
                        appSearch((String)oldVal, (String)newVal);
                    }
                });

        // Set up the ListView

        // Populate the list's for testing entries - in the actual
        // implementation this will be done with all the recipe names as shown in the for loop

        recipes.add("Lasagne");
        recipes.add("Omelette");
        recipes.add("Crisps");
        recipes.add("Dish Pic");
        recipes.add("Dish Piss");

        /*
        for (int i = 0; i < recipes.length; i++) {
        	// FoundList.setItems( recipes.getName(i) );
		}
		*/

        FoundList.setItems( recipes );



        // basic gui set up for testing

        VBox root = new VBox();
        root.setPadding(new Insets(10,10,10,10));
        root.setSpacing(2);
        root.getChildren().addAll(txt,FoundList,btn);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }


    // the method to search, takes string and returns the list of found items
    // identical as in the appSearch class
    public void recipeSearcher(String oldVal, String newVal) {
        // If the number of characters in the text box is less than last time
        // it must be because the user pressed delete
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