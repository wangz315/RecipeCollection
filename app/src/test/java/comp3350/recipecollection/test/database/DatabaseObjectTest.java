package comp3350.recipecollection.test.database;

import junit.framework.TestCase;

import java.util.ArrayList;

import comp3350.recipecollection.application.Services;
import comp3350.recipecollection.application.Main;

import comp3350.recipecollection.objects.Recipe;
import comp3350.recipecollection.database.DataAccess;
import comp3350.recipecollection.test.database.DatabaseRecipeStub;

public class DatabaseObjectTest extends TestCase{
    private static String dbName = Main.dbName;


    public DatabaseObjectTest(String arg0)
    {
        super(arg0);
    }

    public void testDataAccess() throws Exception
    {
        DataAccess dataAccess;

        Services.closeDataAccess();

        System.out.println("\nStarting database test DataAccess (using stub)");

        // Use the following statement to run with the stub database
        dataAccess = Services.createDataAccess(new DatabaseRecipeStub(dbName));

        dataAccessTest();

        System.out.println("Finished database test DataAccess (using stub)");
    }

    public static void dataAccessTest(){
        DataAccess dataAccess;
        ArrayList<Recipe> recipes;
        ArrayList<String> favouriteRecipes;
        int[] count = new int[1];
        String[] id = new String[1];

        Recipe recipe;
        String result;

        System.out.println("Starting dataAccessTest");
        dataAccess = Services.getDataAccess(dbName);
        System.out.println("Test existingRecipes");

        recipes = new ArrayList<Recipe>();
        result = dataAccess.getRecipeSequential(recipes);
        assertNull(result);

        recipe = recipes.get(0);
        assertNotNull(recipe);
        assertEquals(recipe.getRecipeID(), "1");

        recipe = recipes.get(1);
        assertNotNull(recipe);
        assertEquals(recipe.getRecipeID(), "2");

        recipe = recipes.get(2);
        assertNotNull(recipe);
        assertEquals(recipe.getRecipeID(), "3");

        dataAccess.countRecipe(count);
        assertEquals(count[0],recipes.size());

        System.out.println("Test existingRecipes complete");


        System.out.println("Test addRecipe & get next ID & count recipe");

        recipe = new Recipe();
        dataAccess.getNextID(id);
        recipe.setRecipeID(id[0]);
        dataAccess.insertRecipe(recipe);

        recipes = new ArrayList<Recipe>();
        result = dataAccess.getRecipeSequential(recipes);
        assertNull(result);
        assertNotNull(recipes);
        assertEquals(4,recipes.size());
        dataAccess.countRecipe(count);
        assertEquals(id[0],recipes.get(3).getRecipeID());
        assertEquals(count[0],recipes.size());

        System.out.println("Test addRecipe & get next ID & count recipe complete");

        System.out.println("Test search & update recipe");
        recipes = new ArrayList<Recipe>();
        result = dataAccess.searchRecipe(id[0], recipes);
        assertNull(result);
        assertNotNull(recipes);
        assertEquals(recipes.get(0).getRecipeID(), id[0]);

        recipes.get(0).setName("testName");
        result = dataAccess.updateRecipe(recipes.get(0));
        assertNull(result);

        recipes = new ArrayList<Recipe>();
        result = dataAccess.searchRecipe(id[0], recipes);
        assertNull(result);
        assertNotNull(recipes);
        assertEquals(recipes.get(0).getName(), "testName");

        System.out.println("Test search & update recipe complete");


        System.out.println("Test deleteRecipe");

        dataAccess.deleteRecipe(recipe);

        recipes = new ArrayList<Recipe>();
        result = dataAccess.getRecipeSequential(recipes);
        assertNull(result);
        assertNotNull(recipes);
        assertEquals(3,recipes.size());
        dataAccess.countRecipe(count);
        assertEquals(count[0],recipes.size());

        System.out.println("Test deleteRecipe complete");

        System.out.println("Test favouriteRecipes");

        dataAccess.insertFavo(recipe.getRecipeID());
        favouriteRecipes = new ArrayList<String>();
        result = dataAccess.getFavoSequential(favouriteRecipes);
        assertNull(result);
        assertNotNull(favouriteRecipes);
        assertEquals(favouriteRecipes.get(0),recipe.getRecipeID());

        dataAccess.deleteFavo(recipe.getRecipeID());
        favouriteRecipes = new ArrayList<String>();
        result = dataAccess.getFavoSequential(favouriteRecipes);
        assertNull(result);
        assertNotNull(favouriteRecipes);
        assertEquals(0, favouriteRecipes.size());

        System.out.println("Test favouriteRecipes complete");


        System.out.println("Finished dataAccessTest");

        Services.closeDataAccess();
    }

}
