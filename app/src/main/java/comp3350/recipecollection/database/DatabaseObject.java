package comp3350.recipecollection.database;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLWarning;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import comp3350.recipecollection.business.ParseRecipe;
import comp3350.recipecollection.objects.Ingredient;
import comp3350.recipecollection.objects.Recipe;

public class DatabaseObject implements DataAccess {

    private Statement st1;
    private Connection c1;
    private ResultSet rs1, rs2, rs3;

    private String dbName;
    private String dbType;

    private String cmdString;
    private int updateCount;
    private String result;
    private static String EOF = "  ";

    public DatabaseObject(String dbName)
    {
        this.dbName = dbName;
    }

    public void open(String dbPath)
    {
        String url;
        try {
            // Setup for HSQL
            dbType = "HSQL";
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
            url = "jdbc:hsqldb:file:" + dbPath; // stored on disk mode
            c1 = DriverManager.getConnection(url, "SA", "");
            st1 = c1.createStatement();
        }
        catch (Exception e)
        {
            processSQLError(e);
        }
        System.out.println("Opened " +dbType +" database " +dbPath);

    }

    public void close()
    {
        try
        {	// commit all changes to the database
            cmdString = "shutdown compact";
            rs2 = st1.executeQuery(cmdString);
            c1.close();
        }
        catch (Exception e)
        {
            processSQLError(e);
        }
        System.out.println("Closed " +dbType +" database " +dbName);
    }

    public String countRecipe(int size[]){
        result = null;

        try{
            cmdString = "SELECT COUNT(RecipeID) AS rowCount FROM Recipes";
            rs1 = st1.executeQuery(cmdString);
            rs1.next();
            size[0] = rs1.getInt("rowCount");

            rs1.close();
        }catch (Exception e){
            result = processSQLError(e);
        }

        return result;
    }


