package com.group3.swengandroidapp.ShoppingList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.group3.swengandroidapp.MainActivity;
import com.group3.swengandroidapp.R;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/*This is the shopping list code that runs the main GUI for the shopping list. Developed by Alex Bennett using references from;
    https://www.youtube.com/watch?v=3QHgJnPPnqQ&t=1545s
    https://developer.android.com/guide/topics/ui/declaring-layout#AdapterViews
    https://robgibbens.com/androids-built-in-list-item-layouts/*/

public class ShoppinglistActivity extends MainActivity {

    //Listviews
    ListView listViewName;
    ListView listViewQuantity;
    ListView listViewUnit;

    //Main Arraylist containing all of the objects.
    ArrayList<listItem> arrayListForShopping = new ArrayList<>();

    //Arraylists and adapters for the Item names display list.
    ArrayList<String> itemNames = new ArrayList<>();
    ArrayAdapter <String> arrayAdapterName;

    //Arraylists and adapters for the Item Quantities display list.
    ArrayList<String> itemQuantities = new ArrayList<>();
    ArrayAdapter <String> arrayAdapterQuantity;

    //Arraylists and adapters for the Item Units display list.
    ArrayList<String> itemUnits = new ArrayList<>();
    ArrayAdapter <String> arrayAdapterUnit;

    //Other variables
    String messageText;
    int position;
    String quantity = "1";
    String unit = "default";
    boolean checkedState;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_list_gui_new);
        super.onCreateDrawer();
        setTitle(getString(R.string.shopping_list_name));

        //Set ListViews for each of the lists.
        //This listview is for the name display list.
        listViewName = findViewById(R.id.listViewName);
        arrayAdapterName = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemNames);
        listViewName.setAdapter(arrayAdapterName);
        //noinspection StatementWithEmptyBody
        if(arrayListForShopping.size()== 0) {//Do nothing
        }
        else {
            int j = 0;
            while (j != arrayListForShopping.size()+1) {
                itemNames.add(arrayListForShopping.get(j).getName());
                j++; }
            arrayAdapterName.notifyDataSetChanged();}


        //This listview is for the quantity display list.
        listViewQuantity = findViewById(R.id.listViewQuantity);
        arrayAdapterQuantity = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemQuantities);
        listViewQuantity.setAdapter(arrayAdapterQuantity);
        //noinspection StatementWithEmptyBody
        if(arrayListForShopping.size()== 0) {//Do nothing
        }
        else {
            int j = 0;
            while (j != arrayListForShopping.size()+1) {
                itemQuantities.add(String.valueOf(arrayListForShopping.get(j).getQuantity()));
                j++;}
            arrayAdapterQuantity.notifyDataSetChanged();}


        //This listview is for the unit display list.
        listViewUnit =  findViewById(R.id.listViewUnit);
        arrayAdapterUnit = new ArrayAdapter<>(this, android.R.layout.simple_list_item_checked, itemUnits);
        listViewUnit.setAdapter(arrayAdapterUnit);
        listViewUnit.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        //noinspection StatementWithEmptyBody
        if(arrayListForShopping.size()== 0) {//Do nothing
        }
        else {
            int j = 0;
            while (j != arrayListForShopping.size()+1) {
                itemUnits.add(arrayListForShopping.get(j).getUnits());
                j++;}
            arrayAdapterUnit.notifyDataSetChanged();}


        //When an item in the list in clicked on, the EditMessage method is called so that the message can be edited.
        listViewName.setOnItemClickListener((adapterView, view, position, l) -> {
            Intent intent = new Intent();
            intent.setClass(ShoppinglistActivity.this,EditMessageClass.class);
            intent.putExtra(Intent_Constants.INTENT_MESSAGE_DATA, arrayListForShopping.get(position).getName());
            intent.putExtra(Intent_Constants.INTENT_ITEM_QUANTITY, arrayListForShopping.get(position).getQuantity());
            intent.putExtra(Intent_Constants.INTENT_ITEM_UNIT, arrayListForShopping.get(position).getUnits());
            intent.putExtra(Intent_Constants.INTENT_ITEM_POSITION,position);
            startActivityForResult(intent,Intent_Constants.INTENT_REQUEST_CODE_TWO);
        });

        //When an items checkbox is clicked on, this method is called.
        listViewUnit.setOnItemClickListener((adapterView, view, position, l) -> {
            SparseBooleanArray checkedItemPositions = listViewUnit.getCheckedItemPositions();
            checkedState = checkedItemPositions.get(position);
            //If the button if currently off, the user must want to check the item off the list, so do that and visa versa.
            if(checkedState)
            {
                //arrayListForShopping.get(position).setName();
                System.out.println("Changed state to TRUE");
                itemNames.remove(position);
                itemNames.add(position, arrayListForShopping.get(position).getName());
                itemQuantities.remove(position);
                itemQuantities.add(position, String.valueOf(arrayListForShopping.get(position).getQuantity()));
                itemUnits.remove(position);
                itemUnits.add(position, arrayListForShopping.get(position).getUnits());
                arrayAdapterName.notifyDataSetChanged();
                arrayAdapterQuantity.notifyDataSetChanged();
                arrayAdapterUnit.notifyDataSetChanged();
            }
            else
            {
                //arrayListForShopping.get(position).setName();
                System.out.println("Changed state to FALSE");
                itemNames.remove(position);
                itemNames.add(position, arrayListForShopping.get(position).getName());
                itemQuantities.remove(position);
                itemQuantities.add(position, String.valueOf(arrayListForShopping.get(position).getQuantity()));
                itemUnits.remove(position);
                itemUnits.add(position, arrayListForShopping.get(position).getUnits());
                arrayAdapterName.notifyDataSetChanged();
                arrayAdapterQuantity.notifyDataSetChanged();
                arrayAdapterUnit.notifyDataSetChanged();
            }
        });

