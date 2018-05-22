package com.group3.swengandroidapp.XMLRenderer;

import android.app.Activity;

/**
 * Created by Jack on 24/02/2018.
 */

public abstract class Shape extends XmlElement implements Drawable {

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
}
