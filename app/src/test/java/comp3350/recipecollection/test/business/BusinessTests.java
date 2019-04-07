package comp3350.recipecollection.test.business;

import junit.framework.Test;
import junit.framework.TestSuite;

public class BusinessTests
{
    public static TestSuite suite;

    public static Test suite()
    {
        suite = new TestSuite("Business tests");
        suite.addTestSuite(AccessRecipesTest.class);
        suite.addTestSuite(BuildStringTest.class);
        suite.addTestSuite(ParseRecipeTest.class);
        return suite;
    }
}
