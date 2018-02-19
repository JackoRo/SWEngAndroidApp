package com.group3.swengandroidapp;

public class RecipeTest {
	public static void main(String args[]) {
		System.out.println("RECIPE TEST BEGINS");
		Recipe recipe = new Recipe();
		System.out.println("Generating mock ingredient: '100ml of Milk'");
		recipe.ingredients.add(new Ingredient("Milk", "100ml"));
		System.out.println("Test ingredient name:           " + recipe.ingredients.get(0).getName());
		System.out.println("Test ingredient quantity:       " + recipe.ingredients.get(0).getQuantity());
		System.out.println("Test ingredient quantity value: " + recipe.ingredients.get(0).getQuantityValue());
		System.out.println("Test ingredient quantity units: " + recipe.ingredients.get(0).getQuantityUnits());
		
	}
}
