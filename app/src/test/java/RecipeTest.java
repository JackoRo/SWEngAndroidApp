/**
 * Unit tests for recipe class
 */

import com.group3.swengandroidapp.PythonClient;
import com.group3.swengandroidapp.XMLRenderer.Audio;
import com.group3.swengandroidapp.XMLRenderer.Format;
import com.group3.swengandroidapp.XMLRenderer.Presentation;
import com.group3.swengandroidapp.XMLRenderer.RawText;
import com.group3.swengandroidapp.XMLRenderer.Recipe;
import com.group3.swengandroidapp.XMLRenderer.Shape;
import com.group3.swengandroidapp.XMLRenderer.Text;
import com.group3.swengandroidapp.XMLRenderer.TextAndroid;
import com.group3.swengandroidapp.XMLRenderer.Video;
import com.group3.swengandroidapp.XMLRenderer.XmlElement;
import com.group3.swengandroidapp.XMLRenderer.XmlParser;
import com.group3.swengandroidapp.XMLRenderer.XmlRecipe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
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

            //Parsing slides
            assertEquals("#023211", presentation.getSlide(2).getProperty("fill"));
            assertEquals("#F341DE", presentation.getSlide(2).getProperty("color"));

            //Parsing text
            assertTrue(presentation.getSlide(2).getChildren().get(1) instanceof Text);
            assertEquals("true", presentation.getSlide(2).getChildren().get(1).getInheritableProperty("bold"));
            assertEquals("true", presentation.getSlide(2).getChildren().get(1).getInheritableProperty("underline"));
            assertEquals("10", presentation.getSlide(2).getChildren().get(1).getInheritableProperty("textsize"));
            assertEquals(" Some text on one line ", ((RawText) presentation.getSlide(2).getChildren().get(1).getChildren().get(0)).getText());

            //Parsing format
            assertTrue(presentation.getSlide(2).getChildren().get(1).getChildren().get(2) instanceof Format);
            assertEquals("false", presentation.getSlide(2).getChildren().get(1).getChildren().get(2).getInheritableProperty("bold"));
            assertEquals("true", presentation.getSlide(2).getChildren().get(1).getChildren().get(2).getInheritableProperty("italic"));
            assertEquals("12", presentation.getSlide(2).getChildren().get(1).getChildren().get(2).getInheritableProperty("textsize"));
            assertEquals("Helvetica", presentation.getSlide(2).getChildren().get(1).getChildren().get(2).getInheritableProperty("font"));
            assertEquals("#F341DE", presentation.getSlide(2).getChildren().get(1).getChildren().get(2).getInheritableProperty("color"));

            //Parsing shapes
            assertTrue(presentation.getSlide(2).getChildren().get(3) instanceof Shape);
            assertEquals("103", presentation.getSlide(2).getChildren().get(3).getInheritableProperty("x1"));
            assertEquals("123", presentation.getSlide(2).getChildren().get(3).getInheritableProperty("y1"));
            assertEquals("ellipse", presentation.getSlide(2).getChildren().get(3).getInheritableProperty("type"));
            assertEquals("583", presentation.getSlide(2).getChildren().get(3).getInheritableProperty("x2"));
            assertEquals("933", presentation.getSlide(2).getChildren().get(3).getInheritableProperty("y2"));
            assertEquals("2", presentation.getSlide(2).getChildren().get(3).getInheritableProperty("stroke"));
            assertEquals("#FF69B4", presentation.getSlide(2).getChildren().get(3).getInheritableProperty("color"));
            assertEquals("gradient(#38AEDE,#3482ED)", presentation.getSlide(3).getChildren().get(3).getInheritableProperty("fill"));

            //Parsing videos
            assertTrue(presentation.getSlide(4).getChildren().get(1) instanceof Video);
            assertEquals("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                    presentation.getSlide(4).getChildren().get(1).getInheritableProperty("path"));
            assertEquals("100", presentation.getSlide(4).getChildren().get(1).getInheritableProperty("x1"));
            assertEquals("100", presentation.getSlide(4).getChildren().get(1).getInheritableProperty("y1"));
            assertEquals("500", presentation.getSlide(4).getChildren().get(1).getInheritableProperty("x2"));
            assertEquals("500", presentation.getSlide(4).getChildren().get(1).getInheritableProperty("y2"));

            //Parsing audio
            assertTrue(presentation.getSlide(5).getChildren().get(1) instanceof Audio);
            assertEquals("audio/audiotest.wav",
                    presentation.getSlide(5).getChildren().get(1).getInheritableProperty("path"));

        }
        catch (Exception e) {
            e.printStackTrace();
            fail("No presentation found");
        }
    }
}
