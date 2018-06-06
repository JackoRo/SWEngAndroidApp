package com.group3.swengandroidapp.ShoppingList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.group3.swengandroidapp.R;

public class EditFieldClass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);
    }

    //Makes sure that when the save button is clicked after clicking the add item button, the message is saved as long as the string is not empty.
    //Also make sure that the quantity is indeed an integer and that the unit is a string.
    public void saveButtonClicked(View v){
        //Set item name.
        String messageText = ((EditText)findViewById(R.id.addMessage)).getText().toString();
        String quantity = ((EditText)findViewById(R.id.addQuantity)).getText().toString();
        String unit = ((EditText)findViewById(R.id.addUnit)).getText().toString();
        //noinspection StatementWithEmptyBody
        if(!messageText.equals("") | !quantity.equals("") | !unit.equals("")){
            //Do nothing
            Intent intent = new Intent();
            intent.putExtra(Intent_Constants.INTENT_MESSAGE_FIELD,messageText);
            intent.putExtra(Intent_Constants.INTENT_ITEM_QUANTITY,quantity);
            intent.putExtra(Intent_Constants.INTENT_ITEM_UNIT,unit);
            setResult(Intent_Constants.INTENT_RESULT_CODE,intent);
            finish();
        }
    }
}