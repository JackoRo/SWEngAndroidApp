package com.group3.swengandroidapp.ShoppingList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.group3.swengandroidapp.R;

public class EditMessageClass extends AppCompatActivity {

    String messageText;
    String quantity;
    String units;
    int position;

    //This makes sure that when editing the message, the existing version of the message is overwritten with the new message.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_item);
        Intent intent = getIntent();
        messageText = intent.getStringExtra(Intent_Constants.INTENT_MESSAGE_DATA);
        quantity = intent.getStringExtra(Intent_Constants.INTENT_ITEM_QUANTITY);
        units = intent.getStringExtra(Intent_Constants.INTENT_ITEM_UNIT);
        position = intent.getIntExtra(Intent_Constants.INTENT_ITEM_POSITION,-1);

        //Make sure that the user can see what they had previously written when they are editing it.
        EditText messageData = findViewById(R.id.editMessage);
        messageData.setText(messageText);
        EditText quantityData = findViewById(R.id.editQuantity);
        quantityData.setText(quantity);
        EditText unitData = findViewById(R.id.editUnit);
        unitData.setText(units);
    }

    //If the user is trying to save their edited message when they clicked on it in the list, run this code.
    public void saveButtonClicked(View v){
        String changedMessageText = ((EditText)findViewById(R.id.editMessage)).getText().toString();
        String changedQuantity = ((EditText)findViewById(R.id.editQuantity)).getText().toString();
        String changedUnits = ((EditText)findViewById(R.id.editUnit)).getText().toString();
        Intent intent = new Intent();
        intent.putExtra(Intent_Constants.INTENT_CHANGED_MESSAGE,changedMessageText);
        intent.putExtra(Intent_Constants.INTENT_CHANGED_QUANTITY,changedQuantity);
        intent.putExtra(Intent_Constants.INTENT_CHANGED_UNITS,changedUnits);
        intent.putExtra(Intent_Constants.INTENT_ITEM_POSITION,position);
        setResult(Intent_Constants.INTENT_RESULT_CODE_TWO,intent);
        finish();
    }

    //If the user is trying to delete their message when they clicked on it in the list, run this code.
    public void deleteButtonClicked(View v){
        Intent intent = new Intent();
        intent.putExtra(Intent_Constants.INTENT_ITEM_POSITION,position);
        setResult(Intent_Constants.INTENT_RESULT_CODE_THREE,intent);
        finish();
    }
}