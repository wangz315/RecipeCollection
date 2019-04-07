package comp3350.recipecollection.test.objects;

import junit.framework.TestCase;
import static org.junit.Assert.assertNotEquals;

import comp3350.recipecollection.objects.Recipe;
import comp3350.recipecollection.objects.Ingredient;

public class RecipeTest extends TestCase
{
    public RecipeTest(String arg0)
    {
        super(arg0);
    }

    public void testRecipe1()
    {
        Recipe recipe, cmpRecipe1, cmpRecipe2;
        Ingredient ingredient1, ingredient2, ingredient3;

        ingredient1 = new Ingredient("chicken", 200.0, "g");
        ingredient2 = new Ingredient("garlic", 20.0, "g");
        ingredient3 = new Ingredient("beef", 200.0, "g");

        System.out.println("\nStarting testRecipe");

        recipe = new Recipe();
        assertNotNull(recipe);

        recipe.setRecipeID("1");
        assertEquals(recipe.getRecipeID(), "1");

        recipe.setName("Rusty Chicken Thighs");
        assertEquals(recipe.getName(), "Rusty Chicken Thighs");

        recipe.setTime(200);
        assertEquals(recipe.getTime(), 200);

        recipe.addIngredients(ingredient1);
        assertEquals(recipe.getIngredients().get(0), new Ingredient("chicken", 0.0, "g"));

        recipe.addIngredients(ingredient2);
        assertNotEquals(recipe.getIngredients().get(1), new Ingredient("chicken", 0.0, "g"));

        assertTrue(recipe.getIngredients().contains(ingredient1));
        assertTrue(recipe.getIngredients().contains(ingredient2));

        recipe.addSteps("cut");
        recipe.addSteps("cook");
        recipe.addSteps("eat");
        assertEquals(recipe.getSteps().get(0), "cut");
        assertEquals(recipe.getSteps().get(1), "cook");
        assertEquals(recipe.getSteps().get(2), "eat");
        assertTrue(recipe.getSteps().contains("cut"));
        assertTrue(recipe.getSteps().contains("cook"));
        assertTrue(recipe.getSteps().contains("eat"));

        recipe.addAttributes("meat");
        assertEquals(recipe.getAttributes().get(0), "meat");
        assertTrue(recipe.getAttributes().contains("meat"));

        recipe.addAttributes("seafood");
        assertEquals(recipe.getAttributes().get(1), "seafood");
        assertTrue(recipe.getAttributes().contains("seafood"));

        recipe.setComment("I need chicken.");
        assertEquals(recipe.getComment(), "I need chicken.");

        recipe.setComment("I need beef.");
        assertEquals(recipe.getComment(), "I need beef.");

        //tests for equals methods
        assertNotEquals(recipe, null);
        assertNotEquals(recipe, new Ingredient());
        assertEquals(recipe, new Recipe());

        cmpRecipe1 = new Recipe();
        cmpRecipe1.setRecipeID("2");
        cmpRecipe1.setName("cmp1");

        cmpRecipe2 = new Recipe();
        cmpRecipe2.setRecipeID("3");
        cmpRecipe2.setName("cmp1");
        assertEquals(cmpRecipe1, cmpRecipe2);

        cmpRecipe1 = new Recipe();
        cmpRecipe1.setRecipeID("2");

        cmpRecipe2 = new Recipe();
        cmpRecipe2.setRecipeID("3");

        cmpRecipe1.setTime(100);
        cmpRecipe2.setTime(200);
        assertEquals(cmpRecipe1, cmpRecipe2);

        cmpRecipe1.setTime(20000);
        assertNotEquals(cmpRecipe1, cmpRecipe2);

        cmpRecipe1 = new Recipe();
        cmpRecipe1.setRecipeID("2");

        cmpRecipe2 = new Recipe();
        cmpRecipe2.setRecipeID("3");

        cmpRecipe1.addAttributes("meat");
        assertNotEquals(cmpRecipe2, cmpRecipe1);

        cmpRecipe2.addAttributes("duck");
        assertNotEquals(cmpRecipe1, cmpRecipe2);

        cmpRecipe2.addAttributes("meat");
        assertEquals(cmpRecipe1, cmpRecipe2);

        cmpRecipe1.addAttributes("seafood");
        assertEquals(cmpRecipe1, cmpRecipe2);

        cmpRecipe2.addAttributes("chicken");
        assertEquals(cmpRecipe1, cmpRecipe2);

        cmpRecipe2.addAttributes("fish");
        assertEquals(cmpRecipe1, cmpRecipe2);

        cmpRecipe1 = new Recipe();
        cmpRecipe1.setRecipeID("2");

        cmpRecipe2 = new Recipe();
        cmpRecipe2.setRecipeID("3");

        cmpRecipe1.addIngredients(ingredient1);
        assertNotEquals(cmpRecipe2, cmpRecipe1);

        cmpRecipe2.addIngredients(ingredient2);
        assertNotEquals(cmpRecipe2, cmpRecipe1);

        cmpRecipe1.addIngredients(ingredient2);
        assertEquals(cmpRecipe2, cmpRecipe1);

        cmpRecipe1.addIngredients(ingredient3);
        assertEquals(cmpRecipe2, cmpRecipe1);

        cmpRecipe2.addIngredients(ingredient3);
        assertEquals(cmpRecipe2, cmpRecipe1);

        cmpRecipe2.addIngredients(ingredient1);
        assertEquals(cmpRecipe2, cmpRecipe1);

        System.out.println("Finished testRecipe");
    }
}
