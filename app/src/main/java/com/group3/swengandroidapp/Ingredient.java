package com.group3.swengandroidapp;

public class Ingredient {
	private String name;
	private String quantity;
	
	public Ingredient() {
		name = null;
		quantity = null;
	}
	
	/**
	 * 
	 * @param name
	 * @param quantity format: XY where X = value, Y = units
	 */
	public Ingredient(String name, String quantity) {
		this.name = name;
		this.quantity = quantity;
	}
	
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the integer value of the quantity string.
	 * @return int
	 */
	public int getQuantityValue() {
		// Parses an integer from all digit characters in "quantity"
		try {
			return Integer.parseInt(quantity.replaceAll("\\D+", ""));
		}catch(NumberFormatException e) {
			return -1;
		}
	}
	
	public String getQuantityUnits() {
		return quantity.replaceAll("\\d", "");
	}
	
	public String getQuantity() {
		return quantity;
	}
	
	public String toString() {
		return new String(name + " " + quantity);
	}
}
