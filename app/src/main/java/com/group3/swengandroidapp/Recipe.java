package com.group3.swengandroidapp;

import java.util.ArrayList;

/**
 * Created by User 1 on 06/03/2018.
 */

public class Recipe {
    protected String title;
    protected String author;
    protected String description;
    protected int ID;

    public Recipe(String title, String author, String description, int ID) {
        this.title = title;
        this.ID = ID;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getAuthor(){
        return author;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getID(){
        return ID;
    }

    public void setID(int ID){
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", ID=" + ID +
                '}';
    }



}