/* Not working at the moment.
        //This will read the items from the ShoppingList.txt file that we create and will add all of the items to the list
        //when the app is opened again.
        try {
            Scanner sc = new Scanner(openFileInput("ShoppingList.txt"));
            //Is their something in the file?
            while(sc.hasNextLine()){
                //If so, pass the scanner to the next line and pass the data to the string data.
                String data = sc.nextLine();
                //Then add the data to the arrayList to show on the shopping list.
                listItem item = new listItem(data, 2 , kg, false);
                arrayAdapter.add(item);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
    }

    /* Also not working at the moment.
        //When the user exits the application, any data they have left in the shopping list will be saved to a text file called ShoppingList.txt
        @Override
        public void onBackPressed(){
            try{
                deleteFile("ShoppingList.txt");
                PrintWriter pw = new PrintWriter(openFileOutput("ShoppingList.txt", Context.MODE_PRIVATE));
                for(listItem items : arrayListForShopping){
                    pw.println(items);
                }
                pw.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            finish();
        }
    */
    //When the 'add' button is clicked from the shopping list gui, this code will run and send an intent request to the EditField Class.
    public void onClick(View v){
        Intent intent = new Intent();
        intent.setClass(ShoppinglistActivity.this,EditFieldClass.class);
        startActivityForResult(intent,Intent_Constants.INTENT_REQUEST_CODE);
    }

    //When the clear all button is clicked from the shopping list gui, this code will run and clear the list.
    public void clearAllButtonClicked(View v){
        int i=arrayListForShopping.size();
        while(i!=0){
            arrayListForShopping.remove(i-1);
            itemNames.remove(i-1);
            itemQuantities.remove(i-1);
            itemUnits.remove(i-1);
            arrayAdapterName.notifyDataSetChanged();
            arrayAdapterQuantity.notifyDataSetChanged();
            arrayAdapterUnit.notifyDataSetChanged();
            i--;
        }
    }

    //After the button has been clicked, the activity is established via request/result code and the appropriate method run.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //If it is result code 1, then the user is trying to add some text to the list, so this code should run.
        if(resultCode==Intent_Constants.INTENT_REQUEST_CODE)
        {
            messageText = data.getStringExtra(Intent_Constants.INTENT_MESSAGE_FIELD);
            quantity = data.getStringExtra(Intent_Constants.INTENT_ITEM_QUANTITY);
            unit = data.getStringExtra(Intent_Constants.INTENT_ITEM_UNIT);
            listItem item = new listItem(messageText, quantity, unit);
            arrayListForShopping.add(item);
            itemNames.add(messageText);
            itemQuantities.add(quantity);
            itemUnits.add(unit);
            arrayAdapterName.notifyDataSetChanged();
            arrayAdapterQuantity.notifyDataSetChanged();
            arrayAdapterUnit.notifyDataSetChanged();
        }
        //If it is result code 2, then the user is trying to save their edited text, so this code should run.
        else if(resultCode==Intent_Constants.INTENT_REQUEST_CODE_TWO){
            //Find the specific object in the arraylist and change it to the new values.
            position = data.getIntExtra(Intent_Constants.INTENT_ITEM_POSITION,-1);
            messageText = data.getStringExtra(Intent_Constants.INTENT_CHANGED_MESSAGE);
            quantity = data.getStringExtra(Intent_Constants.INTENT_CHANGED_QUANTITY);
            unit = data.getStringExtra(Intent_Constants.INTENT_CHANGED_UNITS);

            //Set the new changes
            arrayListForShopping.get(position).setName(messageText);
            arrayListForShopping.get(position).setQuantity(quantity);
            arrayListForShopping.get(position).setUnits(unit);
            itemNames.remove(position);
            itemNames.add(position, arrayListForShopping.get(position).getName());
            itemQuantities.remove(position);
            itemQuantities.add(position, String.valueOf(arrayListForShopping.get(position).getQuantity()));
            itemUnits.remove(position);
            itemUnits.add(position, arrayListForShopping.get(position).getUnits());
            arrayAdapterName.notifyDataSetChanged();
            arrayAdapterQuantity.notifyDataSetChanged();
            arrayAdapterUnit.notifyDataSetChanged();
        }

        //If it is result code 3, then the user is trying to delete their list item, so this code should run.
        else if(resultCode==Intent_Constants.INTENT_REQUEST_CODE_THREE)
        {
            position = data.getIntExtra(Intent_Constants.INTENT_ITEM_POSITION,-1);
            //noinspection StatementWithEmptyBody
            if(arrayListForShopping.size()==0)
            {
                //Do Nothing
            }
            else
            {
                arrayListForShopping.remove(position);
                itemNames.remove(position);
                itemQuantities.remove(position);
                itemUnits.remove(position);
                arrayAdapterName.notifyDataSetChanged();
                arrayAdapterQuantity.notifyDataSetChanged();
                arrayAdapterUnit.notifyDataSetChanged();
            }
        }
    }
}