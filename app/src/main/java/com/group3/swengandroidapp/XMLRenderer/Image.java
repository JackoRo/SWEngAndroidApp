package com.group3.swengandroidapp.XMLRenderer;

import android.app.Activity;

/**
 * Created by Jack on 22/02/2018.
 */

public class Image extends XmlElement implements Drawable {

    public Image(XmlElement parent) {
        super(parent);
    }

    @Override
    public void draw(Activity activity) {
        // do some imagey things.
        /* E.G.
        // Some existing RelativeLayout from your layout xml
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.my_relative_layout);

        ImageView iv = new ImageView(this);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(30, 40);
        params.leftMargin = 50;
        params.topMargin = 60;
        rl.addView(iv, params);
        */
    }
}
