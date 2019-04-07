package comp3350.recipecollection.presentation;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import comp3350.recipecollection.R;

public class CreateBasicActivity extends AppCompatActivity {

    private String[] messages;
    private String name, time, attr;
    private EditText inputName, inputTime, inputAttr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_basic);
    }

    public void onClickNextBasic(View createButton){

        inputName = (EditText)findViewById(R.id.createBasicName);
        inputTime = (EditText)findViewById(R.id.createBasicTime);
        inputAttr = (EditText)findViewById(R.id.createBasicAttr);

        name = inputName.getText().toString().trim();
        if(name.length() == 0){
            name = Messages.UNKNOWN;
        }
        time = inputTime.getText().toString().trim();
        attr = inputAttr.getText().toString().trim();

        messages = new String[]{name, time, "", attr, ""};

        Intent createIngre = new Intent(this, CreateIngreActivity.class);
        createIngre.putExtra(Messages.MESSAGE_NEXT_BASIC, messages);
        startActivity(createIngre);
    }
}
