package com.group3.swengandroidapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.group3.swengandroidapp.XMLRenderer.Recipe;

/**
 * A simplified listener for listening to broadcasts sent from ImageDownloaderService.
 * You can also optionally override the <code>public void onIconChanged()</code> method too.
 * Created by Marco on 08/04/2018.
 */
public abstract class ImageDownloaderListener extends BroadcastReceiver {

    private Context context;

    /**
     * Creates and registers a new reciever that specifically listens for the {@link ImageDownloaderService#BITMAP_READY}
     * message issued by the {@link ImageDownloaderService} class.
     * <p>
     *     When a bitmap is ready for fetching, the {@link ImageDownloaderListener#onBitmapReady(String, String)} method is called.
     * </p>
     * @param context application context for the receiver
     */
    public ImageDownloaderListener(Context context){
        super();
        this.context = context;
        IntentFilter filter = new IntentFilter();
        filter.addAction(ImageDownloaderService.BITMAP_READY);
        filter.addAction(Recipe.Icon.ICON_CHANGED);
        LocalBroadcastManager.getInstance(this.context).registerReceiver(this, filter);
    }

    @Override
    public void onReceive(Context context, Intent intent){
        String id = intent.getStringExtra(Recipe.ID);
        if(id != null){
            String action = intent.getAction();
            if(action != null){
                switch(action){
                    case ImageDownloaderService.BITMAP_READY:
                        String absolutePath = intent.getStringExtra(ImageDownloaderService.ABSOLUTE_PATH);
                        Log.d("ImageDownloaderListener", "Received file path: " + absolutePath);
                        if(absolutePath != null){
                            onBitmapReady(id, absolutePath);
                        }
                        break;
                    case Recipe.Icon.ICON_CHANGED:
                        onIconChanged(id);
                        break;
                }
            }
        }
    }

    /**
     * This method is called every time the ImageDownloaderService broadcasts a BITMAP_READY message.
     * No null checking is needed.
     * <p>
     *     Once this method is called, you can use:<br>
     *     <code>
     *         BitmapDrawable {@link ImageDownloaderService#fetchBitmapDrawable(String)}
     *     </code><br>
     *     to fetch the image.
     * </p>
     * @param id id of the recipe whos bitmap is ready
     * @param filePath absolute path of the file.
     */
    public abstract void onBitmapReady(String id, String filePath);

    /**
     * This method is optionally overrideable (does nothing otherwise), and is called whenever an
     * {@link Recipe.Icon#ICON_CHANGED} message is broadcasted.
     * <p>
     *     This is used in {@link HomeActivity}
     *     to synchronize changes between two seperate views.
     * </p>
     * @param id id of recipe icon that has changed.
     */
    public void onIconChanged(String id){}

    /**
     * Un-register the receiver from the {@link LocalBroadcastManager}.
     */
    public void unRegister(){
        LocalBroadcastManager.getInstance(this.context).unregisterReceiver(this);
    }
}
