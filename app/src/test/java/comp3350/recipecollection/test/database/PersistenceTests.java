package comp3350.recipecollection.test.database;


import junit.framework.Test;
import junit.framework.TestSuite;

public class PersistenceTests
{
    public static TestSuite suite;

    public static Test suite()
    {
        suite = new TestSuite("Persistence tests");
        suite.addTestSuite(DatabaseObjectTest.class);
        return suite;
    }
}
