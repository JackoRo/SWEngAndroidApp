package com.group3.swengandroidapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.group3.swengandroidapp.XMLRenderer.Recipe;

/**
 * Created by Marco on 08/04/2018.
 */

public abstract class ImageDownloaderListener extends BroadcastReceiver {

    private IntentFilter filter = new IntentFilter();
    private Context context;

    public ImageDownloaderListener(Context context){
        super();
        this.context = context;
        filter.addAction(ImageDownloaderService.BITMAP_READY);
        filter.addAction(Recipe.Icon.ICON_CHANGED);
        LocalBroadcastManager.getInstance(this.context).registerReceiver(this, filter);
    }

    public void destroy(){
        LocalBroadcastManager.getInstance(this.context).unregisterReceiver(this);
    }
}
