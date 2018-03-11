package com.group3.recipe;

/**
 * @deprecated
 * {@link Ingredient}<p>
 * Class that contains name and quantity information about a single ingredient.<p>
 * @see {@link #getQuantityUnits()}, {@link #getQuantityValue()}, {@link #getQuantity()}, {@link #getName()}
 * @author mb1510 (Team Leader)
 *
 */
public abstract class Ingredient{
	private String name;
	private String quantity;
	
	public Ingredient(String name, String quantity) {
		this.name = name;
		this.quantity = quantity;
	}
	
	public String getName() {
		return name;
	}
	
	/**
	 * String getQuantity(void)<p>
	 * Returns the full 'quantity' string<p>
	 * e.g. "100g", "5 cups", "1 tbsp" etc
	 * @return
	 */
	public String getQuantity() {
		return quantity;
	}
	
	/**
	 * Get the value of the quantity string<p>
	 * Works by extracting any digit characters
	 * from the quantity string.
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
	
	/**
	 * Get the units from the quantity string<p>
	 * Works by extracting any non-digit characters from the
	 * quantity string.
	 * @return String units
	 */
	public String getQuantityUnits() {
		return quantity.replaceAll("\\d", "");
	}
	

	
	public String toString() {
		return new String(name + ": " + quantity);
	}

}
