package com.group3.swengandroidapp.XMLRenderer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.widget.ImageViewCompat;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.bumptech.glide.Glide;
import com.group3.swengandroidapp.PythonClient;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;


/**
 * Created by Jack on 05/03/2018.
 */

public class ImageAndroid extends Image {

    public ImageAndroid(XmlElement parent) {
        super(parent);
    }

    @Override
    public void draw(Activity activity) {

        if (parent instanceof Slide) {

            LinearLayout layout = ((Slide) parent).getLayout();
            ImageView image = new ImageView(activity);
            String urlString;

            try {
                new URL(getPath());
                urlString = getPath();
            }
            catch (Exception e) {
                urlString = "http://"+ PythonClient.IP_ADDR + ":5000/download/presentation/" + getInheritableProperty("_ID") + "/" + getPath();
            }

            int x1 = Integer.valueOf(getX1());
            int x2 = Integer.valueOf(getX2());
            int y1 = Integer.valueOf(getY1());
            int y2 = Integer.valueOf(getY2());
//            if (x2 * 20 > layout.getWidth()) {
//                x2 = 500;
//            }
//            if (y2 * 50 > layout.getHeight()) {
//                y2 = 500;
//            }
//            if (x1 * 20 > layout.getWidth()) {
//                x1 = 250;
//            }
//            if (y1 * 20 > layout.getHeight()) {
//                y1 = 250;
//            }

            LayoutParams layoutParams=new LayoutParams(x2, y2);
            layoutParams.setMargins(x1, y1, x2, y2);
            image.setLayoutParams(layoutParams);

            // Load image via Glide lib using context
            Glide.with(activity)
                    .load(urlString)
                    .into(image);

            layout.addView(image);

        }

    }


}
