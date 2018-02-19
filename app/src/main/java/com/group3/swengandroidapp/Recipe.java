package com.group3.swengandroidapp;

import java.util.ArrayList;

public class Recipe {
	// Meta data
	private String title = "n/a";
	private String author = "n/a";
	private String description = "n/a";
	
	// public to take advantage of built-in methods of ArrayList class
	public ArrayList<Ingredient> ingredients;
	public ArrayList<Slide> slides;
	
	
	public Recipe() {
		ingredients = new ArrayList<Ingredient>(0);
	}
	
	public Recipe(String title, String author, String description) {
		if(title != null) {
			this.title = title;
		}
		if(author != null) {
			this.author = author;
		}
		if(description != null) {
			this.description = description;
		}
	}
	
	/**
	 * Update the description of the recipe
	 * @param description new description to be set for this recipe
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public String getDescription() {
		return description;
	}
}
