package comp3350.recipecollection.presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import comp3350.recipecollection.R;

public class SearchActivity extends AppCompatActivity {

    private EditText messageName, messageTime, messageIng, messageAttr;
    private Intent searchResultActivity;
    private String[] messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    public void onClickSearch(View view)
    {
        messageName = (EditText)findViewById(R.id.messageName);
        messageTime = (EditText)findViewById(R.id.messageTime);
        messageIng = (EditText)findViewById(R.id.messageIng);
        messageAttr = (EditText)findViewById(R.id.messageAttr);

        searchResultActivity = new Intent(this, ViewRecipeActivity.class);
        messages = new String[]{messageName.getText().toString(), messageTime.getText().toString(),
                messageIng.getText().toString(), messageAttr.getText().toString(), Messages.DATABASE};

        searchResultActivity.putExtra(Messages.MESSAGE_VIEW, messages);
        startActivity(searchResultActivity);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
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
