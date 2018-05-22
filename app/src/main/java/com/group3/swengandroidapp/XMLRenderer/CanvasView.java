package com.group3.swengandroidapp.XMLRenderer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Jack on 21/05/2018.
 */

public class CanvasView extends View {

    private Paint paint;
    private float x1;
    private float y1;
    private float x2;
    private float y2;
    private String type;
    private ArrayList<RectF> canvasshapes;

    public CanvasView(Context context) {
        super(context);
    }

    public Float getX1() {
        return x1;
    }

    public void setX1(Float x1) {
        this.x1 = x1;
    }

    public Float getY1() {
        return y1;
    }

    public void setY1(Float y1) {
        this.y1 = y1;
    }

    public Float getX2() {
        return x2;
    }

    public void setX2(Float x2) {
        this.x2 = x2;
    }

    public Float getY2() {
        return y2;
    }

    public void setY2(Float y2) {
        this.y2 = y2;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void assessType() {
        if (type.equals("ellipse")) {
            Log.d("Ellipse","x1 = " + x1 + " y1 = " + y1 + " x2 = " + x2 + " y2 = " + y2);
            canvasshapes.add(new RectF(x1,y1,x2,y2));
        }
        else if (type.equals("rectangle")) {
            Log.d("Rectangle","x1 = " + x1 + " y1 = " + y1 + " x2 = " + x2 + " y2 = " + y2);
            canvasshapes.add(new RectF(x1,y1,x2,y2));
        }
//        else if (type.equals("line")) {
//            Log.d("Line","x1 = " + x1 + " y1 = " + y1 + " x2 = " + x2 + " y2 = " + y2);
//            canvas.drawLine(x1, y1, x2, y2, paint);
//        }
    }

    @Override
    public void onDraw(Canvas canvas) {

        for (int i = 0; i < canvasshapes.size(); i++) {

            if (type.equals("ellipse")) {
                Log.d("Ellipse", "x1 = " + x1 + " y1 = " + y1 + " x2 = " + x2 + " y2 = " + y2);
                canvas.drawOval(canvasshapes.get(i), paint);
            } else if (type.equals("rectangle")) {
                Log.d("Rectangle", "x1 = " + x1 + " y1 = " + y1 + " x2 = " + x2 + " y2 = " + y2);
                canvas.drawRect(canvasshapes.get(i), paint);
            } else if (type.equals("line")) {
                Log.d("Line", "x1 = " + x1 + " y1 = " + y1 + " x2 = " + x2 + " y2 = " + y2);
                canvas.drawLine(x1, y1, x2, y2, paint);
            }

        }
    }
}
