package comp3350.recipecollection.test.business;

import junit.framework.TestCase;

import java.util.ArrayList;

import comp3350.recipecollection.application.Main;
import comp3350.recipecollection.application.Services;
import comp3350.recipecollection.business.AccessRecipes;
import comp3350.recipecollection.objects.Ingredient;
import comp3350.recipecollection.objects.Recipe;
import comp3350.recipecollection.test.database.DatabaseRecipeStub;
import static org.junit.Assert.assertNotEquals;

public class AccessRecipesTest extends TestCase {

    private static String dbName = Main.dbName;
    private AccessRecipes businessTest;
    private Recipe targetRecipe;
    private Ingredient newIngredient1, newIngredient2;
    private ArrayList<Recipe> resultRecipes;

    public AccessRecipesTest(String arg0) { super(arg0); }

    public void setUp() throws Exception
    {
        System.out.println("Setting up ...");
        targetRecipe = new Recipe();
        resultRecipes = new ArrayList<Recipe>();

        Services.closeDataAccess();
        Services.createDataAccess(new DatabaseRecipeStub(dbName));

        businessTest = new AccessRecipes();
    }

    public void tearDown() throws Exception
    {
        System.out.println("Tearing down ...\n");
        businessTest = null;
        targetRecipe = null;
        resultRecipes = null;
        Services.closeDataAccess();
    }

    public void testSearchInsertedRecipe() throws Exception
    {

        System.out.println("Starting testSearchInsertedRecipe");

        assertNotNull(businessTest);

        targetRecipe.setRecipeID("1");
        targetRecipe.setName("Fries");
        targetRecipe.setTime(200);

        newIngredient1 = new Ingredient("potato", 100.0, "g");
        targetRecipe.addIngredients(newIngredient1);
        newIngredient2 = new Ingredient("garlic", 10.0, "g");
        targetRecipe.addIngredients(newIngredient2);

        businessTest.insertRecipe(targetRecipe);
        businessTest.compareRecipe(targetRecipe, resultRecipes);
        assertFalse(resultRecipes.isEmpty());
        assertTrue(resultRecipes.size()==1);
        assertEquals(targetRecipe, resultRecipes.get(0));

        System.out.println("Finished testSearchInsertedRecipe");
    }

    public void testSearchExistingRecipe()
    {

        System.out.println("Starting testSearchExistingRecipe");

        assertNotNull(businessTest);
        targetRecipe.setRecipeID("1");
        targetRecipe.setName("Rusty Chicken Thighs");
        targetRecipe.setTime(200);

        newIngredient1 = new Ingredient("chicken", 200.0, "g");
        targetRecipe.addIngredients(newIngredient1);
        newIngredient2 = new Ingredient("garlic", 20.0, "g");
        targetRecipe.addIngredients(newIngredient2);

        targetRecipe.addSteps("cut");
        targetRecipe.addSteps("cook");
        targetRecipe.addSteps("eat");
        targetRecipe.addAttributes("meat");
        targetRecipe.setComment("I need chicken.");

        businessTest.compareRecipe(targetRecipe, resultRecipes);
        assertFalse(resultRecipes.isEmpty());
        assertTrue(resultRecipes.size()==1);
        assertEquals(targetRecipe, resultRecipes.get(0));

        System.out.println("Finished testSearchExistingRecipe");

    }

    public void testSearchMissingRecipe()
    {
        System.out.println("Starting testSearchMissingRecipe");

        assertNotNull(businessTest);
        targetRecipe.setRecipeID("3");
        targetRecipe.setName("Lasagna");
        businessTest.compareRecipe(targetRecipe, resultRecipes);
        assertTrue(resultRecipes.isEmpty());

        System.out.println("Finished testSearchByName");
    }

