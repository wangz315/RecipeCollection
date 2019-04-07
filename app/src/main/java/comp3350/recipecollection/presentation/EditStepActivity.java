package comp3350.recipecollection.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import comp3350.recipecollection.R;
import comp3350.recipecollection.business.AccessRecipes;
import comp3350.recipecollection.objects.Ingredient;
import comp3350.recipecollection.objects.Recipe;

public class EditStepActivity extends AppCompatActivity {
    private AccessRecipes accessRecipes;
    private String[] messages;
    private String step;
    private ArrayList<Recipe> recipes;
    private Recipe targetRecipe;
    private EditText inputSteps;
    private CheckBox addToFavo;
    private boolean firstStep;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firstStep = true;
        accessRecipes = new AccessRecipes();
        Intent intent = getIntent();
        messages = intent.getStringArrayExtra(Messages.MESSAGE_EDIT_STEPS);
        setContentView(R.layout.activity_create_step);
        accessRecipes = new AccessRecipes();
        recipes = new ArrayList<Recipe>();
        accessRecipes.searchRecipe(messages[0], recipes);
        if(recipes.size() > 0) {
            targetRecipe = recipes.get(0);
        }
        else{
            Toast.makeText(this, "Cannot Find the Recipe", Toast.LENGTH_SHORT).show();
            Intent mainActivity = new Intent(this, MainActivity.class);
            startActivity(mainActivity);
        }
    }


    public void onClickDone(View createButton){

        inputSteps = (EditText)findViewById(R.id.createSteps);
        addToFavo = (CheckBox) findViewById(R.id.addToFavourite);


        step = inputSteps.getText().toString();
        if(step.length() > 0){
            addSteps();
        }

        accessRecipes.updateRecipe(targetRecipe);

        if (addToFavo.isChecked()) {
            accessRecipes.insertFavoRecipe(targetRecipe.getRecipeID());
        }

        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }



    public void onClickAddMoreSteps(View view){
        inputSteps = (EditText)findViewById(R.id.createSteps);
        addSteps();
        inputSteps.setText("");
    }



    private void addSteps(){
        inputSteps = (EditText)findViewById(R.id.createSteps);
        step = inputSteps.getText().toString();

        if(step.trim().length() > 0){
            if(firstStep){
                firstStep = false;
                targetRecipe.setSteps(new ArrayList<String>());
                targetRecipe.addSteps(step);

            }
            else {
                targetRecipe.addSteps(step);
            }
            Toast.makeText(this, "Step Added", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Please Enter the Step", Toast.LENGTH_SHORT).show();
        }
    }
}