    public String insertRecipe(Recipe currentRecipe)
    {
        String values;

        result = null;
        try
        {
            values = currentRecipe.getRecipeID()
                    +", '" +currentRecipe.getName()
                    +"', " +currentRecipe.getTime()
                    +", '" +currentRecipe.getIngredientNamesString()
                    +"', '" +currentRecipe.getIngredientAmountsString()
                    +"', '" +currentRecipe.getIngredientUnitsString()
                    +"', '" +currentRecipe.getStepsString()
                    +"', '" +currentRecipe.getAttributesString()
                    +"', '" +currentRecipe.getComment()
                    +"', '" +currentRecipe.getImageName()
                    +"'";
            cmdString = "Insert into Recipes " +" Values(" +values +")";
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);
        }
        catch (Exception e)
        {
            result = processSQLError(e);
        }
        return result;
    }


    public String insertFavo(String ID)
    {
        result = null;
        try
        {
            cmdString = "Insert into FAVOS Values(" + ID + ")";
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);
        }
        catch (Exception e)
        {
            result = processSQLError(e);
        }
        return result;
    }

    public String updateRecipe(Recipe currentRecipe)
    {
        String values;
        String where;

        result = null;
        try
        {
            // Should check for empty values and not update them
            values = "Name='" +currentRecipe.getName()
                    +"', Time=" +currentRecipe.getTime()
                    +", IngredientNames='" +currentRecipe.getIngredientNamesString()
                    +"', IngredientAmounts='" +currentRecipe.getIngredientAmountsString()
                    +"', IngredientUnits='" +currentRecipe.getIngredientUnitsString()
                    +"', Steps='" +currentRecipe.getStepsString()
                    +"', Attributes='"+currentRecipe.getAttributesString()
                    +"', Comment='" +currentRecipe.getComment()
                    +"'";
            where = "where RecipeID=" +currentRecipe.getRecipeID();
            cmdString = "Update Recipes " +" Set " +values + " " +where;
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);
        }
        catch (Exception e)
        {
            result = processSQLError(e);
        }
        return result;
    }

    public String updateID()
    {
        String values;
        result = null;
        int currID = 0;

        try {
            cmdString = "Select * from ID";
            rs2 = st1.executeQuery(cmdString);

            while (rs2.next())
            {
                currID = rs2.getInt("RecipeID");
                currID++;
            }
            rs2.close();
        }
        catch (Exception e){
            processSQLError(e);
        }

        try
        {
            values = "RecipeID=" + String.valueOf(currID);
            cmdString = "Update ID " +" Set " + values;
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);
        }
        catch (Exception e)
        {
            result = processSQLError(e);
        }
        return result;
    }

    public String deleteRecipe(Recipe currentRecipe)
    {
        result = null;
        try
        {
            cmdString = "Delete from Recipes where RecipeID=" + currentRecipe.getRecipeID();
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);
        }
        catch (Exception e)
        {
            result = processSQLError(e);
        }
        return result;

    }


    public String deleteFavo(String ID)
    {

        result = null;
        try
        {
            cmdString = "Delete from FAVOS where RecipeID=" + ID;
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);
        }
        catch (Exception e)
        {
            result = processSQLError(e);
        }
        return result;

    }

    public String getRecipeSequential(List<Recipe> recipeResult)
    {
        Recipe recipe;

        result = null;
        try {
            cmdString = "Select * from Recipes";
            rs2 = st1.executeQuery(cmdString);
        }
        catch (Exception e){
            processSQLError(e);
        }
        try
        {
            while (rs2.next())
            {
                recipe = createRecipeObject(rs2);
                recipeResult.add(recipe);
            }
            rs2.close();
        }
        catch (Exception e)
        {
            result = processSQLError(e);
        }

        return result;
    }



    public String getFavoSequential(List<String> recipeID)
    {
        String myRecipeID;

        myRecipeID = EOF;

        result = null;
        try {
            cmdString = "Select * from FAVOS";
            rs2 = st1.executeQuery(cmdString);
        }
        catch (Exception e){
            processSQLError(e);
        }

        try
        {
            while (rs2.next())
            {
                myRecipeID = rs2.getString("RecipeID");
                recipeID.add(myRecipeID);
            }
            rs2.close();
        }
        catch (Exception e)
        {
            result = processSQLError(e);
        }

        return result;
    }

    public String getNextID(String[] ID)
    {
        String myRecipeID;

        myRecipeID = EOF;

        result = null;
        try {
            cmdString = "Select * from ID";
            rs2 = st1.executeQuery(cmdString);
        }
        catch (Exception e){
            processSQLError(e);
        }
        try
        {
            if (rs2.next())
            {
                myRecipeID = rs2.getString("RecipeID");
                ID[0] = myRecipeID;

            }
            updateID();

            rs2.close();
        }
        catch (Exception e)
        {
            result = processSQLError(e);
        }

        return result;
    }



    public String searchRecipe(String ID, ArrayList<Recipe> resultList)
    {
        Recipe resultRecipe;

        try
        {
            cmdString = "Select * from Recipes where RecipeID=" + ID;
            rs3 = st1.executeQuery(cmdString);

            if (rs3.next())
            {
                resultRecipe = createRecipeObject(rs3);
                resultList.add(resultRecipe);
            }
            rs3.close();
        } catch (Exception e)
        {
            processSQLError(e);
        }
        return null;
    }

    public String checkWarning(Statement st, int updateCount)
    {
        String result;

        result = null;
        try
        {
            SQLWarning warning = st.getWarnings();
            if (warning != null)
            {
                result = warning.getMessage();
            }
        }
        catch (Exception e)
        {
            result = processSQLError(e);
        }
        if (updateCount != 1)
        {
            result = "Tuple not inserted correctly.";
        }
        return result;
    }

    public String processSQLError(Exception e)
    {
        String result = "*** SQL Error: " + e.getMessage();

        e.printStackTrace();

        return result;
    }

    private Recipe createRecipeObject(ResultSet rs){
        String myRecipeID, myRecipeName, myIngredientNames, myIngredientAmounts, myIngredientUnits,
                mySteps, myAttributes, myComment, myImage;
        int myRecipeTime;
        Recipe recipe = null;
        Ingredient newIngre;
        ArrayList<Ingredient> ingredients;
        ArrayList<String> ingreName;
        ArrayList<String> ingreAmt;
        ArrayList<String> ingreUnit;
        ArrayList<String> attributes;
        ArrayList<String> steps;


        myRecipeID = EOF;
        myRecipeName = EOF;
        myRecipeTime = 0;
        myIngredientNames = EOF;
        myIngredientAmounts = EOF;
        myIngredientUnits = EOF;
        mySteps = null;
        myComment = EOF;
        myImage = EOF;

        try {
            myRecipeID = rs.getString("RecipeID");
            myRecipeName = rs.getString("Name");
            myRecipeTime = rs.getInt("Time");
            myIngredientNames = rs.getString("IngredientNames");
            myIngredientAmounts = rs.getString("IngredientAmounts");
            myIngredientUnits = rs.getString("IngredientUnits");
            mySteps = rs.getString("Steps");
            myAttributes = rs.getString("Attributes");
            myComment = rs.getString("Comment");
            myImage = rs.getString("Image");

            ingredients = new ArrayList<Ingredient>();
            ingreName = new ArrayList<String>(Arrays.asList(myIngredientNames.split(",")));
            ingreAmt = new ArrayList<String>(Arrays.asList(myIngredientAmounts.split(",")));
            ingreUnit = new ArrayList<String>(Arrays.asList(myIngredientUnits.split(",")));

            for (int i = 0; i < ingreName.size(); i++) {
                newIngre = new Ingredient(ingreName.get(i), 0.0, ingreUnit.get(i));

                if ((new ParseRecipe()).isParsableDouble(ingreAmt.get(i))) {
                    newIngre.setAmount(Double.parseDouble(ingreAmt.get(i)));
                }
                ingredients.add(newIngre);
            }

            steps = new ArrayList<String>(Arrays.asList(mySteps.split(",")));
            attributes = new ArrayList<String>(Arrays.asList(myAttributes.split(",")));

            recipe = new Recipe(myRecipeID, myRecipeName, myRecipeTime,
                    ingredients, steps, attributes, myComment, myImage);
        }
        catch (Exception e)
        {
            processSQLError(e);
        }

        return recipe;
    }
}
