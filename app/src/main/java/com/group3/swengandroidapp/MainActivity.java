package com.group3.swengandroidapp;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.app.FragmentManager;
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

import static com.group3.swengandroidapp.R.drawable.hands_off_logo;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mFragmentTitles;

    private String xmlFile;

    @Override
    protected void onStart() {
        super.onStart();

        try {

        }
        catch (Exception e){
            e.printStackTrace() ;
        }

    }


    protected void onCreateDrawer() {


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mTitle = mDrawerTitle = getTitle();
        mFragmentTitles = getResources().getStringArray(R.array.screens_array);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);


        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mFragmentTitles));
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





//    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            // Get extra data included in the Intent
//            String message = intent.getStringExtra("message");
//            Log.d("receiver", "Got message: " + message);
//
//            if (intent.getStringExtra(PythonClient.ACTION) == PythonClient.FETCH_RECIPE) {
//                Intent newIntent = new Intent(context, RecipeSelectionActivity.class);
//                startActivity(newIntent);
//            }
//            else {
//                Log.d("ASDLKA", intent.getStringExtra(PythonClient.ACTION));
//            }
//            //fragmentManager.beginTransaction().replace(presentation.getLayout().getId(),fragment).commit();
//        }
//    };

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
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
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
                this.finish();        // End this activity
                break;
            case 1:  // Favourites
                intent = new Intent();
                intent.setClass(this,FavouriteList.class);                 // Set new activity destination
                startActivityForResult(intent, IntentConstants.INTENT_REQUEST_CODE);            // switch activities
                this.finish();
                break;
            case 2:  // Instructional Videos
                intent = new Intent();
                intent.setClass(this,InstructionalVideoActivity.class);                 // Set new activity destination
                startActivityForResult(intent, IntentConstants.INTENT_REQUEST_CODE);           // switch activities
                this.finish();     // End this activity
                break;
            case 3:  // Shopping List
                intent = new Intent();
                intent.setClass(this,ShoppinglistActivity.class);                 // Set new activity destination
                startActivityForResult(intent, IntentConstants.INTENT_REQUEST_CODE);             // Send intent request and switch activities
                this.finish();     // End this activity
                break;
            case 4:  // History
                intent = new Intent();
                intent.setClass(this,HistoryActivity.class);                 // Set new activity destination
                startActivityForResult(intent, IntentConstants.INTENT_REQUEST_CODE);             // Send intent request and switch activities
                this.finish();     // End this activity
                break;
            case 5: // Settings
                intent = new Intent();
                intent.setClass(this,SettingsActivity.class);                 // Set new activity destination
                startActivityForResult(intent, IntentConstants.INTENT_REQUEST_CODE);             // Send intent request and switch activities
                this.finish();     // End this activity
                break;
            default: // Home
                intent = new Intent();
                intent.setClass(this,HomeActivity.class);                 // Set new activity destination
                startActivityForResult(intent, IntentConstants.INTENT_REQUEST_CODE);           // switch activities
                this.finish();
        }


        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mFragmentTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);

        /* if (position == 7){
            Intent intent = new Intent(MainActivity.this, PythonClient.class);
            intent.putExtra(PythonClient.ACTION,PythonClient.FETCH_RECIPE);
            intent.putExtra(PythonClient.ID,"0000");
            startService(intent);
        } */

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
}
