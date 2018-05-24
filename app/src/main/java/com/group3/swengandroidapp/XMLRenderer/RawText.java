package com.group3.swengandroidapp.XMLRenderer;

import android.app.Activity;

/**
 * Created by Jack on 25/02/2018.
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
