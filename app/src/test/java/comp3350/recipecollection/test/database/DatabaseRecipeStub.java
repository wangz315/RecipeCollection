package comp3350.recipecollection.test.database;

import java.util.ArrayList;
import java.util.List;

import comp3350.recipecollection.R;
import comp3350.recipecollection.application.Main;
import comp3350.recipecollection.database.DataAccess;
import comp3350.recipecollection.objects.Ingredient;
import comp3350.recipecollection.objects.Recipe;

public class DatabaseRecipeStub implements DataAccess {

    private String dbName;
    private String dbType = "stub";

    private ArrayList<Recipe> recipes;
    private ArrayList<String> favouriteRecipes;
    private static int numRecipes;
    private static int nextID;

    public DatabaseRecipeStub(String dbName) {this.dbName = dbName;}

    public DatabaseRecipeStub() {this(Main.dbName);}

    public void open(String dbName)
    {
        Recipe recipe;
        Ingredient ingredient;
        nextID = 1;

        favouriteRecipes = new ArrayList<String>();
        recipes = new ArrayList<Recipe>();

        recipe = new Recipe();
        recipe.setRecipeID(String.valueOf(nextID));
        nextID++;
        recipe.setName("Rusty Chicken Thighs");
        recipe.setTime(200);
        recipe.setRecipePosterResource(R.drawable.chicken);

        ingredient = new Ingredient("chicken", 200.0, "g");
        recipe.addIngredients(ingredient);
        ingredient = new Ingredient("garlic", 20.0, "g");
        recipe.addIngredients(ingredient);

        recipe.addSteps("cut");
        recipe.addSteps("cook");
        recipe.addSteps("eat");
        recipe.addAttributes("meat");

        recipe.setComment("I need chicken.");

        recipes.add(recipe);

        recipe = new Recipe();
        recipe.setRecipeID(String.valueOf(nextID));
        nextID++;
        recipe.setName("A fish recipe");
        recipe.setTime(300);
        recipe.setRecipePosterResource(R.drawable.fish);

        ingredient = new Ingredient("fish", 200.0, "g");
        recipe.addIngredients(ingredient);
        ingredient = new Ingredient("garlic", 20.0, "g");
        recipe.addIngredients(ingredient);

        recipe.addSteps("wash");
        recipe.addSteps("cook");
        recipe.addSteps("eat");
        recipe.addAttributes("seafood");
        recipe.addAttributes("meat");

        recipe.setComment("I love fish.");

        recipes.add(recipe);

        recipe = new Recipe();
        recipe.setRecipeID(String.valueOf(nextID));
        nextID++;
        recipe.setName("A vegetable recipe");
        recipe.setTime(100);
        recipe.setRecipePosterResource(R.drawable.vegetable);

        ingredient = new Ingredient("tomato", 200.0, "g");
        recipe.addIngredients(ingredient);
        ingredient = new Ingredient("egg", 50.0, "g");
        recipe.addIngredients(ingredient);

        recipe.addSteps("wash");
        recipe.addSteps("cook");
        recipe.addSteps("eat");
        recipe.addAttributes("vegetable");

        recipe.setComment("eggs, more eggs please.");

        recipes.add(recipe);

        numRecipes = 3;

        System.out.println("Opened " +dbType +" database " +dbName);
    }

    public void close()
    {
        System.out.println("Closed " +dbType +" database " +dbName);
    }

    public String insertRecipe(Recipe currentRecipe)
    {
        recipes.add(currentRecipe);
        numRecipes++;

        return null;
    }

    public String updateRecipe(Recipe currentRecipe)
    {
        int index;

        index = recipes.indexOf(currentRecipe);
        if (index >= 0)
        {
            recipes.set(index, currentRecipe);
        }
        return null;
    }

    public String deleteRecipe(Recipe currentRecipe)
    {
        int index;

        index = recipes.indexOf(currentRecipe);
        if (index >= 0)
        {
            recipes.remove(index);
            numRecipes--;
        }
        return null;
    }

    public String getRecipeSequential(List<Recipe> recipeResult)
    {
        recipeResult.addAll(recipes);
        return null;
    }

    public String searchRecipe(String ID, ArrayList<Recipe> resultRecipe)
    {
        Recipe currentRecipe;

        if (!resultRecipe.isEmpty())
        {
            resultRecipe.clear();
        }

        for (int i = 0; i < recipes.size(); i++) {
            currentRecipe = recipes.get(i);

            if (currentRecipe.getRecipeID().equals(ID))
            {
                resultRecipe.add(currentRecipe);
            }
        }

        return null;
    }

    public String insertFavo(String ID)
    {

        favouriteRecipes.add(ID);
        return null;
    }

    public String deleteFavo(String ID)
    {

        favouriteRecipes.remove(ID);
        return null;
    }

    public String getFavoSequential(List<String> resultID)
    {
        if (!resultID.isEmpty())
        {
            resultID.clear();
        }

        for(int count=0; count<favouriteRecipes.size(); count++) {
            String ID = favouriteRecipes.get(count);
            resultID.add(ID);
        }

        return null;
    }

    public String countRecipe(int[] size)
    {
        size[0] = numRecipes;

        return null;
    }

    public String getNextID(String[] ID){
        ID[0] = String.valueOf(nextID);
        nextID++;
        return null;
    }
}
