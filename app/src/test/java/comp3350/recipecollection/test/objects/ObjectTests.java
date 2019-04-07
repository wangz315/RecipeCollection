package comp3350.recipecollection.test.objects;

import junit.framework.Test;
import junit.framework.TestSuite;

public class ObjectTests
{
    public static TestSuite suite;

    public static Test suite()
    {
        suite = new TestSuite("Object tests");
        suite.addTestSuite(IngredientTest.class);
        suite.addTestSuite(RecipeTest.class);
        return suite;
    }
}
