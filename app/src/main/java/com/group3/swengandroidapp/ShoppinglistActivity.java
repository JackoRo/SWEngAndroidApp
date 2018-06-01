/**
 * @deprecated
 */

//package com.group3.swengandroidapp;
//
//import android.content.Context;
//import android.content.Intent;
//import android.support.v4.widget.DrawerLayout;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//
//import com.group3.swengandroidapp.XMLRenderer.Ingredient;
//
//import java.io.FileNotFoundException;
//import java.io.PrintWriter;
//import java.lang.reflect.Array;
//import java.util.ArrayList;
//import java.util.Scanner;
//import java.util.regex.*;
//
//public class ShoppinglistActivity extends MainActivity {
//
//    ListView listView;
//    ArrayList<String> arrayList;
//    ArrayAdapter<String> arrayAdapter;
//    String messageText;
//    int position;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.shopping_list_gui_new);
//        super.onCreateDrawer();
//
//        setTitle("Shopping List");
//
//
//        listView = findViewById(R.id.listView);
//        arrayList = new ArrayList<>();
//        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
//        listView.setAdapter(arrayAdapter);
//
//        //When something is clicked on in the list, the EditMessage method is called so that the message can be edited.
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                Intent intent = new Intent();
//                intent.setClass(ShoppinglistActivity.this,EditMessageClass.class);
//                intent.putExtra(IntentConstants.INTENT_MESSAGE_DATA, arrayList.get(position));
//                intent.putExtra(IntentConstants.INTENT_ITEM_POSITION,position);
//                startActivityForResult(intent, IntentConstants.INTENT_REQUEST_CODE_TWO);
//            }
//        });
//
//        //Get items from shopping list handler and convert to string and add to arrayList
//        ShoppinglistHandler shoppingListHandlerObject = ShoppinglistHandler.getInstance();
//        ArrayList<Ingredient> ingredients = shoppingListHandlerObject.getItems();
//        if (ingredients == null){
//            //do nothing
//        }
//        else {
//            for (Ingredient data : ingredients) {
//                StringBuilder sb = new StringBuilder(32);
////                String pattern = data.getName();
////                Boolean isTrue = false;
////                String currentValue = "initialValue";
//
//                sb.append(data.getName() + ", ");
//                sb.append(data.getQuantityValue() + " ");
//                sb.append(data.getQuantityUnits());
//                arrayList.add(sb.toString());
//
////                for (String ing: arrayList) {
////                   Pattern r = Pattern.compile(pattern);
////                   Matcher m = r.matcher(ing);
////                   if (m.find()) {
////                       Pattern s = Pattern.compile("\\d+");
////                       Matcher o = s.matcher(ing);
////                       isTrue = true;
////                       Log.d("ShoppingListActivity:","found! isTrue is true");
////                       if (o.find()){
////                           currentValue = o.group(0);
////                           arrayList.remove(ing);
////                       }else{
////                            currentValue = "";
////                       }
////                   }else{
////                        isTrue = false;
////                       Log.d("ShoppingListActivity:","not found! isTrue is false");
////                   }
////
////                }
////                if (isTrue == true){
////                    sb.append(data.getName() + ", " );
////                    sb.append(data.getQuantityValue() + currentValue);
////                    sb.append(data.getQuantityUnits());
////                    arrayList.add(sb.toString());
////                }else {
////                    sb.append(data.getName() + ", ");
////                    sb.append(data.getQuantityValue() + " ");
////                    sb.append(data.getQuantityUnits());
////                    arrayList.add(sb.toString());
////                }
//
//
//            }
//        }
//
//
//        //This will read the items from the ShoppingList.txt file that we create and will add all of the items to the list
//        //when the app is opened again.
//        try {
//            Scanner sc = new Scanner(openFileInput("ShoppingList.txt"));
//            //Is their something in the file?
//            while(sc.hasNextLine()){
//                //If so, pass the scanner to the next line and pass the data to the string data.
//                String data = sc.nextLine();
//                //Then add the data to the arrayList to show on the shopping list.
//                arrayAdapter.add(data);
//            }
//            sc.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    //When the user exits the application, any data they have left in the shopping list will be saved to a text file called ShoppingList.txt
//    @Override
//    public void onBackPressed(){
//        try{
//            deleteFile("ShoppingList.txt");
//            PrintWriter pw = new PrintWriter(openFileOutput("ShoppingList.txt", Context.MODE_PRIVATE));
//            for(String data : arrayList){
//                pw.println(data);
//            }
//            pw.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        finish();
//    }
//
//    //When the 'add' button is clicked from the shopping list gui, this code will run and send an intent request to the EditField Class.
//    public void onClick(View v){
//        Intent intent = new Intent();
//        intent.setClass(ShoppinglistActivity.this,EditFieldClass.class);
//        startActivityForResult(intent, IntentConstants.INTENT_REQUEST_CODE);
//    }
//
//    //When the clear all button is clicked from the shopping list gui, this code will run and clear the list.
//    public void clearAllButtonClicked(View v){
//        ShoppinglistHandler removeList = ShoppinglistHandler.getInstance();
//        int i=arrayList.size();
//        while(i!=0){
//            arrayList.remove(i-1);
//            i--;
//            arrayAdapter.notifyDataSetChanged();
//            removeList.removeFromArrayList();
//
//        }
//    }
//
//    //After the button has been clicked, the activity is established via request/result code and the appropriate method run.
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        //If it is result code 1, then the user is trying to add some text to the list, so this code should run.
//        if(resultCode== IntentConstants.INTENT_REQUEST_CODE){
//            messageText=data.getStringExtra(IntentConstants.INTENT_MESSAGE_FIELD);
//            arrayList.add(messageText);
//            arrayAdapter.notifyDataSetChanged();
//        }
//        //If it is result code 2, then the user is trying to modify their text, so this code should run.
//        else if(resultCode== IntentConstants.INTENT_REQUEST_CODE_TWO){
//            messageText = data.getStringExtra(IntentConstants.INTENT_CHANGED_MESSAGE);
//            position = data.getIntExtra(IntentConstants.INTENT_ITEM_POSITION,-1);
//            arrayList.remove(position);
//            arrayList.add(position,messageText);
//            arrayAdapter.notifyDataSetChanged();
//        }
//
//        //If it is result code 3, then the user is trying to delete their text, so this code should run.
//        else if(resultCode== IntentConstants.INTENT_REQUEST_CODE_THREE){
//            //noinspection StatementWithEmptyBody
//            if(arrayList.size()==0){
//                //Do Nothing
//            }
//            else{
//                position = data.getIntExtra(IntentConstants.INTENT_ITEM_POSITION,-1);
//                arrayList.remove(position);
//                arrayAdapter.notifyDataSetChanged();
//            }
//        }
//
//        //If it is result code 3, then the user is trying to delete their text, so this code should run.
//        else //noinspection StatementWithEmptyBody
//            if(resultCode== IntentConstants.INTENT_REQUEST_CODE_FOUR) {
//            //Do Nothing
//        }
//    }
//}
