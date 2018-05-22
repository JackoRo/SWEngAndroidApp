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
    private ArrayList<Shape> shapes = new ArrayList<Shape>();

    public CanvasView(Context context) {
        super(context);
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public ArrayList<Shape> getShapes() {
        return shapes;
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    @Override
    public void onDraw(Canvas canvas) {

        for (Shape shape : shapes) {

            Float x1 = Float.valueOf(shape.getX1());
            Float y1 = Float.valueOf(shape.getY1());
            Float x2 = Float.valueOf(shape.getX2());
            Float y2 = Float.valueOf(shape.getY2());

            if (shape.getType().equals("ellipse")) {
                Log.d("Ellipse", "x1 = " + x1 + " y1 = " + y1 + " x2 = " + x2 + " y2 = " + y2);
                canvas.drawOval(x1, y1, x2, y2, paint);
            } else if (shape.getType().equals("rectangle")) {
                Log.d("Rectangle", "x1 = " + x1 + " y1 = " + y1 + " x2 = " + x2 + " y2 = " + y2);
                canvas.drawRect(x1, y1, x2, y2, paint);
            } else if (shape.getType().equals("line")) {
                Log.d("Line", "x1 = " + x1 + " y1 = " + y1 + " x2 = " + x2 + " y2 = " + y2);
                canvas.drawLine(x1, y1, x2, y2, paint);
            }

        }
    }
}
