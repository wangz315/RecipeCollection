package comp3350.recipecollection.application;


import comp3350.recipecollection.presentation.CLI;

public class Main {
    public static final String dbName = "Recipe";
    private static String dbPathName = "database/Recipe";

    public static void main(String[] args)
    {
        startUp();

        CLI.run();

        shutDown();
        System.out.println("All done");
    }

    public static void startUp()
    {
        Services.createDataAccess(dbName);
    }

    public static void shutDown()
    {
        Services.closeDataAccess();
    }

    public static String getDBPathName() {
        if (dbPathName == null)
            return dbName;
        else
            return dbPathName;
    }

    public static void setDBPathName(String pathName) {
        System.out.println("Setting DB path to: " + pathName);
        dbPathName = pathName;
    }
}
