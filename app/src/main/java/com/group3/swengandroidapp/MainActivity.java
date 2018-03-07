package com.group3.swengandroidapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.group3.swengandroidapp.XMLRenderer.Presentation;
import com.group3.swengandroidapp.XMLRenderer.PresentationManager;


//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.web.client.RestTemplate;

public class MainActivity extends AppCompatActivity {

    private String xmlFile;
    //private XmlParser xmlParser;

    /*
    private CustomerService customerServiceTest;
    private Customer customerTest = new Customer();
    */

    @Override
    protected void onStart() {
        super.onStart();
        //createServerExample();
//        new HttpRequestTask().execute();

        try {

        }
        catch (Exception e){
            e.printStackTrace() ;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Context thisContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //listenButtons();
        /*add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((ToggleButton) view).isChecked()) {
                    DisplayToast("Toggle button is On");
                }
                else{
                    DisplayToast("Toggle button is Off");
                }
            }
        });*/

        /*view_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayToast("You have clicked the Save button");
                //viewFavourites();
            }
        });*/



    //}

    /*public void listenButtons(){
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((ToggleButton) view).isChecked()) {
                    DisplayToast("Toggle button is On");
                }
                else{
                    DisplayToast("Toggle button is Off");
                }
            }
        });

        view_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DisplayToast("You have clicked the Save button");
                viewFavourites();
            }
        });
    }*/


    /*
    public void addFavourite() {
       Recipe recipe1 = new Recipe("Crisps", "user1", "This is a recipe for crisps.", 1);
       addToFavourites(recipe1);

    }

    protected void addToFavourites(Recipe recipe){

        ArrayList<Recipe> arrayRecipe = new ArrayList<Recipe>();

        arrayRecipe.add(recipe);

    }*/



/*
    public void viewFavourites() {
        //Call command to pass arraylist through to other activity.


        //Creates an instance of the Intent class-the way android switches between activities.
        //Tells you to watch the class FavouriteList.
        Intent view_f = new Intent(this, FavouriteList.class);
        //Next, the code that launches an activity.
        startActivity(view_f);
    }

    private void DisplayToast(String msg) {
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
    }*/
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            Log.d("receiver", "Got message: " + message);

            String xmlFile = PresentationManager.getInstance().getXML();
            Presentation presentation = PresentationManager.getInstance().getPresentation();

            presentation.draw(MainActivity.this);


            //TextView greetingContentText = (TextView) findViewById(R.id.content_value);
            //greetingContentText.setText(presentation.getMeta("author"));
            //greetingContentText.setText("After");

        }
    };

    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //new HttpRequestTask().execute();

            return true;
        }




        return super.onOptionsItemSelected(item);
    }

    /*
    private void createServerExample(){

        new serverGrizzly();
        customerServiceTest = new CustomerService();

    }*/

    /*private class HttpRequestTask extends AsyncTask<Void, Void, Greeting> {
        @Override
        protected Greeting doInBackground(Void... params) {
            try {
                final String url = "http://rest-service.guides.spring.io/greeting";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Greeting greeting = restTemplate.getForObject(url, Greeting.class);
                return greeting;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Greeting greeting) {
            TextView greetingContentText = (TextView) findViewById(R.id.content_value);
            greetingContentText.setText(greeting.getContent());
        }

    }*/
}

