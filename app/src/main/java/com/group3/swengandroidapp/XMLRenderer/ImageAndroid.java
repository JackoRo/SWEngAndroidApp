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

import com.bumptech.glide.Glide;

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

            // Load image via Glide lib using context
            Glide.with(activity)
                    .load(getPath())
                    .into(image);

            layout.addView(image);

        }

    }


}
