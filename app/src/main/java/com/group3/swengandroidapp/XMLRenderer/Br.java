package com.group3.swengandroidapp.XMLRenderer;

import android.app.Activity;

/**
 * BR class for parsing into from the PWS
 * Handles any BR expressions in the PWS as new lines in the text
 */

public class Br extends XmlElement {
    public Br(XmlElement parent) {
        super(parent);
    }

    @Override
    public void draw(Activity activity) {
    }
}
