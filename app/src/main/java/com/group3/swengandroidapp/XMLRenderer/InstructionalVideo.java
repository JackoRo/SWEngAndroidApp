package com.group3.swengandroidapp.XMLRenderer;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.io.Serializable;

// TODO: copy from recipe.java

public class InstructionalVideo implements Serializable {
    public final static int THUMBNAILSIZE = 250;
    public final static String ID = "Recipe_ID"; // Used with broadcast

    // Meta data
    private String title = "n/a";
    private String id = "n/a";
    private String thumbnail = "n/a";

    // CONSTRUCTORS
    public InstructionalVideo() {

    }

    public InstructionalVideo(String title, String id) {

        // Set meta data, checking to make sure that each has been given
        // if not, leave at default of "n/a"
        if(title != null) {
            this.title = title;
        }
        if(id != null) {
            this.id = id;
        }
    }

    // SETTERS
    public void setTitle(String title) {
        this.title = title;
    }
    public void setID(String id) {
        this.id = id;
    }
    public void setThumbnail(String url) { this.thumbnail = url; }

    // GETTERS
    public String getTitle() {
        return title;
    }
    public String getID() { return id; }
    public String getThumbnail() { return thumbnail; }

    public InstructionalVideo clone(){
        InstructionalVideo r = new InstructionalVideo(this.getTitle(), this.getID());
        r.setThumbnail(this.getThumbnail());
        return r;
    }

    public static class Icon{
        public final static String ICON_CHANGED = "InstructionalVideo.Icon_iconChanged"; // Used with broadcast

        private String title;
        private android.graphics.drawable.Drawable image;
        private String id;

        public Icon(String title, android.graphics.drawable.Drawable image, String id){
            this.title = title;
            this.image = image;
            this.id = id;
        }

        public String getTitle(){return this.title;}
        public android.graphics.drawable.Drawable getDrawable(){return this.image;}
        public String getId(){return this.id;}

        public void setDrawable(Drawable d){
            this.image = d;
        }

        public void setTitle(String title){
            this.title = title;
        }
    }

    public static Icon produceDescriptor(Context c, InstructionalVideo instructionalVideo){
        return new InstructionalVideo.Icon(instructionalVideo.getTitle(), null, instructionalVideo.getID());
    }

}
