package comp3350.recipecollection.test.business;

import junit.framework.TestCase;

import comp3350.recipecollection.business.ParseRecipe;
import comp3350.recipecollection.objects.Ingredient;
import comp3350.recipecollection.objects.Recipe;

public class ParseRecipeTest extends TestCase {
    private Recipe recipe;
    private String[] messages;

    public ParseRecipeTest(String arg0)
    {
        super(arg0);
    }

    public void setUp() throws Exception
    {
        System.out.println("Setting up ...");
        recipe = new Recipe();
        messages = new String[4];
    }

    public void tearDown() throws Exception
    {
        System.out.println("Tearing down ...\n");
        recipe = null;
        messages = null;
    }


    public void testParseToRecipe()
    {
        System.out.println("Starting test ParseRecipe");

        messages[0] = "recipe0";
        messages[1] = "150";
        messages[2] = "ingre1,ingre2";
        messages[3] = "attr1,attr2";

        (new ParseRecipe()).parseToRecipe(messages, recipe);
        assertNotNull(recipe);

        assertEquals(messages[0], recipe.getName());
        assertEquals(messages[1], String.valueOf(recipe.getTime()));
        assertEquals(messages[2], recipe.getIngredients().get(0).getName() + "," + recipe.getIngredients().get(1).getName());
        assertEquals(messages[3], recipe.getAttributes().get(0) + "," + recipe.getAttributes().get(1));


        System.out.println("Finished test ParseRecipe");
    }

    public void testIsParsableInt()
    {
        String number = "100";
        String word = "test";
        boolean isParsable;

        System.out.println("Starting test ParseRecipe: isParsableInt");
        isParsable = (new ParseRecipe()).isParsableInt(number);
        assertTrue(isParsable);
        isParsable = (new ParseRecipe()).isParsableInt(word);
        assertTrue(!isParsable);

        isParsable = (new ParseRecipe()).isParsableInt(null);
        assertTrue(!isParsable);

        isParsable = (new ParseRecipe()).isParsableInt("");
        assertTrue(!isParsable);

        System.out.println("Finished test ParseRecipe: isParsableInt");
    }

    public void testIsParsableDouble()
    {
        String number = "10.0";
        String word = "test";
        boolean isParsable;

        System.out.println("Starting test ParseRecipe: isParsableDouble");
        isParsable = (new ParseRecipe()).isParsableDouble(number);
        assertTrue(isParsable);
        isParsable = (new ParseRecipe()).isParsableDouble(word);
        assertTrue(!isParsable);

        isParsable = (new ParseRecipe()).isParsableDouble(null);
        assertTrue(!isParsable);

        isParsable = (new ParseRecipe()).isParsableDouble("");
        assertTrue(!isParsable);

        System.out.println("Finished test ParseRecipe: isParsableDouble");
    }

    public void testEditBasic()
    {
        String name = "name";
        String time = "10";
        String attr = "attr";

        System.out.println("Starting test ParseRecipe: editBasic");

        new ParseRecipe().editBasic(recipe, name, time, attr);
        assertEquals(recipe.getName(), "name");
        assertEquals(recipe.getTime(), 10);
        assertTrue(recipe.getAttributes().contains("attr"));

        attr = "attr1,attr2";
        recipe = new Recipe();
        new ParseRecipe().editBasic(recipe, name, time, attr);
        assertTrue(recipe.getAttributes().contains("attr1"));
        assertTrue(recipe.getAttributes().contains("attr2"));

        time = "time";
        recipe = new Recipe();
        new ParseRecipe().editBasic(recipe, name, time, attr);
        assertEquals(recipe.getTime(), -1);


        System.out.println("Finished test ParseRecipe: editBasic");
    }

    public void testBuildIngreObject()
    {
        Ingredient ingredient;
        String name;
        String amt;
        String unit;

        System.out.println("Starting test ParseRecipe: buildIngreObject");

        name = "name";
        amt = "10.0";
        unit = "g";
        ingredient = new ParseRecipe().buildIngreObject(name, amt, unit);
        assertEquals(ingredient.getName(), "name");
        assertEquals(ingredient.getAmount(), 10.0);
        assertEquals(ingredient.getUnits(), "g");

        amt = "amt";
        ingredient = new ParseRecipe().buildIngreObject(name, amt, unit);
        assertEquals(ingredient.getAmount(), 0.0);

        name = "_name_";
        ingredient = new ParseRecipe().buildIngreObject(name, amt, unit);
        assertNull(ingredient);

        System.out.println("Finished test ParseRecipe: buildIngreObject");
    }

    public void testCreateRecipe()
    {
        messages[0] = "name";
        messages[1] = "10";
        messages[2] = "ingre1_10.0_g,ingre2_20.0_kg";
        messages[3] = "attr1,attr2";
        System.out.println("Starting test ParseRecipe: createRecipe");

        new ParseRecipe().createRecipe(recipe, messages, "1");
        assertEquals(recipe.getName(), "name");
        assertEquals(recipe.getTime(), 10);
        assertEquals(recipe.getIngredients().get(0).getName(), "ingre1");
        assertEquals(recipe.getIngredients().get(0).getAmount(), 10.0);
        assertEquals(recipe.getIngredients().get(0).getUnits(), "g");
        assertEquals(recipe.getIngredients().get(1).getName(), "ingre2");
        assertEquals(recipe.getIngredients().get(1).getAmount(), 20.0);
        assertEquals(recipe.getIngredients().get(1).getUnits(), "kg");
        assertTrue(recipe.getIngredients().contains(new Ingredient("ingre1", 0, "g")));

        assertEquals(recipe.getAttributes().get(0), "attr1");
        assertEquals(recipe.getAttributes().get(1), "attr2");
        assertEquals(recipe.getImageName(), "noimage");

        messages[1] = "time";
        recipe = new Recipe();
        new ParseRecipe().createRecipe(recipe, messages, "1");
        assertEquals(recipe.getTime(), -1);

        messages[2] = "ingre1_string_g";
        recipe = new Recipe();
        new ParseRecipe().createRecipe(recipe, messages, "1");
        assertTrue(recipe.getIngredients().contains(new Ingredient("ingre1", 0, "g")));
        assertEquals(recipe.getIngredients().get(0).getName(), "ingre1");
        assertEquals(recipe.getIngredients().get(0).getAmount(), 0.0);
        assertEquals(recipe.getIngredients().get(0).getUnits(), "g");


        System.out.println("Finished test ParseRecipe: createRecipe");
    }
}
