package com.group3.swengandroidapp;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.group3.swengandroidapp.XMLRenderer.Recipe;
import com.group3.swengandroidapp.XMLRenderer.RemoteFileManager;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Service to handle the requests to download the thumbnail images of recipes
 * Created by Marco on 06/04/2018.
 */

public class ImageDownloaderService extends IntentService {

    public final static String DOWNLOAD_TO_ICON = "ImageDownloaderService_downloadToIcon";
    public final static String DOWNLOAD_TO_RECIPE = "ImageDownloaderService_downloadToRecipe";
    public final static String RECIPE_ID = "ImageDownloaderService_recipeID";
    public final static String DOWNLOAD_COMPLETE = "ImageDownloaderService_downloadComplete";
    public final static String TARGET_ICON = "ImageDownloaderService_TargetIcon";
    public final static String ID = "ImageDownloaderService_recipeID";

    public ImageDownloaderService(){
        super("ImageDownloaderService");
        Log.d("ImageDownloaderService", "[OK] ImageDownloaderService started");
    }

    @Override
    public void onHandleIntent(Intent intent){
        switch(intent.getAction()){
            case DOWNLOAD_TO_ICON:
                Recipe.Icon destination = (Recipe.Icon) intent.getSerializableExtra(TARGET_ICON);
                if(destination != null){
                    destination.updateThumbnail(new BitmapDrawable(getResources(),
                            downloadImage(RemoteFileManager.getInstance().getRecipe(destination.getId()).getThumbnail())));
                    sendMessage(destination.getId());
                }else{
                    Log.d("ImageDownloaderService", "[ER][1] Unable to extract destination icon!");
                }
            case DOWNLOAD_TO_RECIPE:
            default:
                String id = intent.getStringExtra(RECIPE_ID);
                if(id == null){
                    id = intent.getDataString();
                }
                if(id != null){
                    Recipe r = RemoteFileManager.getInstance().getRecipe(id);
                    if(r.getThumbnailBitmap() == null){
                        Log.d("ImageDownloaderService", "[OK] Downloading image for Recipe #" + r.getID() + ": " + r.getTitle());
                        r.setThumbnailBitmap(downloadImage(r.getThumbnail()));
                        sendMessage(id);    // Broadcast message notifying the completion of the download
                    }
                }else{
                    Log.d("ImageDownloaderService", "[ER][2] Unable to extract destination recipe id!");
                }

                break;
        }
    }

    /**
     * Download image from given destination. Can handle URLs as well as file paths.
     * If the image is not found, it will return the default "Thumbnail" icon found in
     * the drawable section of Resources.
     * @param url filepath of the image (URL or local)
     * @return Downloaded bitmap (null if not found)
     */
    private Bitmap downloadImage(String url){

        if(url == null){
            return null;
        }

        // STAGE 1: Import image
        Bitmap image = null;

        // If thumbnail location is URL, connect and download
        if (url.contains("http")) {
            try {
                // Get HTTP connection
                URL address = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) address.openConnection();
                connection.setDoInput(true);
                connection.connect();
                image = BitmapFactory.decodeStream(connection.getInputStream());
                connection.disconnect();
            } catch (Exception e) {}
        }else{
            // Is a file location...
            image = BitmapFactory.decodeFile(url);
        }

        if(image == null){
            // File could not be loaded, load default
            image = BitmapFactory.decodeResource(getResources(), R.drawable.thumbnail);
        }else{
            // STAGE 2: Image processing
            // If Image successfully imported, scale and crop

            // STAGE 2.1: Crop
            int width = image.getWidth();
            int height = image.getHeight();
            // Crop image based on dimensions
            if(width>height){
                int start = (int)(0.5*(width-height));
                image = Bitmap.createBitmap(image, start, 0, height, height);
            }else{
                int start = (int)(0.5*(height-width));
                image = Bitmap.createBitmap(image, 0, start, width, width);
            }

            // STAGE 2.2: Resize
            // Scale image to size used by recyclerview (saves memory & improves performance)
            height = image.getHeight(); // update height (incase the crop changed it)
            if(height > 250){
                image = Bitmap.createScaledBitmap(image, 250, 250, true);
            }
            Log.d("ImageDownloader", "Successfully processed image");
        }
       return image;
    }

    /**
     * Broadcasts message containing information about recipe thumbnails that have been updated
     * @param id
     */
    private void sendMessage(String id) {
        Intent intent = new Intent(DOWNLOAD_COMPLETE);
        intent.putExtra(ID, id);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }
}

