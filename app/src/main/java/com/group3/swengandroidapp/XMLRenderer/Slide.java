package com.group3.swengandroidapp.XMLRenderer;

import android.app.Activity;
import android.graphics.Canvas;
import android.widget.LinearLayout;

import com.group3.swengandroidapp.SImpLeGraphicsModule.GraphicModuleAndroid;

/**
 * Created by Jack on 22/02/2018.
 */

public class Slide extends XmlElement {

    private LinearLayout linearLayout;
    private Canvas canvas = new Canvas();
    private CanvasView canvasView;

    // Must call drawAll again on activity unRegister e.g. rotating phone

    public Slide (XmlElement parent) {
        super(parent);

    }

    public String getAdvert () {
        return getInheritableProperty("advert");
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
            canvasView = new CanvasView(activity);

            for (Drawable item : children) {

                if (item instanceof GraphicModuleAndroid) {
                    ((GraphicModuleAndroid) item).setCanvas(canvas);
                    ((GraphicModuleAndroid) item).setCanvasView(canvasView);
                }

                item.draw(activity);

            }

        }

    }
}
