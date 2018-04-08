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

    public abstract void onBitmapReady(String id, String filePath);
    public void onIconChanged(String id){}

    public void destroy(){
        LocalBroadcastManager.getInstance(this.context).unregisterReceiver(this);
    }
}
