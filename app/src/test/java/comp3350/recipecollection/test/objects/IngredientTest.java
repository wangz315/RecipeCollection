package comp3350.recipecollection.test.objects;


import junit.framework.TestCase;
import comp3350.recipecollection.objects.Ingredient;
import static org.junit.Assert.assertNotEquals;


public class IngredientTest extends TestCase
{
    public IngredientTest(String arg0)
    {
        super(arg0);
    }

    public void testIngredient1()
    {
        Ingredient ingredient1, ingredient2, ingredient3, ingredient4;

        System.out.println("\nStarting testIngredient");

        ingredient1 = new Ingredient("chicken", 200.0, "g");
        ingredient2 = new Ingredient("chicken", 10.0, "mg");
        ingredient3 = new Ingredient("beef", 10.0, "mg");


        assertNotNull(ingredient1);
        assertEquals("chicken", ingredient1.getName());
        assertEquals("g", ingredient1.getUnits());
        assertEquals(200.0, ingredient1.getAmount());
        assertEquals(ingredient1, ingredient2);
        assertNotEquals(ingredient1, ingredient3);

        System.out.println("Finished testIngredient");
    }
}
