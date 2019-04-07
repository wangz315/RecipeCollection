package comp3350.recipecollection.test.business;


import android.widget.EditText;

import junit.framework.TestCase;

import comp3350.recipecollection.R;
import comp3350.recipecollection.business.BuildString;
import comp3350.recipecollection.objects.Ingredient;
import comp3350.recipecollection.objects.Recipe;

public class BuildStringTest extends TestCase {

    public BuildStringTest(String arg0)
    {
        super(arg0);
    }

    public void setUp() throws Exception
    {
        System.out.println("Setting up ...");

    }

    public void tearDown() throws Exception
    {
        System.out.println("Tearing down ...\n");
    }


    public void testBuildIngreString()
    {
        String name, amt, unit;
        String ingreString;
        System.out.println("Starting test buildIngreString");
        name = "name";
        amt = "10.0";
        unit = "g";

        ingreString = new BuildString().buildIngreString(name, amt, unit);
        assertEquals(ingreString, "name_10.0_g,");

        amt = "amt";
        ingreString = new BuildString().buildIngreString(name, amt, unit);
        assertEquals(ingreString, "name_0_g,");

        name = "_name_";
        ingreString = new BuildString().buildIngreString(name, amt, unit);
        assertNull(ingreString);


        System.out.println("Finished test buildIngreString");
    }


    public void testDetailNameBuilder()
    {
        Recipe recipe = new Recipe();
        recipe.setName("name");
        String nameString;

        System.out.println("Starting test detailNameBuilder");

        nameString = new BuildString().detailNameBuilder(recipe);
        assertEquals(nameString, "Name:\nname");


        System.out.println("Finished test detailNameBuilder");
    }


    public void testDetailStepBuilder()
    {
        Recipe recipe = new Recipe();
        recipe.addSteps("step1");
        String stepString;

        System.out.println("Starting test detailStepBuilder");

        stepString = new BuildString().detailStepBuilder(recipe);
        assertEquals(stepString, "Steps:\nstep1\n");

        recipe.addSteps("step2");
        stepString = new BuildString().detailStepBuilder(recipe);
        assertEquals(stepString, "Steps:\nstep1\nstep2\n");

        System.out.println("Finished test detailStepBuilder");
    }


    public void testDetailIngreBuilder()
    {
        Recipe recipe = new Recipe();
        recipe.addIngredients(new Ingredient("ingre1", 10.0, "g"));
        String ingreString;

        System.out.println("Starting test detailIngreBuilder");

        ingreString = new BuildString().detailIngreBuilder(recipe);
        assertEquals(ingreString, "Ingredients:\n" +
                String.format("%-10s %4.1f %s", "ingre1", 10.0, "g") + "\n");


        recipe.addIngredients(new Ingredient("ingre2", 5.0, "g"));
        ingreString = new BuildString().detailIngreBuilder(recipe);
        assertEquals(ingreString, "Ingredients:\n" +
                String.format("%-10s %4.1f %s", "ingre1", 10.0, "g") + "\n" +
                String.format("%-10s %4.1f %s", "ingre2", 5.0, "g") + "\n");

        System.out.println("Finished test detailIngreBuilder");
    }


    public void testDetailCommentBuilder()
    {
        Recipe recipe = new Recipe();
        String commentString;

        System.out.println("Starting test detailCommentBuilder");

        commentString = new BuildString().detailCommentBuilder(recipe);
        assertEquals(commentString, "Comment:\nNo Comment.");

        recipe.setComment("");
        commentString = new BuildString().detailCommentBuilder(recipe);
        assertEquals(commentString, "Comment:\nNo Comment.");

        recipe.setComment("comment.");
        commentString = new BuildString().detailCommentBuilder(recipe);
        assertEquals(commentString, "Comment:\ncomment.");


        System.out.println("Finished test detailCommentBuilder");
    }


    public void testDetailTimeBuilder()
    {
        Recipe recipe = new Recipe();
        String timeString;

        System.out.println("Starting test detailTimeBuilder");

        timeString = new BuildString().detailTimeBuilder(recipe);
        assertEquals(timeString, "Time Cost:\nUnknown");

        recipe.setTime(-1);
        timeString = new BuildString().detailTimeBuilder(recipe);
        assertEquals(timeString, "Time Cost:\nUnknown");


        recipe.setTime(10);
        timeString = new BuildString().detailTimeBuilder(recipe);
        assertEquals(timeString, "Time Cost:\n10");


        System.out.println("Finished test detailTimeBuilder");
    }
}
