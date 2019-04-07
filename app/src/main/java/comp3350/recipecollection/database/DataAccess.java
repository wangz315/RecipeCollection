package comp3350.recipecollection.database;

import java.util.ArrayList;
import java.util.List;

import comp3350.recipecollection.objects.Recipe;

public interface DataAccess {

    void open(String dbPath);

    void close();

    String countRecipe(int[] size);

    String insertRecipe(Recipe currentRecipe);

    String insertFavo(String ID);

    String updateRecipe(Recipe currentRecipe);

    String deleteRecipe(Recipe currentRecipe);

    String deleteFavo(String ID);

    String getRecipeSequential(List<Recipe> recipeResult);

    String getFavoSequential(List<String> resultID);

    String searchRecipe(String id, ArrayList<Recipe> resultList);

    String getNextID(String[] ID);
}
