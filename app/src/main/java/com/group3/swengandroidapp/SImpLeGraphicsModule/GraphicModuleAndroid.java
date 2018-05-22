package com.group3.swengandroidapp.SImpLeGraphicsModule;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.LinearLayout;

import com.group3.swengandroidapp.XMLRenderer.CanvasView;
import com.group3.swengandroidapp.XMLRenderer.Shape;
import com.group3.swengandroidapp.XMLRenderer.Slide;
import com.group3.swengandroidapp.XMLRenderer.XmlElement;

/**
 * Created by Jack on 20/05/2018.
 */

public class GraphicModuleAndroid extends Shape{

    public GraphicModuleAndroid(XmlElement parent) {
        super(parent);
    }

    @Override
    public void draw(Activity activity) {

        if (parent instanceof Slide) {
            LinearLayout layout = ((Slide) parent).getLayout();
            Canvas canvas = new Canvas();
            Paint paint = new Paint();
            CanvasView canvasView = new CanvasView(activity);

            paint.setColor(Color.parseColor(getColor()));
            paint.setStrokeWidth(Float.valueOf(getStroke()));

            canvasView.setPaint(paint);
            canvasView.setX1(Float.valueOf(getX1()));
            canvasView.setY1(Float.valueOf(getY1()));
            canvasView.setX2(Float.valueOf(getX2()));
            canvasView.setY2(Float.valueOf(getY2()));
            canvasView.setType(getType());

            canvasView.onDraw(canvas);
            canvasView.setBackgroundColor(Color.TRANSPARENT);

            layout.addView(canvasView);
        }

    }

}