    public void testSearchAfterDelete()
    {
        System.out.println("\nStarting testSearchAfterDelete");

        assertNotNull(businessTest);
        targetRecipe.setRecipeID("4");
        targetRecipe.setName("A fish recipe");
        targetRecipe.setTime(300);

        newIngredient1 = new Ingredient("fish", 200.0, "g");
        targetRecipe.addIngredients(newIngredient1);
        newIngredient2 = new Ingredient("garlic", 20.0, "g");
        targetRecipe.addIngredients(newIngredient2);

        targetRecipe.addSteps("wash");
        targetRecipe.addSteps("cook");
        targetRecipe.addSteps("eat");
        targetRecipe.addAttributes("seafood");
        targetRecipe.addAttributes("meat");

        targetRecipe.setComment("I love fish.");


        businessTest.compareRecipe(targetRecipe, resultRecipes);
        assertEquals(targetRecipe, resultRecipes.get(0));
        assertTrue(resultRecipes.size()==1);

        businessTest.deleteRecipe(targetRecipe);
        resultRecipes = new ArrayList<Recipe>();
        businessTest.compareRecipe(targetRecipe, resultRecipes);
        assertTrue(resultRecipes.isEmpty());

        System.out.println("Finished testSearchAfterDelete");
    }

    public void testSearchByName()
    {
        System.out.println("Starting testSearchByName");

        assertNotNull(businessTest);

        targetRecipe.setName("Rusty Chicken Thighs");

        businessTest.compareRecipe(targetRecipe, resultRecipes);
        assertTrue(resultRecipes.get(0).equals(targetRecipe));
        assertTrue(resultRecipes.size()==1);

        System.out.println("Finished testSearchByName");
    }

    public void testSearchByIngredient()
    {
        System.out.println("Starting testSearchAfterDelete");

        assertNotNull(businessTest);

        newIngredient1 = new Ingredient("chicken", 200.0, "g");
        targetRecipe.addIngredients(newIngredient1);

        businessTest.compareRecipe(targetRecipe, resultRecipes);
        assertTrue(resultRecipes.get(0).equals(targetRecipe));

        System.out.println("Finished testSearchByIngredient");
    }

    public void testSearchByTime()
    {
        System.out.println("Starting testSearchByTime");

        assertNotNull(businessTest);

        targetRecipe.setTime(200);

        businessTest.compareRecipe(targetRecipe, resultRecipes);
        assertTrue(resultRecipes.get(0).equals(targetRecipe));

        System.out.println("Finished testSearchByTime");
    }

    public void testSearchByAttribute()
    {
        System.out.println("Starting testSearchByAttribute");

        assertNotNull(businessTest);

        targetRecipe.addAttributes("meat");

        businessTest.compareRecipe(targetRecipe, resultRecipes);
        assertTrue(resultRecipes.size()==2);
        assertTrue(resultRecipes.get(0).equals(targetRecipe));
        assertTrue(resultRecipes.get(1).equals(targetRecipe));

        System.out.println("Finished testSearchByAttribute");
    }

    public void testCountRecipes(){
        String[] id1 = new String[1];
        String[] id2 = new String[1];
        Recipe tempRecipe;

        System.out.println("Starting testCountRecipes");

        assertNotNull(businessTest);
        businessTest.getNextID(id1);
        businessTest.getNextID(id2);
        assertNotEquals(id1[0], id2[0]);
        tempRecipe = new Recipe();
        tempRecipe.setRecipeID(id1[0]);
        businessTest.insertRecipe(tempRecipe);
        businessTest.getNextID(id2);
        assertNotEquals(id1[0], id2[0]);

        System.out.println("Finished testCountRecipes");
    }

    public void testFavo(){
        ArrayList<String> ids = new ArrayList<String>();

        System.out.println("Starting testFavo");

        assertNotNull(businessTest);
        assertNull(businessTest.insertFavoRecipe("1"));
        assertNull(businessTest.insertFavoRecipe("2"));
        assertNull(businessTest.insertFavoRecipe("3"));
        assertNull(businessTest.deleteFavoRecipe("2"));
        assertNull(businessTest.getFavosID(ids));
        assertNotNull(ids);
        assertTrue(ids.contains("1"));
        assertTrue(ids.contains("3"));
        assertTrue(!ids.contains("2"));

        System.out.println("Finished testFavo");
    }
}