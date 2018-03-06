package com.group3.swengandroidapp.XMLRenderer;

import android.app.Activity;
import android.text.SpannableStringBuilder;

/**
 * Created by Jack on 22/02/2018.
 */

public interface Drawable {
    public void draw(Activity activity);
    public void buildString(SpannableStringBuilder stringBuilder, Activity activity);
}
