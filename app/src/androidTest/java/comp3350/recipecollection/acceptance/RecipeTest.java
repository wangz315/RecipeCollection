/*
*Problem:
*If we
*-run the acceptance tests separately on nexus 7 and on emulator
*-run the acceptance test entirely on emulator
*All tests pass
*However, if we
*-run the acceptance tests entirely on nexus 7
*One of the tests may crashes

*We talked to professor, he said this may caused by the device run faster than code. Please consider this situation.
 */






package comp3350.recipecollection.acceptance;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import junit.framework.Assert;

import comp3350.recipecollection.presentation.MainActivity;

public class RecipeTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private Solo solo;

    public RecipeTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception
    {
        solo = new Solo(getInstrumentation(), getActivity());
        // Disable this for full acceptance test
        // System.out.println("Injecting stub database.");
        // Services.createDataAccess(new DataAccessStub(Main.dbName));
    }
    public void tearDown() throws Exception
    {
        solo.finishOpenedActivities();
    }


    public void testSearch()
    {
        solo.waitForActivity("MainActivity");
        solo.clickOnButton("Search Recipes");
        solo.assertCurrentActivity("Expected activity SearchActivity",
                "SearchActivity");
        solo.enterText(0,"A fish recipe");
        solo.clickOnButton("Search");

        solo.goBack();
        solo.clearEditText(0);
        solo.assertCurrentActivity("Expected activity SearchActivity",
                "SearchActivity");
        solo.enterText(1,"300");
        solo.clickOnButton("Search");

        solo.goBack();
        solo.clearEditText(1);
        solo.assertCurrentActivity("Expected activity SearchActivity",
                "SearchActivity");
        solo.enterText(2,"fish");
        solo.clickOnButton("Search");

        solo.goBack();
        solo.clearEditText(2);
        solo.assertCurrentActivity("Expected activity SearchActivity",
                "SearchActivity");
        solo.enterText(3,"meat");
        solo.clickOnButton("Search");

        solo.goBack();
        solo.assertCurrentActivity("Expected activity SearchActivity",
                "SearchActivity");
        solo.clearEditText(3);
        solo.enterText(0,"Rusty Chicken Thighs");
        solo.enterText(1,"200");
        solo.enterText(2,"chicken");
        solo.enterText(3,"meat");
        solo.clickOnButton("Search");


        solo.goBack();
        solo.clearEditText(0);
        solo.clearEditText(1);
        solo.clearEditText(2);
        solo.clearEditText(3);
        solo.clickOnButton("Search");
    }

    public void testFavoriteRecipe()
    {
        solo.waitForActivity("MainActivity");
        solo.clickOnButton("View All Recipes");
        solo.assertCurrentActivity("Expected activity ViewRecipeActivity",
                "ViewRecipeActivity");
        Assert.assertTrue(solo.searchText("A fish recipe"));
        solo.clickOnText("A fish recipe");
        solo.clickOnButton("Add to Favorite");
        solo.goBack();
        solo.goBack();
        solo.waitForActivity("ViewRecipeActivity");
        solo.goBack();
        solo.waitForActivity("MainActivity");

        solo.clickOnButton("View Favorite Recipes");
        solo.assertCurrentActivity("Expected activity ViewRecipeActivity",
                "ViewRecipeActivity");
        Assert.assertTrue(solo.searchText("A fish recipe"));
        solo.clickOnText("A fish recipe");
        solo.assertCurrentActivity("Expected activity DetailPageActivity",
                "DetailPageActivity");
        solo.clickOnButton("REMOVE FROM FAVORITE");
        Assert.assertFalse(solo.searchText("A fish recipe"));

    }

    public void testDeleteRecipe() {
        solo.waitForActivity("MainActivity");
        solo.clickOnButton("create recipe");
        solo.assertCurrentActivity("Expected activity CreateBasicActivity",
                "CreateBasicActivity");

        solo.enterText(0, "Fish_Test");
        solo.clickOnButton("Next Step");
        solo.assertCurrentActivity("Expected activity CreateIngreActivity",
                "CreateIngreActivity");
        solo.clickOnButton("Next Step");
        solo.assertCurrentActivity("Expected activity CreateStepActivity",
                "CreateStepActivity");
        solo.clickOnButton("Done");

        solo.waitForActivity("MainActivity");
        solo.clickOnButton("View All Recipes");
        solo.assertCurrentActivity("Expected activity ViewRecipeActivity",
                "ViewRecipeActivity");
        Assert.assertTrue(solo.searchText("Fish_Test"));
        solo.clickOnText("Fish_Test");
        solo.assertCurrentActivity("Expected activity DetailPageActivity",
                "DetailPageActivity");
        solo.clickOnButton("Delete Recipe");

        solo.waitForActivity("MainActivity");
        solo.clickOnButton("View All Recipes");
        solo.assertCurrentActivity("Expected activity ViewRecipeActivity",
                "ViewRecipeActivity");
        Assert.assertFalse(solo.searchText("Fish_Test"));

    }

    public void testComment()
    {
        solo.waitForActivity("MainActivity");
        solo.clickOnButton("View All Recipes");
        solo.assertCurrentActivity("Expected activity ViewRecipeActivity",
                "ViewRecipeActivity");
        Assert.assertTrue(solo.searchText("A fish recipe"));
        solo.clickOnText("A fish recipe");

        solo.clickOnButton("Comment");
        solo.typeText(0,"yeah");
        solo.clickOnButton("Done");
    }

    public void testEdit()
    {
        solo.waitForActivity("MainActivity");

        //edit time
        solo.clickOnButton("View All Recipes");
        solo.assertCurrentActivity("Expected activity ViewRecipeActivity",
                "ViewRecipeActivity");
        Assert.assertTrue(solo.searchText("A fish recipe"));
        solo.clickOnText("A fish recipe");
        solo.clickOnButton("Edit");
        solo.enterText(1,"500");
        solo.clickOnButton("Next Step");
        solo.clickOnButton("Next Step");
        solo.clickOnButton("Done");

        //edit attributs
        solo.clickOnButton("View All Recipes");
        solo.assertCurrentActivity("Expected activity ViewRecipeActivity",
                "ViewRecipeActivity");
        Assert.assertTrue(solo.searchText("A fish recipe"));
        solo.clickOnText("A fish recipe");
        solo.clickOnButton("Edit");
        solo.enterText(2,"fish");
        solo.clickOnButton("Next Step");
        solo.clickOnButton("Next Step");
        solo.clickOnButton("Done");

        //edit ingredients
        solo.clickOnButton("View All Recipes");
        solo.assertCurrentActivity("Expected activity ViewRecipeActivity",
                "ViewRecipeActivity");
        Assert.assertTrue(solo.searchText("A fish recipe"));
        solo.clickOnText("A fish recipe");
        solo.clickOnButton("Edit");
        solo.clickOnButton("Next Step");
        solo.enterText(0,"little fish");
        solo.enterText(1,"1000");
        solo.enterText(2,"g");
        solo.clickOnButton("Next Step");
        solo.clickOnButton("Done");

        //edit steps
        solo.clickOnButton("View All Recipes");
        solo.assertCurrentActivity("Expected activity ViewRecipeActivity",
                "ViewRecipeActivity");
        Assert.assertTrue(solo.searchText("A fish recipe"));
        solo.clickOnText("A fish recipe");
        solo.clickOnButton("Edit");
        solo.clickOnButton("Next Step");
        solo.clickOnButton("Next Step");
        solo.typeText(0,"clean");
        solo.clickOnButton("Done");

        //edit name
        solo.clickOnButton("View All Recipes");
        solo.assertCurrentActivity("Expected activity ViewRecipeActivity",
                "ViewRecipeActivity");
        Assert.assertTrue(solo.searchText("A fish recipe"));
        solo.clickOnText("A fish recipe");
        solo.clickOnButton("Edit");
        solo.enterText(0,"A fish recipe1");
        solo.clickOnButton("Next Step");
        solo.clickOnButton("Next Step");
        solo.clickOnButton("Done");

        // careful change name back to original name
        solo.clickOnButton("View All Recipes");
        solo.assertCurrentActivity("Expected activity ViewRecipeActivity",
                "ViewRecipeActivity");
        Assert.assertTrue(solo.searchText("A fish recipe1"));
        solo.clickOnText("A fish recipe1");
        solo.clickOnButton("Edit");
        solo.enterText(0,"A fish recipe");
        solo.clickOnButton("Next Step");
        solo.clickOnButton("Next Step");
        solo.clickOnButton("Done");
    }


    public void testCreateRecipe()
    {
        solo.waitForActivity("MainActivity");
        solo.clickOnButton("create recipe");
        solo.assertCurrentActivity("Expected activity CreateBasicActivity",
                "CreateBasicActivity");

        solo.enterText(0,"beef");
        solo.enterText(1,"Asd"); // input should be int, otherwise should show Unknown
        solo.enterText(2,"123");

        solo.clickOnButton("Next Step");
        solo.clickOnButton("Next Step");
        solo.clickOnButton("Done");

        //create only by Ingredients
        solo.waitForActivity("MainActivity");
        solo.clickOnButton("create recipe");
        solo.assertCurrentActivity("Expected activity CreateBasicActivity",
                "CreateBasicActivity");
        solo.clickOnButton("Next Step");
        solo.enterText(0,"meat");
        solo.enterText(1,"#$"); // input should be double, otherwise should show Unknown
        solo.enterText(2,"g");
        solo.clickOnButton("Next Step");
        solo.clickOnButton("Done");

        // create a empty recipe
        solo.waitForActivity("MainActivity");
        solo.clickOnButton("create recipe");
        solo.assertCurrentActivity("Expected activity CreateBasicActivity",
                "CreateBasicActivity");
        solo.clickOnButton("Next Step");
        solo.assertCurrentActivity("Expected activity CreateIngreActivity",
                "CreateIngreActivity");
        solo.clickOnButton("Next Step");

        solo.assertCurrentActivity("Expected activity CreateStepActivity",
                "CreateStepActivity");
        solo.clickOnButton("Add More Steps");
        solo.clickOnButton("Done");

        // create by full
        solo.waitForActivity("MainActivity");
        solo.clickOnButton("create recipe");
        solo.assertCurrentActivity("Expected activity CreateBasicActivity",
                "CreateBasicActivity");

        solo.enterText(0,"beef1");
        solo.enterText(1,"300");
        solo.enterText(2,"beef");

        solo.clickOnButton("Next Step");
        solo.assertCurrentActivity("Expected activity CreateIngreActivity",
                "CreateIngreActivity");

        solo.enterText(0,"beef");
        solo.enterText(1,"50");
        solo.enterText(2,"g");

        solo.clickOnButton("Next Step");
        solo.assertCurrentActivity("Expected activity CreateStepActivity",
                "CreateStepActivity");
        solo.typeText(0,"Cut");
        solo.clickOnCheckBox(0);
        solo.clickOnButton("Done");

    }

}

