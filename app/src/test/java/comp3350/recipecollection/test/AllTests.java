package comp3350.recipecollection.test;

import junit.framework.Test;
import junit.framework.TestSuite;

import comp3350.recipecollection.test.business.AccessRecipesTest;
import comp3350.recipecollection.test.business.BuildStringTest;
import comp3350.recipecollection.test.business.ParseRecipeTest;
import comp3350.recipecollection.test.database.DatabaseObjectTest;
import comp3350.recipecollection.test.objects.IngredientTest;
import comp3350.recipecollection.test.objects.RecipeTest;


public class AllTests
{
    public static TestSuite suite;

    public static Test suite()
    {
        suite = new TestSuite("All tests");
        testObjects();
        testBusiness();
        testDatabase();
        return suite;
    }

    private static void testObjects()
    {
        suite.addTestSuite(IngredientTest.class);
        suite.addTestSuite(RecipeTest.class);
    }

    private static void testBusiness() {
        suite.addTestSuite(AccessRecipesTest.class);
        suite.addTestSuite(ParseRecipeTest.class);
        suite.addTestSuite(BuildStringTest.class);
    }

    private static void testDatabase() { suite.addTestSuite(DatabaseObjectTest.class);}
}
