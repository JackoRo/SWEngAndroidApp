package com.group3.swengandroidapp.XMLRenderer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Jack on 21/05/2018.
 */

public class CanvasView extends View {

    private Paint paint;
    private Float x1;
    private Float y1;
    private Float x2;
    private Float y2;
    private String type;

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

    @Override
    public void onDraw(Canvas canvas) {

        if (type.equals("ellipse")) {
            canvas.drawOval(x1, y1, x2, y2, paint);
        }
        else if (type.equals("rectangle")) {
            canvas.drawRect(x1, y1, x2, y2, paint);
        }
        else if (type.equals("line")) {
            canvas.drawLine(x1, y1, x2, y2, paint);
        }

    }
}
