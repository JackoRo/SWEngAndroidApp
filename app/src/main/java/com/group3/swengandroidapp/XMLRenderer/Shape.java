package com.group3.swengandroidapp.XMLRenderer;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Shape class for parsing into from the PWS
 */

public abstract class Shape extends XmlElement implements Drawable {

    private Paint strokePaint;
    private Paint fillPaint;
    // Regex used to determine a union of two colours in the paint attribute
    private static Pattern pattern = Pattern.compile("gradient\\((#.{6}),(#.{6})\\)");

    public Shape(XmlElement parent) {
        super(parent);
    }

    public String  getX1(){
        return getInheritableProperty("x1");
    }

    public void setX1(String x1){
        setProperty("x1", x1);
    }

    public String  getY1(){
        return getInheritableProperty("y1");
    }

    public void setY1(String y1) {
        setProperty("y1", y1);
    }

    public String  getX2(){
        return getInheritableProperty("x2");
    }

    public void setX2(String x2) {
        setProperty("x2", x2);
    }

    public String  getY2(){
        return getInheritableProperty("y2");
    }

    public void setY2(String y2) {
        setProperty("y2", y2);
    }

    public String  getColor(){
        return getInheritableProperty("color");
    }

    public void setColor(String color) {
        setProperty("color", color);
    }

    public String  getFill(){
        return getInheritableProperty("fill");
    }

    public void setFill(String fill) {
        setProperty("fill", fill);
    }

    public String getType() {
        return getInheritableProperty("type");
    }

    public void setType(String type) {
        setProperty("type", type);
    }

    public String getStroke() {
        return getInheritableProperty("stroke");
    }

    public void setStroke(String stroke) {
        setProperty("stroke", stroke);
    }

    public String getDuration() {
        return getProperty("duration");
    }

    public void setDuration(String duration) {
        setProperty("duration", duration);
    }

    public Paint getFillPaint() {
        if (fillPaint == null) {
            fillPaint = new Paint();

            Matcher matcher = pattern.matcher(getFill());

            if (matcher.find()) {
                fillPaint.setShader(new LinearGradient(Float.valueOf(getX1()),
                        Float.valueOf(getY1()),
                        Float.valueOf(getX2()),
                        Float.valueOf(getY2()),
                        Color.parseColor(matcher.group(1)),
                        Color.parseColor(matcher.group(2)),
                        TileMode.REPEAT));
                Log.d("Gradient Fill Color", matcher.group(1) + " " + matcher.group(2));
            } else {
                fillPaint.setColor(Color.parseColor(getFill()));
                Log.d("Fill Color", getFill());
            }

            fillPaint.setStyle(Style.FILL);
            fillPaint.setStrokeWidth(Float.valueOf(getStroke()));
        }
        return fillPaint;
    }

    public Paint getStrokePaint() {
        if (strokePaint == null) {
            strokePaint = new Paint();
            strokePaint.setColor(Color.parseColor(getColor()));
            Log.d("Stroke Color", getColor());
            strokePaint.setStyle(Style.STROKE);
            strokePaint.setStrokeWidth(Float.valueOf(getStroke()));
        }
        return strokePaint;
    }

}
