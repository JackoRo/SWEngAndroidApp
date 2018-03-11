package com.group3.swengandroidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditMessageClass extends AppCompatActivity {

    String messageText;
    int position;

    //This makes sure that when editing the message, the existing version of the message is overwritten with the new message.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);
        Intent intent = getIntent();
        messageText = intent.getStringExtra(IntentConstants.INTENT_MESSAGE_DATA);
        position = intent.getIntExtra(IntentConstants.INTENT_ITEM_POSITION,-1);
        EditText messageData = findViewById(R.id.addMessage);
        messageData.setText(messageText);
    }

    public void saveButtonClicked(View v){
        String changedMessageText = ((EditText) findViewById(R.id.addMessage)).getText().toString();
        Intent intent = new Intent();
        intent.putExtra(IntentConstants.INTENT_CHANGED_MESSAGE,changedMessageText);
        intent.putExtra(IntentConstants.INTENT_ITEM_POSITION,position);
        setResult(IntentConstants.INTENT_RESULT_CODE_TWO,intent);
        finish();
    }

    public void deleteButtonClicked(View v){
        Intent intent = new Intent();
        intent.putExtra(IntentConstants.INTENT_ITEM_POSITION,position);
        setResult(IntentConstants.INTENT_RESULT_CODE_THREE,intent);
        finish();
    }
}
