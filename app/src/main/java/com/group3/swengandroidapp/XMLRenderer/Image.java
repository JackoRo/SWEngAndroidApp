package com.group3.swengandroidapp.XMLRenderer;

import android.app.Activity;

/**
 * Created by Jack on 22/02/2018.
 */

public class Image extends XmlElement implements Drawable {

    public Image(XmlElement parent) {
        super(parent);
    }

    public String  getX(){
        return getInheritableProperty("x");
    }

    public void setX(String x) {
        setProperty("x", x);
    }


    public String  getY(){
        return getInheritableProperty("y");
    }

    public void setY(String y) {
        setProperty("y", y);
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

    @Override
    public void draw(Activity activity) {


    }
}
