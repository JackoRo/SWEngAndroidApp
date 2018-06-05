package com.group3.swengandroidapp.XMLRenderer;

import android.app.Activity;
import android.text.SpannableStringBuilder;

/**
 * Interface to define drawable objects for the presentation viewer
 */

public interface Drawable {
    public void draw(Activity activity);
    public void buildString(SpannableStringBuilder stringBuilder, Activity activity);
}
