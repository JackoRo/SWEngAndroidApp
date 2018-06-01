package com.group3.swengandroidapp.ShoppingList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.group3.swengandroidapp.MainActivity;
import com.group3.swengandroidapp.R;
import com.group3.swengandroidapp.ShoppinglistHandler;
import com.group3.swengandroidapp.XMLRenderer.Ingredient;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/*This is the shopping list code that runs the main GUI for the shopping list. Developed by Alex Bennett using references from;
    https://www.youtube.com/watch?v=3QHgJnPPnqQ&t=1545s
    https://developer.android.com/guide/topics/ui/declaring-layout#AdapterViews
    https://robgibbens.com/androids-built-in-list-item-layouts/*/

@SuppressWarnings("StatementWithEmptyBody")
public class ShoppinglistActivity extends MainActivity
{
    //Listviews
    ListView listViewName;
    ListView listViewQuantity;
    ListView listViewUnit;

    //Main Arraylist containing all of the objects.
    ArrayList<listItem> arrayListForShopping = new ArrayList<>();
    //ArrayList<String> arrayList;

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

        //Get items from shopping list handler and add to arrayList
        ShoppinglistHandler shoppingListHandlerObject = ShoppinglistHandler.getInstance();
        ArrayList<Ingredient> ingredients = shoppingListHandlerObject.getItems();

