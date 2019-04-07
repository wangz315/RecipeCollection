package comp3350.recipecollection.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import comp3350.recipecollection.R;
import comp3350.recipecollection.business.AccessRecipes;
import comp3350.recipecollection.objects.Recipe;

public class CommentActivity extends AppCompatActivity {
    private EditText inputComment;
    private String[] messages;
    private AccessRecipes accessRecipes;
    private ArrayList<Recipe> resultRecipes;
    private Recipe targetRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        messages = intent.getStringArrayExtra(Messages.MESSAGE_COMMENT);
        setContentView(R.layout.activity_comment);
        accessRecipes = new AccessRecipes();
        resultRecipes = new ArrayList<Recipe>();
        accessRecipes.searchRecipe(messages[0], resultRecipes);
        targetRecipe = null;
        if(resultRecipes.size() > 0) {
            targetRecipe = resultRecipes.get(0);
            inputComment = (EditText) findViewById(R.id.editComment);

            inputComment.setText(targetRecipe.getComment());
        }
        else {
            Toast.makeText(this, "Cannot Find the Recipe", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickCommentDone(View v){
        Intent detailPageIntent;

        if(targetRecipe != null) {
            targetRecipe.setComment(inputComment.getText().toString().trim());
            accessRecipes.updateRecipe(targetRecipe);

            messages = new String[]{targetRecipe.getRecipeID()};

            detailPageIntent = new Intent(this, DetailPageActivity.class);
            detailPageIntent.putExtra(Messages.MESSAGE_DETL, messages);
            startActivity(detailPageIntent);
        }
        else {
            Toast.makeText(this, "Cannot Find the Recipe", Toast.LENGTH_SHORT).show();
        }
    }
}
