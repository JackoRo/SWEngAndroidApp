package com.group3.swengandroidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditFieldClass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);
    }

    //Makes sure that when the save button is clicked, the message is saved as long as the string is not empty.
    public void saveButtonClicked(View v){
        String messageText = ((EditText)findViewById(R.id.addMessage)).getText().toString();
        //noinspection StatementWithEmptyBody
        if(messageText.equals("")){

        }
        else{
            Intent intent = new Intent();
            intent.putExtra(Intent_Constants.INTENT_MESSAGE_FIELD,messageText);
            setResult(Intent_Constants.INTENT_RESULT_CODE,intent);
            finish();
        }
    }
}
