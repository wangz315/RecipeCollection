package comp3350.recipecollection.presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import comp3350.recipecollection.R;
import comp3350.recipecollection.business.BuildString;
import comp3350.recipecollection.objects.Ingredient;
import comp3350.recipecollection.objects.Recipe;
import comp3350.recipecollection.business.AccessRecipes;

public class DetailPageActivity extends AppCompatActivity{
    private TextView showName, showCommon, showAttribute, showSteps, showTime,showIngredient;
    private ImageView showImage;
    private String[] messages;
    private AccessRecipes accessRecipes;
    private ArrayList<Recipe> recipeList;
    private Recipe resultRecipe;
    private boolean[] inFavo;
    private Button addButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resultRecipe = null;
        inFavo = new boolean[1];
        ArrayList<Recipe> resultList = new ArrayList<Recipe>();
        setContentView(R.layout.activity_detail_page);
        accessRecipes = new AccessRecipes();
        recipeList = new ArrayList<Recipe>();

        final String result = accessRecipes.getRecipes(recipeList);
        if (result != null)
        {
            Messages.fatalError(this, result);
        }
        else {
            Intent intent = getIntent();
            messages = intent.getStringArrayExtra(Messages.MESSAGE_DETL);

            accessRecipes.searchRecipe(messages[0].trim(), resultList);
            if(resultList.size() > 0) {
                resultRecipe = resultList.get(0);

                showName = (TextView) findViewById(R.id.DetailName);
                showSteps = (TextView) findViewById(R.id.DetailSteps);
                showAttribute = (TextView) findViewById(R.id.DetailAttribute);
                showCommon = (TextView) findViewById(R.id.DetailComment);
                showTime = (TextView) findViewById(R.id.DetailTime);
                showIngredient = (TextView) findViewById(R.id.DetailIngredient);
                showImage = (ImageView) findViewById(R.id.DetailImage);


                showName.setText(new BuildString().detailNameBuilder(resultRecipe));
                showAttribute.setText(new BuildString().detailAttrBuilder(resultRecipe));
                showSteps.setText(new BuildString().detailStepBuilder(resultRecipe));
                showCommon.setText(new BuildString().detailCommentBuilder(resultRecipe));
                showTime.setText(new BuildString().detailTimeBuilder(resultRecipe));
                showIngredient.setText(new BuildString().detailIngreBuilder(resultRecipe));


                resultRecipe.setRecipePosterResource(getResources().getIdentifier(resultRecipe.getImageName(),
                        "drawable", this.getPackageName()));
                showImage.setImageResource(resultRecipe.getRecipePosterResource());

                accessRecipes.containFavo(resultRecipe.getRecipeID(), inFavo);
                if (inFavo[0]) {
                    addButton = (Button) findViewById(R.id.addFavoButton);
                    addButton.setText("REMOVE FROM FAVORITE");
                }
            }
            else {
                Toast.makeText(this, "Cannot Find the Recipe", Toast.LENGTH_SHORT).show();
                Intent mainActivity = new Intent(this, MainActivity.class);
                startActivity(mainActivity);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onClickAddFavo(View view) {
        if(resultRecipe != null) {
            if (inFavo[0]) {
                accessRecipes.deleteFavoRecipe(resultRecipe.getRecipeID());
                Toast.makeText(this, "Removed from Favorite", Toast.LENGTH_SHORT).show();
            } else {
                accessRecipes.insertFavoRecipe(resultRecipe.getRecipeID());
                Toast.makeText(this, "Added to Favorite", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Cannot Find the Recipe", Toast.LENGTH_SHORT).show();
            Intent mainActivity = new Intent(this, MainActivity.class);
            startActivity(mainActivity);
        }

        resultRecipe = null;

        Intent viewFavo = new Intent(this, ViewRecipeActivity.class);
        viewFavo.putExtra(Messages.MESSAGE_VIEW, new String[]{"", "", "", "", Messages.FAVO});
        startActivity(viewFavo);
    }

    public void onClickDelete(View view){
        if(resultRecipe != null) {
            accessRecipes.deleteRecipe(resultRecipe);
            accessRecipes.deleteFavoRecipe(resultRecipe.getRecipeID());
            Toast.makeText(this, "Recipe Removed", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Cannot Find the Recipe", Toast.LENGTH_SHORT).show();
        }

        resultRecipe = null;

        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }


    public void onClickComment(View view){
        String[] messagesComment;
        if(resultRecipe != null) {

            Intent commentActivity = new Intent(this, CommentActivity.class);
            messagesComment = new String[]{resultRecipe.getRecipeID()};
            commentActivity.putExtra(Messages.MESSAGE_COMMENT, messagesComment);
            startActivity(commentActivity);
        }
        else{
            Toast.makeText(this, "Cannot Find the Recipe", Toast.LENGTH_SHORT).show();
        }
    }


    public void onClickEdit(View view){
        String[] messagesEdit;

        if(resultRecipe != null) {

            Intent editActivity = new Intent(this, EditBasicActivity.class);
            messagesEdit = new String[]{resultRecipe.getRecipeID()};
            editActivity.putExtra(Messages.MESSAGE_EDIT_BASIC, messagesEdit);
            startActivity(editActivity);
        }
        else{
            Toast.makeText(this, "Cannot Find the Recipe", Toast.LENGTH_SHORT).show();
        }
    }
}
