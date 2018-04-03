package com.group3.swengandroidapp.XMLRenderer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;

import com.group3.swengandroidapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Marco on 03/04/2018.
 */

class ImageDownloader extends AsyncTask<Recipe, Integer, Bitmap[]>{
    @Override
    protected Bitmap[] doInBackground(Recipe... recipes){

        Log.d("TEST", "Fetching bitmap");
        Bitmap[] output = new Bitmap[recipes.length];
        int counter = 0;
        for(Recipe r : recipes) {
            Bitmap image = null;
            if (r.getThumbnail().contains("http")) {
                try {
                    // Get HTTP connection
                    URL url = new URL(r.getThumbnail());
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    image = BitmapFactory.decodeStream(input);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // Crop image to be square
            int width = image.getWidth();
            int height = image.getHeight();
            if(width>height){
                int crop = (int)(0.5*(width-height));
                image = Bitmap.createBitmap(image, crop, 0, height, height);
            }else{
                int crop = (int)(0.5*(height-width));
                image = Bitmap.createBitmap(image, 0, crop, width, width);
            }
            output[counter] = image;
            counter++;
        }

        Log.d("TEST", "Returning " + counter + " bitmap(s)");
        return output;
    }
}