        //Set ListViews for each of the lists.
        //This listview is for the name display list.
        listViewName = findViewById(R.id.listViewName);
        arrayAdapterName = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemNames);
        listViewName.setAdapter(arrayAdapterName);
        //noinspection StatementWithEmptyBody
        if(arrayListForShopping.size()== 0)
        {
            //Do nothing
        }
        else
        {
            int j = 0;
            while (j != arrayListForShopping.size())
            {
                itemNames.add(arrayListForShopping.get(j).getName());
                j++;
            }
            arrayAdapterName.notifyDataSetChanged();
        }

        //This listview is for the quantity display list.
        listViewQuantity = findViewById(R.id.listViewQuantity);
        arrayAdapterQuantity = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemQuantities);
        listViewQuantity.setAdapter(arrayAdapterQuantity);
        //noinspection StatementWithEmptyBody
        if(arrayListForShopping.size() != 0)
        {
            int j = 0;
            while (j != arrayListForShopping.size())
            {
                itemQuantities.add(String.valueOf(arrayListForShopping.get(j).getQuantity()));
                j++;
            }
            arrayAdapterQuantity.notifyDataSetChanged();
        }

        //This listview is for the unit display list.
        listViewUnit =  findViewById(R.id.listViewUnit);
        arrayAdapterUnit = new ArrayAdapter<>(this, android.R.layout.simple_list_item_checked, itemUnits);
        listViewUnit.setAdapter(arrayAdapterUnit);
        listViewUnit.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        //noinspection StatementWithEmptyBody
        if(arrayListForShopping.size()== 0)
        {
            //Do nothing
        }
        else
        {
            int j = 0;
            while (j != arrayListForShopping.size())
            {
                itemUnits.add(arrayListForShopping.get(j).getUnits());
                j++;
            }
            arrayAdapterUnit.notifyDataSetChanged();
        }

        //When an item in the list in clicked on, the EditMessage method is called so that the message can be edited.
        listViewName.setOnItemClickListener((adapterView, view, position, l) ->
        {
            Intent intent = new Intent();
            intent.setClass(ShoppinglistActivity.this,EditMessageClass.class);
            intent.putExtra(Intent_Constants.INTENT_MESSAGE_DATA, arrayListForShopping.get(position).getName());
            intent.putExtra(Intent_Constants.INTENT_ITEM_QUANTITY, arrayListForShopping.get(position).getQuantity());
            intent.putExtra(Intent_Constants.INTENT_ITEM_UNIT, arrayListForShopping.get(position).getUnits());
            intent.putExtra(Intent_Constants.INTENT_ITEM_POSITION,position);
            startActivityForResult(intent,Intent_Constants.INTENT_REQUEST_CODE_TWO);
        });

        //When an items checkbox is clicked on, this method is called.
        /* This method is currently unused but has been left in in case needed in the future.
        listViewUnit.setOnItemClickListener((adapterView, view, position, l) ->
        {

        });
        */

        //This will read the items from the ShoppingList.txt file that we create and will add all of the items to the list
        //when the app is opened again.
        try
        {
            Scanner sc1 = new Scanner(openFileInput("ShoppingListItemNames.txt"));
            Scanner sc2 = new Scanner(openFileInput("ShoppingListItemQuantities.txt"));
            Scanner sc3 = new Scanner(openFileInput("ShoppingListItemUnits.txt"));
            Scanner sc4 = new Scanner(openFileInput("ShoppingListItemChecked.txt"));
            //Is their something in the file?
            while(sc1.hasNextLine())
            {
                //If so, pass the scanner to the next line and pass the data to the string data.
                String data = sc1.nextLine();
                String data2 = sc2.nextLine();
                String data3 = sc3.nextLine();
                //Then add the data to the arrayList to show on the shopping list.
                itemNames.add(data);
                itemQuantities.add(data2);
                itemUnits.add(data3);
                arrayAdapterName.notifyDataSetChanged();
                arrayAdapterQuantity.notifyDataSetChanged();
                arrayAdapterUnit.notifyDataSetChanged();
            }
            sc1.close();
            sc2.close();
            sc3.close();

            //Used to reapply the checked items after opening.
            String data4 = sc4.nextLine();
            sc4.close();

            //This is a real hack to make this work. There is most likely a better way but for now it is just this.
            //Doing it this way limits the amount of items this will work for. Currently to 15.
            if (data4.contains("0=true"))
            {
                listViewUnit.setItemChecked(0, true);
                arrayAdapterUnit.notifyDataSetChanged();
            }
            if (data4.contains("1=true"))
            {
                listViewUnit.setItemChecked(1, true);
                arrayAdapterUnit.notifyDataSetChanged();
            }
            if (data4.contains("2=true"))
            {
                listViewUnit.setItemChecked(2, true);
                arrayAdapterUnit.notifyDataSetChanged();
            }
            if (data4.contains("3=true"))
            {
                listViewUnit.setItemChecked(3, true);
                arrayAdapterUnit.notifyDataSetChanged();
            }
            if (data4.contains("4=true"))
            {
                listViewUnit.setItemChecked(4, true);
                arrayAdapterUnit.notifyDataSetChanged();
            }
            if (data4.contains("5=true"))
            {
                listViewUnit.setItemChecked(5, true);
                arrayAdapterUnit.notifyDataSetChanged();
            }
            if (data4.contains("6=true"))
            {
                listViewUnit.setItemChecked(6, true);
                arrayAdapterUnit.notifyDataSetChanged();
            }
            if (data4.contains("7=true"))
            {
                listViewUnit.setItemChecked(7, true);
                arrayAdapterUnit.notifyDataSetChanged();
            }
            if (data4.contains("8=true"))
            {
                listViewUnit.setItemChecked(8, true);
                arrayAdapterUnit.notifyDataSetChanged();
            }
            if (data4.contains("9=true"))
            {
                listViewUnit.setItemChecked(9, true);
                arrayAdapterUnit.notifyDataSetChanged();
            }
            if (data4.contains("10=true"))
            {
                listViewUnit.setItemChecked(10, true);
                arrayAdapterUnit.notifyDataSetChanged();
            }
            if (data4.contains("11=true"))
            {
                listViewUnit.setItemChecked(11, true);
                arrayAdapterUnit.notifyDataSetChanged();
            }
            if (data4.contains("12=true"))
            {
                listViewUnit.setItemChecked(12, true);
                arrayAdapterUnit.notifyDataSetChanged();
            }
            if (data4.contains("13=true"))
            {
                listViewUnit.setItemChecked(13, true);
                arrayAdapterUnit.notifyDataSetChanged();
            }
            if (data4.contains("14=true"))
            {
                listViewUnit.setItemChecked(14, true);
                arrayAdapterUnit.notifyDataSetChanged();
            }

            //This is used to make sure that Item objects are created along with all of the other arrays being filled up.
            int j=itemNames.size();
            while (j!=0)
            {
                listItem item = new listItem(itemNames.get(j-1), itemQuantities.get(j-1), itemUnits.get(j-1));
                arrayListForShopping.add(item);
                j--;
            }

            //This is the code to add ingredients to the shopping list from a recipe or other page.
            if (ingredients != null)
            {
                for (Ingredient data : ingredients)
                {
                    String name = data.getName();
                    String value = Integer.toString(data.getQuantityValue());
                    String unit = data.getQuantityUnits();
                    
                    listItem ingredientsItem = new listItem(name, value, unit);
                    itemNames.add(name);
                    itemQuantities.add(value);
                    itemUnits.add(unit);
                    arrayListForShopping.add(ingredientsItem);
                }
                shoppingListHandlerObject.removeFromArrayList();
            }
            update();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    //This updates the shopping list every time it is called which will pack items together if they are the same as each other
    public void update()
    {
        //adds together similar items
        for (int i =0; i<itemNames.size(); i++)
        {
            String name = itemNames.get(0);
            for (int j =0; j<itemNames.size(); j++)
            {
                String otherNames = itemNames.get(j);
                if (name.equals(otherNames))
                {
                    Integer index1 = 0;
                    Integer index2 = j;
                    if (index1!=index2)
                    {
                        String unit1 = itemUnits.get(index1);
                        String unit2 = itemUnits.get(index2);
                        if (unit1.equals(unit2))
                        {
                            Integer value1 = Integer.parseInt(itemQuantities.get(index1));
                            Integer value2 = Integer.parseInt(itemQuantities.get(index2));
                            Integer updatedInteger = value1+value2;
                            String updatedValue = Integer.toString(updatedInteger);

                            listItem newIngredientsItem = new listItem(name, updatedValue, unit1);
                            arrayListForShopping.add(newIngredientsItem);
                            itemNames.add(name);
                            itemQuantities.add(updatedValue);
                            itemUnits.add(unit1);

                            arrayListForShopping.remove(0);
                            itemNames.remove(0);
                            itemQuantities.remove(0);
                            itemUnits.remove(0);

                            arrayListForShopping.remove(j-1);
                            itemNames.remove(j-1);
                            itemQuantities.remove(j-1);
                            itemUnits.remove(j-1);

                            arrayAdapterName.notifyDataSetChanged();
                            arrayAdapterQuantity.notifyDataSetChanged();
                            arrayAdapterUnit.notifyDataSetChanged();
                        }
                    }
                }
            }
        }
    }


    //When the user exits the application, any data they have left in the shopping list will be saved to a text file called ShoppingList.txt
    @Override
    public void onPause()
    {
        super.onPause();
        update();

        try
        {
        //Delete old files.
        deleteFile("ShoppingListItemNames.txt");
        deleteFile("ShoppingListItemQuantities.txt");
        deleteFile("ShoppingListItemUnits.txt");
        deleteFile("ShoppingListItemChecked.txt");

        //Write new files with the contents of the arrays.
        PrintWriter pw1 = new PrintWriter(openFileOutput("ShoppingListItemNames.txt", Context.MODE_PRIVATE));
        PrintWriter pw2 = new PrintWriter(openFileOutput("ShoppingListItemQuantities.txt", Context.MODE_PRIVATE));
        PrintWriter pw3 = new PrintWriter(openFileOutput("ShoppingListItemUnits.txt", Context.MODE_PRIVATE));
        PrintWriter pw4 = new PrintWriter(openFileOutput("ShoppingListItemChecked.txt", Context.MODE_PRIVATE));

        int i=0;
        while (i!=(itemNames.size()))
        {
            pw1.println(itemNames.get(i));
            pw2.println(itemQuantities.get(i));
            pw3.println(itemUnits.get(i));
            i++;
        }
        pw1.close();
        pw2.close();
        pw3.close();

        //Used to reapply the checked items after opening.
        pw4.println(listViewUnit.getCheckedItemPositions());
        pw4.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    //When the 'add' button is clicked from the shopping list gui, this code will run and send an intent request to the EditField Class.
    public void onClick(View v)
    {
        Intent intent = new Intent();
        intent.setClass(ShoppinglistActivity.this,EditFieldClass.class);
        startActivityForResult(intent,Intent_Constants.INTENT_REQUEST_CODE);
    }

    //When the clear all button is clicked from the shopping list gui, this code will run and clear the list.
    public void clearAllButtonClicked(View v)
    {
            arrayListForShopping.clear();
            itemNames.clear();
            itemQuantities.clear();
            itemUnits.clear();
            arrayAdapterName.notifyDataSetChanged();
            arrayAdapterQuantity.notifyDataSetChanged();
            arrayAdapterUnit.notifyDataSetChanged();
    }

    //When the clear all button is clicked from the shopping list gui, this code will run and clear the list.
    public void clearCheckedButtonClicked(View v)
    {
        SparseBooleanArray checkedItemPositions = listViewUnit.getCheckedItemPositions();
        int i = arrayListForShopping.size();
        while(i!=0)
        {
            checkedState = checkedItemPositions.get(i-1);
            if(checkedState)
            {
                arrayListForShopping.remove(i-1);
                itemNames.remove(i-1);
                itemQuantities.remove(i-1);
                itemUnits.remove(i-1);
                arrayAdapterName.notifyDataSetChanged();
                arrayAdapterQuantity.notifyDataSetChanged();
                arrayAdapterUnit.notifyDataSetChanged();
            }
            i--;
        }
        checkedItemPositions.clear();
    }

    //After the button has been clicked, the activity is established via request/result code and the appropriate method run.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
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
            update();
        }
        //If it is result code 2, then the user is trying to save their edited text, so this code should run.
        else if(resultCode==Intent_Constants.INTENT_REQUEST_CODE_TWO)
        {
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
            update();
        }
        //If it is result code 3, then the user is trying to delete their list item, so this code should run.
        else if(resultCode==Intent_Constants.INTENT_REQUEST_CODE_THREE)
        {
            position = data.getIntExtra(Intent_Constants.INTENT_ITEM_POSITION, -1);
            //noinspection StatementWithEmptyBody
            if (arrayListForShopping.size() == 0)
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
                update();
            }
        }
    }
}