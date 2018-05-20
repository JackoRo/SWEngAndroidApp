package com.group3.swengandroidapp.SImpLeGraphicsModule;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.LinearLayout;

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

            paint.setColor(Color.parseColor(getColor()));
            paint.setStrokeWidth(Float.valueOf(getStroke()));

            if (getType() == "ellipse") {
                canvas.drawOval(getX(),getY(),getX2(),getY2(),paint);
            }
            else if (getType() == "rectangle") {
                canvas.drawRect(getX(),getY(),getX2(),getY2(),paint);
            }
            else if (getType() == "line") {
                canvas.drawLine(getX(),getY(),getX2(),getY2(),paint);
            }


        }

    }

}
