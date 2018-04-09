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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;

/**
 * <p>
 *     Service to handle the requests to download the thumbnail images of recipes.
 * </p>
 * <p>
 *     All downloaded images are temporarily saved to cache memory. To get access,
 *     you need to obtain the absolute file path.
 * </p>
 * <p>
 *     To do this:<br>
 *         1. create a new intent:<br>
 *         <code>
 *              Intent intent = new Intent(this, ImageDownloaderService.class);<br>
 *              intent.setAction(ImageDownloaderService.GET_BITMAP_READY);<br>
 *              intent.putExtra(Recipe.ID, id);<br>
 *              startService(intent);<br>
 *         </code>
 * </p>
 * Created by Marco on 06/04/2018.
 */

public class ImageDownloaderService extends IntentService {

    private final static int NUM_DEFAULT_THUMBNAILS = 5;

    public final static String GET_BITMAP_READY = "ImageDownloaderService.fetchBitmapDrawable";
    public final static String BITMAP_READY = "ImageDownloaderService.bitmapSaved";
    public final static String ABSOLUTE_PATH = "ImageDownloaderService.jpgFilePath";
    private static HashMap<String, SaveFileDescriptor> savedFiles = new HashMap<>(); // Record of all saved bitmaps

    public ImageDownloaderService(){
        super("ImageDownloaderService");
        Log.d("ImageDownloaderService", "[OK] ImageDownloaderService started");
    }

    @Override
    public void onHandleIntent(Intent intent){
        String id = intent.getStringExtra(Recipe.ID);
        Log.d("ImageDownloaderService", "Received request: " + id + ": " + intent.getAction());
        String action = intent.getAction();
        if(action != null){
            switch(action){
                case GET_BITMAP_READY:
                    if(id != null){
                        // Check if it's already downloaded
                        if(!thumbnailNeedsDownloading(id)){
                            sendBitmapSavedMessage(id);
                        }else{
                            // Try downloading the bitmap
                            Boolean temporary = false;
                            Bitmap bitmap = downloadImage(RemoteFileManager.getInstance().getRecipe(id).getThumbnail());
                            if(bitmap == null){
                                Log.d("ImageDownloaderService", "[ER][1] Unable to download bitmap!");
                                // If file doesnt exist, assign a temporary file
                                if(!savedFiles.containsKey(id)){
                                    temporary = true;
                                    // File could not be loaded, load default
                                    Random random = new Random();
                                    int i = random.nextInt(NUM_DEFAULT_THUMBNAILS) + 1;
                                    String filename = "default_thumbnail_" + Integer.toString(i);
                                    int resource = getResources().getIdentifier(filename, "drawable", "com.group3.swengandroidapp");

                                    if(resource > 0){
                                        bitmap = BitmapFactory.decodeResource(getResources(), resource);
                                    }else{
                                        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.thumbnail);
                                    }
                                }else{
                                    sendBitmapSavedMessage(id);
                                }
                            }

                            if(bitmap != null){
                                // Save image
                                try{
                                    saveBitmap(id, bitmap, temporary);
                                    sendBitmapSavedMessage(id);
                                }catch(IOException e){
                                    Log.d("ImageDownloaderService", "[ER][5] Unable to save bitmap");
                                    e.printStackTrace();
                                }
                            }
                        }
                    }else{
                        Log.d("ImageDownloaderService", "[ER][2] Unable to extract destination recipe id!");
                    }
                    break;
                default:
                    break;
            }
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
                // If connection times out, revert to defaul thumbnail.
                connection.setConnectTimeout(500);
                connection.setReadTimeout(500);
                connection.connect();
                image = BitmapFactory.decodeStream(connection.getInputStream());
                connection.disconnect();
            } catch (Exception e) {
                Log.d("ImageDownloaderService", "[ER][6] Error when downloading image!");
                return null;
            }
        }else{
            // Is a file location...
            image = BitmapFactory.decodeFile(url);
        }

        if(image != null){
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
        }

       return image;
    }

    private void sendBitmapSavedMessage(String id){
        Intent intent = new Intent(BITMAP_READY);
        intent.putExtra(Recipe.ID, id);
        intent.putExtra(ImageDownloaderService.ABSOLUTE_PATH, savedFiles.get(id).absolutePath);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
        Log.d("ImageDownloaderService", "Bitmap " + id + "Ready");
    }

    private void saveBitmap(String id, Bitmap bitmap, Boolean temporary) throws IOException{
        File file = File.createTempFile(id, ".jpg");
        FileOutputStream stream = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        stream.flush();

        // Log the existance of the saved file
        savedFiles.put(id, new SaveFileDescriptor(file.getAbsolutePath(), temporary));

        sendBitmapSavedMessage(id);
    }

    /**
     * Use this to extract a bitmap drawable from the cached memory
     * @param absolutePath path of the cached image
     * @return converted bitmap drawable
     */
    public static BitmapDrawable fetchBitmapDrawable(String absolutePath){
        // Try and read from local memory
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return new BitmapDrawable(BitmapFactory.decodeFile(absolutePath, options));
    }

    public Boolean thumbnailNeedsDownloading(String id){
        if(savedFiles.containsKey(id)){
            return savedFiles.get(id).isTemporary;
        }else{
            return true;
        }
    }

    private class SaveFileDescriptor{
        public String absolutePath;
        public boolean isTemporary;

        public SaveFileDescriptor(String path, boolean temporary){
            this.absolutePath = path;
            this.isTemporary = temporary;
        }
    }
}

