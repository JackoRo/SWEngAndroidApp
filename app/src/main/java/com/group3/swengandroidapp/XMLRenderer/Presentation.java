package com.group3.swengandroidapp.XMLRenderer;

import android.app.Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jack on 22/02/2018.
 */

public class Presentation extends XmlElement {
    private List<Slide> slides;
    private Map<String, String> meta;

    public Presentation() {
        super(null);
        slides = new ArrayList<>();
        meta = new HashMap<>();
    }

    @Override
    public void addChild(XmlElement item) {
        if (item instanceof Slide) {
            addSlide((Slide) item);
        } else if (item instanceof Meta) {
            for (Map.Entry<String, String> e : item.getProperties().entrySet()) {
                addMeta(e.getKey(), e.getValue());
            }
        }
        else {
            super.addChild(item);
        }
    }

    public void addSlide(Slide slide) {
        slides.add(slide);
    }

    public Slide getSlide(int index) {
        return slides.get(index);
    }

    public void addMeta(String k, String v) {
        meta.put(k, v);
    }

    public String getMeta(String k) {
        return meta.get(k);
    }

    @Override
    public void draw(Activity activity) {

    }
}
