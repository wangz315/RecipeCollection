package comp3350.recipecollection.test.integration;


import junit.framework.TestCase;

import java.util.ArrayList;

import comp3350.recipecollection.business.AccessRecipes;
import comp3350.recipecollection.objects.Recipe;
import comp3350.recipecollection.application.Services;
import comp3350.recipecollection.application.Main;
import static org.junit.Assert.assertNotEquals;

public class BusinessPersistenceSeamTest extends TestCase
{
    public BusinessPersistenceSeamTest(String arg0)
    {
        super(arg0);
    }

    public void testAccessRecipes()
    {
        ArrayList<Recipe> recipelist = new ArrayList<Recipe>();
        ArrayList<String> IDs = new ArrayList<String>();
        AccessRecipes accessRecipes;
        Recipe targetRecipe;
        String result;
        String oldName;
        String[] id = new String[1];
        boolean[] contain = new boolean[1];

        Services.closeDataAccess();

        System.out.println("\nStarting Integration test of AccessRecipes to persistence");

        Services.createDataAccess(Main.dbName);

        accessRecipes = new AccessRecipes();

        //get all recipes
        result = accessRecipes.getRecipes(recipelist);
        assertNull(result);
        assertTrue(recipelist.size() > 0);

        targetRecipe = new Recipe();
        targetRecipe.setRecipeID("1");

        //search recipe
        recipelist = new ArrayList<Recipe>();
        result = accessRecipes.searchRecipe("1", recipelist);
        assertNull(result);
        assertEquals("1", recipelist.get(0).getRecipeID());
        targetRecipe = recipelist.get(0);

        //delete recipe, then search, put back
        recipelist = new ArrayList<Recipe>();
        result = accessRecipes.deleteRecipe(targetRecipe);
        assertNull(result);
        result = accessRecipes.searchRecipe("1", recipelist);
        assertNull(result);
        assertEquals(recipelist.size(), 0);
        result = accessRecipes.insertRecipe(targetRecipe);
        assertNull(result);


        //update recipe, then search, then compare, then change back
        recipelist = new ArrayList<Recipe>();
        result = accessRecipes.searchRecipe("2", recipelist);
        assertNull(result);
        assertEquals("2", recipelist.get(0).getRecipeID());
        targetRecipe = recipelist.get(0);
        oldName = targetRecipe.getName();

        recipelist = new ArrayList<Recipe>();
        targetRecipe.setName("targetRecipeTest");
        result = accessRecipes.updateRecipe(targetRecipe);
        assertNull(result);
        result = accessRecipes.searchRecipe("2", recipelist);
        assertNull(result);
        assertEquals(recipelist.size(), 1);
        targetRecipe = recipelist.get(0);
        assertEquals(targetRecipe.getName(), "targetRecipeTest");


        recipelist = new ArrayList<Recipe>();
        result = accessRecipes.compareRecipe(targetRecipe, recipelist);
        assertNull(result);
        assertTrue(recipelist.size() > 0);
        assertTrue(recipelist.contains(targetRecipe));

        targetRecipe.setName(oldName);
        result = accessRecipes.updateRecipe(targetRecipe);
        assertNull(result);

        //get next ID, then insert, search, delete
        recipelist = new ArrayList<Recipe>();
        result = accessRecipes.getNextID(id);
        assertNull(result);
        targetRecipe = new Recipe();
        targetRecipe.setRecipeID(id[0]);
        result = accessRecipes.insertRecipe(targetRecipe);
        assertNull(result);
        result = accessRecipes.searchRecipe(id[0], recipelist);
        assertNull(result);
        assertEquals(recipelist.size(), 1);
        result = accessRecipes.deleteRecipe(recipelist.get(0));
        assertNull(result);


        //search for the recipe that does not exist in database
        recipelist = new ArrayList<Recipe>();
        result = accessRecipes.searchRecipe("-1", recipelist);
        assertNull(result);
        assertEquals(recipelist.size(), 0);

        /******FAVO******/
        //insert, contain, getID, getRecipe, delete, contain, getID, getRecipe
        result = accessRecipes.insertFavoRecipe("2");
        assertNull(result);
        result = accessRecipes.containFavo("2", contain);
        assertNull(result);
        assertTrue(contain[0]);
        result = accessRecipes.getFavosID(IDs);
        assertNull(result);
        assertTrue(IDs.contains("2"));
        assertEquals(IDs.get(0), "2");

        recipelist = new ArrayList<Recipe>();
        result = accessRecipes.getFavoRecipeList(recipelist);
        assertNull(result);
        assertEquals(recipelist.get(0).getRecipeID(), "2");

        result = accessRecipes.deleteFavoRecipe("2");
        assertNull(result);

        result = accessRecipes.containFavo("2", contain);
        assertNull(result);
        assertTrue(!contain[0]);

        IDs = new ArrayList<String>();
        result = accessRecipes.getFavosID(IDs);
        assertNull(result);
        assertEquals(IDs.size(), 0);

        recipelist = new ArrayList<Recipe>();
        result = accessRecipes.getFavoRecipeList(recipelist);
        assertNull(result);
        assertEquals(recipelist.size(), 0);


        Services.closeDataAccess();

        System.out.println("Finished Integration test of AccessRecipes to persistence");
    }
}
