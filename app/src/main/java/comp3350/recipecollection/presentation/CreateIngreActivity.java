package comp3350.recipecollection.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import comp3350.recipecollection.R;
import comp3350.recipecollection.business.BuildString;

public class CreateIngreActivity extends AppCompatActivity {

    private String[] messages;
    private EditText inputName, inputAmt, inputUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        messages = intent.getStringArrayExtra(Messages.MESSAGE_NEXT_BASIC);
        setContentView(R.layout.activity_create_ingre);
    }

    public void onClickNextIngre(View createButton){
        inputName = (EditText)findViewById(R.id.createIngreName);


        if(inputName.getText().toString().trim().length() > 0) {
            addIngre();
        }

        Intent createSteps = new Intent(this, CreateStepActivity.class);
        createSteps.putExtra(Messages.MESSAGE_NEXT_STEPS, messages);
        startActivity(createSteps);

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
        String messageString;
        String name, amt, unit;

        inputName = (EditText)findViewById(R.id.createIngreName);
        inputAmt = (EditText)findViewById(R.id.createIngreAmt);
        inputUnit = (EditText)findViewById(R.id.createIngreUnit);

        name = inputName.getText().toString().trim();
        amt = inputAmt.getText().toString().trim();
        unit = inputUnit.getText().toString().trim();

        messageString = (new BuildString()).buildIngreString(name, amt, unit);

        if(messageString != null) {
            messages[2] += messageString;
            Toast.makeText(this, "Ingredient Added", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "_ is an invalid character, try others", Toast.LENGTH_SHORT).show();
        }
    }
}
