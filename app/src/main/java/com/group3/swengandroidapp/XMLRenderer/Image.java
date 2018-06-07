package com.group3.swengandroidapp.XMLRenderer;

import android.app.Activity;

/**
 * Image class for parsing into from the PWS
 */

public abstract class Image extends XmlElement implements Drawable {

    public Image(XmlElement parent) {
        super(parent);
    }

    public String  getX1(){
        return getInheritableProperty("x1");
    }

    public void setX1(String x1) {
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

    public String getPath() { return getInheritableProperty("path"); }

    public void setPath(String path) { setProperty("path", path); }

    public String  getDuration(){
        return getProperty("duration");
    }

    public void setDuration(String duration) {
        setProperty("duration", duration);
    }

}
