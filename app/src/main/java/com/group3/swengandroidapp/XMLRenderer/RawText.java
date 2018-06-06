package com.group3.swengandroidapp.XMLRenderer;

import android.app.Activity;

/**
 * Raw text class for parsing into from the PWS
 * Handles any raw text in the PWS
 */

public class RawText extends XmlElement {
    private String text;
    public RawText(XmlElement parent, String text) {
        super(parent);
        this.text = text.replaceAll("\\s+", " ");
    }

    public String getText() {
        return text;
    }

    @Override
    public void draw(Activity activity) {

    }
}
