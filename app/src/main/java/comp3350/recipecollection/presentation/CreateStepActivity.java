package comp3350.recipecollection.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import comp3350.recipecollection.R;
import comp3350.recipecollection.business.AccessRecipes;
import comp3350.recipecollection.business.ParseRecipe;
import comp3350.recipecollection.objects.Recipe;



public class CreateStepActivity extends AppCompatActivity {

    private String[] messages;
    private String step;
    private EditText inputSteps;
    private AccessRecipes accessRecipes;
    private CheckBox addToFavo;
    private Recipe newRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        messages = intent.getStringArrayExtra(Messages.MESSAGE_NEXT_STEPS);
        accessRecipes = new AccessRecipes();
        newRecipe = new Recipe();
        String[] id = new String[1];
        accessRecipes.getNextID(id);
        new ParseRecipe().createRecipe(newRecipe, messages, id[0]);

        setContentView(R.layout.activity_create_step);
    }

    public void onClickDone(View createButton){
        inputSteps = (EditText)findViewById(R.id.createSteps);
        addToFavo = (CheckBox) findViewById(R.id.addToFavourite);

        accessRecipes = new AccessRecipes();


        step = inputSteps.getText().toString();
        if(step.length() > 0){
            newRecipe.addSteps(step);
        }

        accessRecipes.insertRecipe(newRecipe);

        if (addToFavo.isChecked()) {
            accessRecipes.insertFavoRecipe(newRecipe.getRecipeID());
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
            newRecipe.addSteps(step);
            Toast.makeText(this, "Step Added", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Please Enter the Step", Toast.LENGTH_SHORT).show();
        }
    }
}
