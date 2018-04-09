package com.group3.swengandroidapp.XMLRenderer;

import android.app.Activity;
import android.widget.LinearLayout;

/**
 * Created by Jack on 22/02/2018.
 */

public class Slide extends XmlElement {

    private LinearLayout linearLayout;

    // Must call drawAll again on activity unRegister e.g. rotating phone

    public Slide (XmlElement parent) {
        super(parent);

    }

    public LinearLayout getLayout() {
        return linearLayout;
    }

    @Override
    public void draw(Activity activity) {

        // Set layout of the slide
        if (parent instanceof Presentation) {
            linearLayout = ((Presentation) parent).getLayout();

            activity.setContentView(linearLayout);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            for (Drawable item : children) {
                item.draw(activity);
            }
        }

    }
}
