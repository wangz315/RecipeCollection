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
import comp3350.recipecollection.objects.Recipe;

public class EditBasicActivity extends AppCompatActivity {
    private String[] messages;
    private String name, time, attr;
    private EditText inputName, inputTime, inputAttr;
    private AccessRecipes accessRecipes;
    private Recipe targetRecipe;
    private ArrayList<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accessRecipes = new AccessRecipes();
        Intent intent = getIntent();
        messages = intent.getStringArrayExtra(Messages.MESSAGE_EDIT_BASIC);
        setContentView(R.layout.activity_create_basic);

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


    public void onClickNextBasic(View createButton){
        inputName = (EditText)findViewById(R.id.createBasicName);
        inputTime = (EditText)findViewById(R.id.createBasicTime);
        inputAttr = (EditText)findViewById(R.id.createBasicAttr);

        name = inputName.getText().toString().trim();
        time = inputTime.getText().toString().trim();
        attr = inputAttr.getText().toString().trim();

        new ParseRecipe().editBasic(targetRecipe, name, time, attr);

        accessRecipes.updateRecipe(targetRecipe);

        Intent editIngre = new Intent(this, EditIngreActivity.class);
        editIngre.putExtra(Messages.MESSAGE_EDIT_INGRE, messages);
        startActivity(editIngre);
    }

}
