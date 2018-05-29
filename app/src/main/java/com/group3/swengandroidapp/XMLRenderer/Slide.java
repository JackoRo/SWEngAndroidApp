package com.group3.swengandroidapp.XMLRenderer;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;

import com.group3.swengandroidapp.SImpLeGraphicsModule.GraphicModuleAndroid;

/**
 * Created by Jack on 22/02/2018.
 */

public class Slide extends XmlElement {

    private LinearLayout linearLayout;
    private Canvas canvas = new Canvas();
    private CanvasView canvasView;
    private Handler handler;

    // Must call drawAll again on activity unRegister e.g. rotating phone

    public Slide (XmlElement parent) {
        super(parent);
        this.handler = new Handler(Looper.getMainLooper());

    }

    public String getAdvert () {
        return getInheritableProperty("advert");
    }

    public LinearLayout getLayout() {
        return linearLayout;
    }

    private void removeShape(GraphicModuleAndroid item) {
        item.getCanvasView().removeShape(item);
        item.getCanvasView().invalidate();
    }

    private void removeImage(ImageAndroid item) {
        item.getImage().setVisibility(View.GONE);
    }

    private void removeText(TextAndroid item) {
        item.getTextView().setVisibility(View.GONE);
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

                if (item instanceof GraphicModuleAndroid) {
                    if (((GraphicModuleAndroid) item).getDuration() != null) {
                        handler.postDelayed(() -> removeShape((GraphicModuleAndroid) item), (Integer.valueOf(((GraphicModuleAndroid) item).getDuration()) * 1000));
                    }
                }
                else if (item instanceof ImageAndroid) {
                    if (((ImageAndroid) item).getDuration() != null) {
                        handler.postDelayed(() -> removeImage((ImageAndroid) item), (Integer.valueOf(((ImageAndroid) item).getDuration()) * 1000));
                    }
                }
                else if (item instanceof TextAndroid) {
                    if (((TextAndroid) item).getDuration() != null) {
                        handler.postDelayed(() -> removeText((TextAndroid) item), (Integer.valueOf(((TextAndroid) item).getDuration()) * 1000));
                    }
                }

            }

        }

    }
}
