package com.group3.swengandroidapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.group3.swengandroidapp.XMLRenderer.Recipe;
import com.group3.swengandroidapp.XMLRenderer.RemoteFileManager;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mFragmentTitles;


    @Override
    protected void onCreate(Bundle savedBundleInstance){
        super.onCreate(savedBundleInstance);
        //Load recipes from server if the list of recipes is empty
        if(RemoteFileManager.getInstance().getRecipeList().isEmpty()) {
            LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                    new IntentFilter("XML-event-name"));

            Intent intent = new Intent(MainActivity.this, PythonClient.class);
            intent.putExtra(PythonClient.ACTION,PythonClient.LOAD_ALL);
            startService(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    protected void onCreateDrawer() {
        //final Context thisContext = this;
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

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

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mTitle = mDrawerTitle = getTitle();
        mFragmentTitles = getResources().getStringArray(R.array.screens_array);
        mDrawerList = findViewById(R.id.left_drawer);


        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_list_item, mFragmentTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Set title of drawer
        getSupportActionBar().setTitle(mTitle);

        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerLayout.addDrawerListener(mDrawerToggle);



    }

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
     } */
     /* view_button.setOnClickListener(new View.OnClickListener() {
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
        //Tells you to watch the class FavouriteListActivity.
        Intent view_f = new Intent(this, FavouriteListActivity.class);
        //Next, the code that launches an activity.
        startActivity(view_f);
    }

    private void DisplayToast(String msg) {
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
    }*/



    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            Log.d("receiver", "Got message: " + message);

            //Fetches all the recipes from the server
            if (intent.getStringExtra(PythonClient.ACTION).matches(PythonClient.LOAD_ALL)) {
                Toast toast = Toast.makeText(context, "Recipes loaded!",Toast.LENGTH_LONG);
                toast.show();
            }
            else {
                Log.d("MainActivity:", "BroadcastReceiver: Error on receive" + intent.getStringExtra(PythonClient.ACTION));
            }
        }
    };

    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        //LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_resource, menu);



        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
            case R.id.action_menu:
                Intent intent = new Intent();
                intent.setClass(this,SearchpageActivity.class);                 // Set new activity destination
                startActivityForResult(intent, IntentConstants.INTENT_REQUEST_CODE);            // switch activities
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /** Swaps activities*/
    private void selectItem(int position) {

        // update the main content by replacing fragments
        Intent intent;


        switch(position) {
            case 0:  // Home
                intent = new Intent();
                intent.setClass(this,HomeActivity.class);                 // Set new activity destination
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  // Delete previous activities
                startActivityForResult(intent, IntentConstants.INTENT_REQUEST_CODE);            // switch activities

                break;
            case 1:  // FavouritesActivity
                intent = new Intent();
                intent.setClass(this,FavouritesActivity.class);                 // Set new activity destination
                startActivityForResult(intent, IntentConstants.INTENT_REQUEST_CODE);            // switch activities

                break;
            case 2:  // Instructional Videos
                intent = new Intent();
                intent.setClass(this,InstructionalVideoActivity.class);                 // Set new activity destination
                startActivityForResult(intent, IntentConstants.INTENT_REQUEST_CODE);           // switch activities

                break;
            case 3:  // Shopping List
                intent = new Intent();
                intent.setClass(this,ShoppinglistActivity.class);                 // Set new activity destination
                startActivityForResult(intent, IntentConstants.INTENT_REQUEST_CODE);             // Send intent request and switch activities

                break;
            case 4:  // My Recipes

                break;
            case 5:  // History
                intent = new Intent();
                intent.setClass(this,HistoryActivity.class);                 // Set new activity destination
                startActivityForResult(intent, IntentConstants.INTENT_REQUEST_CODE);             // Send intent request and switch activities

                break;
            case 6: // Settings
                intent = new Intent();
                intent.setClass(this,SettingsActivity.class);                 // Set new activity destination
                startActivityForResult(intent, IntentConstants.INTENT_REQUEST_CODE);             // Send intent request and switch activities

                break;
            default: // Home
                intent = new Intent();
                intent.setClass(this,HomeActivity.class);                 // Set new activity destination
                startActivityForResult(intent, IntentConstants.INTENT_REQUEST_CODE);           // switch activities

        }


        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mFragmentTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public void onResume(){
        super.onResume();
        // Override the transition animation between activities
        overridePendingTransition(0,0);
    }

    @Override
    public void onPause(){
        super.onPause();
        // Override the transition animation between activities
        overridePendingTransition(0,0);
    }

    //TODO: Move method so it can be accessed by the search page activity

    /**
     * <p>
     *     Send a request to {@link ImageDownloaderService} to get the saved thumbnail ready. (if it's
     *     not yet downloaded, will download first, then notify ready).
     * </p>
     * <p>
     *     When thumbnail is ready to load, an intent is broadcasted:<br>
     *         - Action: {@link ImageDownloaderService#BITMAP_READY}<br>
     *         - String Extra: {@link Recipe#ID} - ID of the recipe <br>
     *         - String Extra: {@link ImageDownloaderService#ABSOLUTE_PATH} - absolute path of the saved image
     * </p>
     * <p>
     *     It is reccommended to use an instance of {@link ImageDownloaderListener} to listen for
     *     the broadcast.
     * </p>
     * @param id id of recipe whos thumbnail you want
     */
    public void requestBitmapFile(String id){
        Intent downloadreq = new Intent(this, ImageDownloaderService.class);
        downloadreq.setAction(ImageDownloaderService.GET_BITMAP_READY);
        downloadreq.putExtra(Recipe.ID, id);
        startService(downloadreq);
    }
}
