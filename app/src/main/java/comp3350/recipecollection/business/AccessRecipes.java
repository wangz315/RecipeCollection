package comp3350.recipecollection.business;

import java.util.ArrayList;
import java.util.List;

import comp3350.recipecollection.application.Main;
import comp3350.recipecollection.application.Services;
import comp3350.recipecollection.database.DataAccess;
import comp3350.recipecollection.objects.Recipe;


public class AccessRecipes {
    private DataAccess dataAccess;
    private List<Recipe> recipes;
    private List<String> favos;
    private Recipe recipe;
    private String favoID;
    private int currentRecipe;
    private int currentFavo;

    public AccessRecipes()
    {
        dataAccess = Services.getDataAccess(Main.dbName);
        recipes = null;
        favos = null;
        recipe = null;
        favoID = null;
        currentRecipe = 0;
        currentFavo = 0;
    }

    public String getRecipes(List<Recipe> recipes)
    {
        return dataAccess.getRecipeSequential(recipes);
    }

    public Recipe getSequential()
    {
        String result = null;
        if (recipes == null)
        {
            recipes = new ArrayList<Recipe>();
            result = dataAccess.getRecipeSequential(recipes);
            currentRecipe = 0;
        }
        if (currentRecipe < recipes.size())
        {
            recipe = recipes.get(currentRecipe);
            currentRecipe++;
        }
        else
        {
            recipes = null;
            recipe = null;
            currentRecipe = 0;
        }
        return recipe;
    }

    public String insertRecipe(Recipe currentRecipe)
    {
        return dataAccess.insertRecipe(currentRecipe);
    }

    public String updateRecipe(Recipe currentRecipe)
    {
        return dataAccess.updateRecipe(currentRecipe);
    }

    public String deleteRecipe(Recipe currentRecipe)
    {
        return dataAccess.deleteRecipe(currentRecipe);
    }

    public String searchRecipe(String id, ArrayList<Recipe> resultList)
    {

        ArrayList<Recipe> recipes = new ArrayList<>();
        String message = getRecipes(recipes);
        for(Recipe r: recipes){
            if(id.equals(r.getRecipeID())){
                resultList.add(r);
            }
        }
        return message;

    }

    public String containFavo(String id, boolean[] contain)
    {
        ArrayList<String> IDs = new ArrayList<String>();

        contain[0] = false;
        String message = getFavosID(IDs);
        for(String s: IDs){

            if(id.equals(s)){
                contain[0] = true;
            }
        }
        return message;
    }

    public String compareRecipe(Recipe targetRecipe, ArrayList<Recipe> resultRecipe)
    {
        String errorMessage = null;
        ArrayList<Recipe> recipes = new ArrayList<>();
        getRecipes(recipes);
        for(Recipe r: recipes){
            if(r.equals(targetRecipe)){
                resultRecipe.add(r);
            }
        }

        return errorMessage;
    }

    public String getFavoRecipeList(ArrayList<Recipe> resultRecipes)
    {
        ArrayList<Recipe> recipeList = new ArrayList<Recipe>();
        ArrayList<String> favoID = new ArrayList<String>();
        String message;
        message = getRecipes(recipeList);
        getFavosID(favoID);

        for(int indexRecipe = 0; indexRecipe < recipeList.size(); indexRecipe++) {
            for(int indexID = 0; indexID < favoID.size(); indexID++){
                if(recipeList.get(indexRecipe).getRecipeID().equals(favoID.get(indexID))){
                    resultRecipes.add(recipeList.get(indexRecipe));
                }
            }
        }

        return message;
    }

    public String getFavosID(List<String> ID)
    {
        return dataAccess.getFavoSequential(ID);
    }

    public String getFavoSequential()
    {
        String result = null;
        if (favos == null)
        {
            favos = new ArrayList<String>();
            result = dataAccess.getFavoSequential(favos);
            currentFavo = 0;
        }
        if (currentFavo < favos.size())
        {
            favoID = favos.get(currentFavo);
            currentFavo++;
        }
        else
        {
            favos = null;
            favoID = null;
            currentFavo = 0;
        }
        return favoID;
    }


    public String insertFavoRecipe(String ID){
        return dataAccess.insertFavo(ID);
    }

    public String deleteFavoRecipe(String ID){
        return dataAccess.deleteFavo(ID);
    }

    public String getNextID(String[] ID)
    {
        return dataAccess.getNextID(ID);
    }
}
