package comp3350.recipecollection.presentation;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import comp3350.recipecollection.R;
import comp3350.recipecollection.business.AccessRecipes;
import comp3350.recipecollection.business.ParseRecipe;
import comp3350.recipecollection.objects.Ingredient;
import comp3350.recipecollection.objects.Recipe;

public class EditIngreActivity extends AppCompatActivity {
    private AccessRecipes accessRecipes;
    private String[] messages;
    private ArrayList<Recipe> recipes;
    private Recipe targetRecipe;
    private boolean firstIngre;
    private EditText inputName, inputAmt, inputUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accessRecipes = new AccessRecipes();
        Intent intent = getIntent();
        messages = intent.getStringArrayExtra(Messages.MESSAGE_EDIT_INGRE);
        setContentView(R.layout.activity_create_ingre);
        firstIngre = true;
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

    public void onClickNextIngre(View createButton){
        inputName = (EditText)findViewById(R.id.createIngreName);


        if(inputName.getText().toString().trim().length() > 0) {
            addIngre();
        }

        accessRecipes.updateRecipe(targetRecipe);

        Intent editSteps = new Intent(this, EditStepActivity.class);
        editSteps.putExtra(Messages.MESSAGE_EDIT_STEPS, messages);
        startActivity(editSteps);

    }

    public void onClickAddMoreIngre(View createButton){
        inputName = (EditText)findViewById(R.id.createIngreName);
        inputAmt = (EditText)findViewById(R.id.createIngreAmt);
        inputUnit = (EditText)findViewById(R.id.createIngreUnit);

        if(inputName.getText().toString().trim().length() > 0) {
            addIngre();
            inputName.setText("");
            inputAmt.setText("");
            inputUnit.setText("");
        }
        else{
            Toast.makeText(this, "Please Enter the Name", Toast.LENGTH_SHORT).show();
        }
    }


    private void addIngre(){
        String name, amt, unit;
        Ingredient ingredient;
        inputName = (EditText)findViewById(R.id.createIngreName);
        inputAmt = (EditText)findViewById(R.id.createIngreAmt);
        inputUnit = (EditText)findViewById(R.id.createIngreUnit);

        name = inputName.getText().toString().trim();
        amt = inputAmt.getText().toString().trim();
        unit = inputUnit.getText().toString().trim();


        ingredient = new ParseRecipe().buildIngreObject(name, amt, unit);

        if(ingredient != null) {
            if(firstIngre){
                firstIngre = false;
                targetRecipe.setIngredients(new ArrayList<Ingredient>());
                targetRecipe.addIngredients(ingredient);
            }
            else {
                targetRecipe.addIngredients(ingredient);
            }
            Toast.makeText(this, "Ingredient Added", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "_ is an invalid character, try others", Toast.LENGTH_SHORT).show();
        }
    }
}
