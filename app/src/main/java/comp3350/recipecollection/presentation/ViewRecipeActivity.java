package comp3350.recipecollection.presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;

import comp3350.recipecollection.R;
import comp3350.recipecollection.business.AccessRecipes;
import comp3350.recipecollection.business.ParseRecipe;
import comp3350.recipecollection.objects.Recipe;
import comp3350.recipecollection.objects.RecipeAdapter;

public class ViewRecipeActivity extends AppCompatActivity {
    private android.widget.ListView listView;
    private RecipeAdapter recipeAdapter;
    private ArrayList<Recipe> recipeList;
    private AccessRecipes accessRecipes;
    private Intent intent;
    private String[] messages;
    private Recipe targetRecipe;
    private ArrayList<Recipe> resultRecipes;
    private Intent detailPageIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipes);
        accessRecipes = new AccessRecipes();
        recipeList = new ArrayList<Recipe>();
        resultRecipes = new ArrayList<Recipe>();

        final String result = accessRecipes.getRecipes(recipeList);
        if (result != null)
        {
            Messages.fatalError(this, result);
        }
        else
        {
            listView = (android.widget.ListView)findViewById(R.id.recipeList);

            intent = getIntent();
            messages = intent.getStringArrayExtra(Messages.MESSAGE_VIEW);

            targetRecipe = new Recipe();

            if(messages[4].equals(Messages.DATABASE)) {
                (new ParseRecipe()).parseToRecipe(messages, targetRecipe);
                accessRecipes.compareRecipe(targetRecipe, resultRecipes);
            }
            else if(messages[4].equals(Messages.FAVO)){
                accessRecipes.getFavoRecipeList(resultRecipes);
            }

            for(Recipe r: resultRecipes){
                r.setRecipePosterResource(getResources().getIdentifier(r.getImageName(), "drawable", this.getPackageName()));
            }

            recipeAdapter = new RecipeAdapter(getApplicationContext(),R.layout.activity_view_recipes);

            if(resultRecipes.size() > 0) {
                for(int i = 0; i < resultRecipes.size(); i++) {
                    recipeAdapter.add(resultRecipes.get(i));
                }

                listView.setAdapter(recipeAdapter);

                listView.setOnItemClickListener(
                        new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Recipe recipe = (Recipe) listView.getItemAtPosition(position);
                                detailPageIntent = new Intent(view.getContext(), DetailPageActivity.class);

                                messages = new String[]{recipe.getRecipeID()};

                                detailPageIntent.putExtra(Messages.MESSAGE_DETL, messages);
                                startActivity(detailPageIntent);
                            }
                        }
                );
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_recipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
