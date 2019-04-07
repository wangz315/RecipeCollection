package comp3350.recipecollection.test;

import junit.framework.Test;
import junit.framework.TestSuite;

import comp3350.recipecollection.test.database.PersistenceTests;
import comp3350.recipecollection.test.objects.ObjectTests;
import comp3350.recipecollection.test.business.BusinessTests;

public class RunUnitTests
{
    public static TestSuite suite;

    public static Test suite()
    {
        suite = new TestSuite("Unit tests");
        suite.addTest(ObjectTests.suite());
        suite.addTest(BusinessTests.suite());
        suite.addTest(PersistenceTests.suite());
        return suite;
    }
}