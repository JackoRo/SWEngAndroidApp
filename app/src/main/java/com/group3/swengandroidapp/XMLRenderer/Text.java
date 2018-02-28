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

    public String getBold() {return getInheritableProperty("bold");}
    public void setBold(String bold) {setProperty("bold", bold);}

    public String getUnderline() {return getInheritableProperty("underline");}
    public void setUnderline(String underline) {setProperty("underline", underline);}

}
