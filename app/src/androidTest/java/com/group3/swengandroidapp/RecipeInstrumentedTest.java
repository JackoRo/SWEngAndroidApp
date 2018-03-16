package com.group3.swengandroidapp;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.group3.swengandroidapp.XMLRenderer.RemoteFileManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RecipeInstrumentedTest {
    /* Context appContext = InstrumentationRegistry.getTargetContext();

    RemoteFileManager remoteFileManager = RemoteFileManager.getInstance();

    @Before
    public void initialize() {

        Intent intent = new Intent(appContext, PythonClient.class);
        intent.putExtra(PythonClient.ACTION,PythonClient.LOAD_ALL);
        startService(intent);
    }

    @Test
    public void recipe_isThere() {
        assertNotNull(remoteFileManager.getRecipeList());
    }

    @Test
    public void recipe_iscorrectID() {
        String id = "0000";
        assertEquals(id,remoteFileManager.getRecipe(id).getID());
    }

    @Test
    public void recipe_iscorrectTitle() {
        String id = "0000";
    }

    @Test
    public void recipe_iscorrectTime() {
        String id = "0000";
    }

    @Test
    public void recipe_iscorrectAuthor() {
        String id = "0000";
    }

    @Test
    public void recipe_iscorrectDesc() {
        String id = "0000";
    }

    @Test
    public void recipe_iscorrectThumbURL() {
        String id = "0000";
    }

    @Test
    public void recipe_iscorrectFilters() {
        String id = "0000";
    }

    @Test
    public void recipe_iscorrectIngredients() {
        String id = "0000";
    }

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.


        assertEquals("com.group3.swengandroidapp", appContext.getPackageName());
    } */
}

