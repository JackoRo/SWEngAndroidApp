package com.group3.swengandroidapp.XMLRenderer;

import android.app.Activity;

/**
 * Created by Jack on 22/02/2018.
 */

public class Slide extends XmlElement {

    // Must call drawAll again on activity destroy e.g. rotating phone

    public Slide (XmlElement parent) {
        super(parent);
    }

    @Override
    public void draw(Activity activity) {
        for (Drawable item : children) {
            item.draw(activity);
        }
    }
}
