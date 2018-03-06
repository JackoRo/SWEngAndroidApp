package com.group3.swengandroidapp.XMLRenderer;

/**
 * Created by Jack on 22/02/2018.
 */

public abstract class Text extends XmlElement implements Drawable {

    public Text(XmlElement parent) {
        super(parent);
    }

    public String getFont(){
        return getInheritableProperty("font");
    }

    public void setFont(String font){
        setProperty("font", font);
    }

    public String getTextSize(){
        return getInheritableProperty("textsize");
    }

    public void setTextSize(String textsize){
        setProperty("textsize", textsize);
    }

    public String getItalic(){
        return getInheritableProperty("italic");
    }

    public void setItalic(String  italic){
        setProperty("italic", italic);
    }

    public String  getBold(){
        return getInheritableProperty("bold");
    }

    public void setBold(String bold) {
        setProperty("bold", bold);
    }

    public String getUnderline(){
        return getInheritableProperty("underline");
    }

    public void setUnderline(String underline) {
        setProperty("underline", underline);
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

    public String  getStart(){
        return getInheritableProperty("start");
    }

    public void setStart(String start) {
        setProperty("start", start);
    }

    public String  getDuration(){
        return getInheritableProperty("duration");
    }

    public void setDuration(String duration) {
        setProperty("duration", duration);
    }

}
