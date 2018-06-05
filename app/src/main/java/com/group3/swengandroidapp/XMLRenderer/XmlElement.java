package com.group3.swengandroidapp.XMLRenderer;

import android.app.Activity;
import android.text.SpannableStringBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Element class for parsing into from the PWS
 * Every Drawable object extends this class so the elements can inherit their parent attributes
 * */

public abstract class XmlElement implements Drawable {
    protected XmlElement parent;
    protected Map<String, String> properties;
    protected List<XmlElement> children;

    public XmlElement(XmlElement parent) {
        this.parent = parent;
        properties = new HashMap<>();
        children = new ArrayList<>();
    }

    public void addChild(XmlElement item) {
        if (item != null) {
            children.add(item);
        }
    }

    public void setProperty(String key, String value) {
        properties.put(key, value);
    }


    public String getProperty(String key) {
        return properties.get(key);
    }

    public String getInheritableProperty(String key) {
        String prop = getProperty(key);
        if (prop != null) {
            return prop;
        }
        if (parent != null) {
            prop = parent.getInheritableProperty(key);
            if (prop != null) {
                return prop;
            }
        }
        return null;
    }

    public Map<String, String> getProperties() {
        // mutations of the returned value will affect the value in this class, don't forget
        return properties;
    }

    @Override
    public void buildString(SpannableStringBuilder stringBuilder, Activity activity) {

    }
}
