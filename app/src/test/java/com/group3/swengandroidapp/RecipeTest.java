package com.group3.swengandroidapp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.group3.recipe.Ingredient;
import com.group3.recipe.Recipe;

class RecipeTest {
	Ingredient tempIngredient;
	public static void main(String args[]) {
		Recipe recipe = new Recipe();
		for(int i = 0; i < recipe.slides.size(); i++) {
			handle(recipe.slides.get(i));
			
			
		};
		
		Recipe test;
		
		recipe.slides.get(0).items.add(image);
	}
	@Test
	void test() {
		fail("Not yet implemented");
	}
	
	public void handle(Object o){
	if(o.getClass() == Ingredient.class) {
		((Ingredient)o).getQuantityValue()
	}else if(o.getClass() == Video.class) {
		
	}
	
	
	
	}

}
