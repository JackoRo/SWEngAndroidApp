/**
 * Unit tests for recipe class
 */

import com.group3.swengandroidapp.PythonClient;
import com.group3.swengandroidapp.XMLRenderer.Presentation;
import com.group3.swengandroidapp.XMLRenderer.Recipe;
import com.group3.swengandroidapp.XMLRenderer.XmlElement;
import com.group3.swengandroidapp.XMLRenderer.XmlParser;
import com.group3.swengandroidapp.XMLRenderer.XmlRecipe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class RecipeTest {

    PythonClient client;

    @Before
    public void setUp() throws Exception {
        client = new PythonClient();
    }

    @Test
    public void recipeCreation() {
        try{
            Recipe recipe = new XmlRecipe(client.fetchRecipeFromHttpServer("0000"));
            assertEquals("0000", recipe.getID());
            assertEquals("Cake", recipe.getTitle());
            assertEquals("2 Hours", recipe.getTime());
            assertEquals("Mary Berry", recipe.getAuthor());
            assertEquals("The cake is a lie.", recipe.getDescription());
            assertEquals("thumbnail.jpg", recipe.getThumbnail());
            assertEquals("Egg", recipe.getIngredients().get(0).getName());
            assertEquals("2medium", recipe.getIngredients().get(0).getQuantity());
            assertEquals("Flour", recipe.getIngredients().get(1).getName());
            assertEquals("100grams", recipe.getIngredients().get(1).getQuantity());
            assertEquals("Sugar", recipe.getIngredients().get(2).getName());
            assertEquals("100grams", recipe.getIngredients().get(2).getQuantity());
            assertEquals("Butter", recipe.getIngredients().get(3).getName());
            assertEquals("100grams", recipe.getIngredients().get(3).getQuantity());
            assertFalse(recipe.getVegan());
            assertFalse(recipe.getSpicy());
            assertFalse(recipe.getLactose());
            assertFalse(recipe.getNuts());
            assertTrue(recipe.getVegetarian());
            assertTrue(recipe.getGluten());

        }
        catch (Exception e) {
            e.printStackTrace();
            fail("No recipe found");
        }
    }

    @Test
    public void presentationCreation() {
        try {
            Presentation presentation = new XmlParser(client.fetchPresentationFromHttpServer("0000")).parse();
            assertEquals("#023211", presentation.getSlide(2).getProperty("fill"));
            assertEquals("#F341DE", presentation.getSlide(2).getProperty("color"));
        }
        catch (Exception e) {
            e.printStackTrace();
            fail("No presentation found");
        }
    }
}
