/**
 * Unit tests for client server communication
 */

import android.util.Log;

import com.group3.swengandroidapp.PythonClient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;


public class ClientServerTest {

    PythonClient client;

    @Before
    public void setUp() throws Exception {
        client = new PythonClient();
    }

    @Test
    public void fetchRecipes() {
        String[] expectedRecipeList = new String[]{"0000","0001","0002","0003","0004","0005","0006","0007","0008","0009","0010", "0011", "0012"};
        try {
            String[] recipeList = client.fetchRecipeListFromHttpServer();

            for (String recipeName: recipeList) {
                System.out.println(recipeName);
                if (!Arrays.asList(expectedRecipeList).contains(recipeName)) {
                    fail(recipeName + " not in list");
                }
            }
        }
        catch (Exception e) {
            fail("No connection to server");
        }

    }

    @Test
    public void fetchARecipe() {
        try {
            byte[] testBytes = Files.readAllBytes(Paths.get("app/src/main/python/recipe/0000/0000.xml"));
            String testString = new String(testBytes, Charset.defaultCharset());
            assertEquals(testString, client.fetchRecipeFromHttpServer("0000"));
        }
        catch (Exception e) {
            fail("No connection to server");
        }
    }

    @Test
    public void fetchMyRecipes() {
        String[] expectedRecipeList = new String[]{"0000","0001","0002"};
        try {
            String[] recipeList = client.fetchMyRecipeListFromHttpServer();

            for (String recipeName: recipeList) {
                System.out.println(recipeName);
                if (!Arrays.asList(expectedRecipeList).contains(recipeName)) {
                    fail(recipeName + " not in list");
                }
            }
        }
        catch (Exception e) {
            fail("No connection to server");
        }

    }

    @Test
    public void fetchAMyRecipe() {
        try {
            byte[] testBytes = Files.readAllBytes(Paths.get("app/src/main/python/myRecipe/0000/0000.xml"));
            String testString = new String(testBytes, Charset.defaultCharset());
            assertEquals(testString, client.fetchMyRecipeFromHttpServer("0000"));
        }
        catch (Exception e) {
            fail("No connection to server");
        }
    }
}
