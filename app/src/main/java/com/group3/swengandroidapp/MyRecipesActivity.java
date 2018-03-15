package com.group3.swengandroidapp;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.group3.swengandroidapp.XMLRenderer.Image;

/**
 * Created by Kevin on 12/03/2018.
 */

public class MyRecipesActivity extends MainActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recipes);
        super.onCreateDrawer();

        // Make grid for the vertical scroll
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        // Re-focus the default slider position to the top
        gridview.setFocusable(false);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(MyRecipesActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });


        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("XML-event-name"));

        setTitle("My Recipes");

        /*
        ImageButton imagebuttonExample = (ImageButton)findViewById(R.id.imageButton);
        imagebuttonExample.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PythonClient.class);
                intent.putExtra(PythonClient.ACTION,PythonClient.FETCH_RECIPE);
                intent.putExtra(PythonClient.ID,"0000");
                startService(intent);
            }
        });
        */
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            Log.d("receiver", "Got message: " + message);

            if (intent.getStringExtra(PythonClient.ACTION) == PythonClient.FETCH_RECIPE) {
                Intent newIntent = new Intent(context, RecipeSelectionActivity.class);
                startActivity(newIntent);
            }
            else {
                Log.d("ASDLKA", intent.getStringExtra(PythonClient.ACTION));
            }
        }
    };

    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(
                        (int)mContext.getResources().getDimension(R.dimen.thumbnail_width),
                        (int)mContext.getResources().getDimension(R.dimen.thumbnail_height)));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageResource(mThumbIds[position]);
            return imageView;
        }

        // references to our images
        private Integer[] mThumbIds = {
                R.drawable.mac_and_cheese, R.drawable.mac_and_cheese,
                R.drawable.mac_and_cheese, R.drawable.mac_and_cheese,
                R.drawable.mac_and_cheese, R.drawable.mac_and_cheese,
                R.drawable.mac_and_cheese, R.drawable.mac_and_cheese,

        };
    }

}
