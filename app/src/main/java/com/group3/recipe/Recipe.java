package com.group3.recipe;

import java.util.ArrayList;

/**
 * {@link Recipe}<p>
 * This class contains the following attributes:<p>
 * {@linkplain String} {@linkplain #title} Name of the recipe<p>
 * {@linkplain String} {@linkplain #author} Author of the recipe<p>
 * {@linkplain String} {@linkplain #description} A short description as provided by the author.<p>
 * {@linkplain ArrayList}<{@linkplain Ingredient}> {@linkplain #ingredients} A list of all ingredients<p>
 * {@linkplain ArrayList}<{@linkplain Slide}> {@linkplain #slides} A list of all slides for the slideshow<p>
 * @see {@link Ingredient}, {@link Slide}
 * @author mb1510 (Team Leader)
 *
 */
public class Recipe extends Presentation{
	// Meta data
	private String title = "n/a";
	private String author = "n/a";
	private String description = "n/a";
	private String id = "n/a";
	
	// The following are public to take advantage of built-in methods of ArrayList class
	public ArrayList<Ingredient> ingredients;
	public ArrayList<Slide> slides;
	
	public Recipe() {
		// Both ArrayLists initialised to have 0 stored objects
		// (Whenever you add to an array list, it extends the size by 1 (I think))
		ingredients = new ArrayList<Ingredient>(0);
		slides = new ArrayList<Slide>(0);
	}
	
	public Recipe(String title, String author, String description, String id) {
		
		// Set meta data, checking to make sure that each has been given
		// if not, leave at default of "n/a"
		if(title != null) {
			this.title = title;
		}
		if(author != null) {
			this.author = author;
		}
		if(description != null) {
			this.description = description;
		}
		
		if(id != null) {
			this.id = id;
		}
		
		// Both ArrayLists initialised to have 0 stored objects
		// (Whenever you add to an array list, it extends the size by 1 (I think))
		ingredients = new ArrayList<Ingredient>(0);
		slides = new ArrayList<Slide>(0);
		
		
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setID(String id) {
		this.id = id;
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
	
	public String getId() {
		return this.id;
	}
}
