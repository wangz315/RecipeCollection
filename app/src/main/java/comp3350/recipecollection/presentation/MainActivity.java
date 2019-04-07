package comp3350.recipecollection.presentation;

import comp3350.recipecollection.R;
import comp3350.recipecollection.application.Main;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;


public class MainActivity extends Activity {
    public Button viewAllButton;
    public Button searchButton;
    public Button createButton;
    public Button viewFavoButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        copyDatabaseToDevice();
        Main.startUp();
        setContentView(R.layout.activity_main);

        viewFavoInit();
        createActivityInit();
        viewAllRecipeInit();
        searchActivityInit();
    }

    public void viewFavoInit(){
        viewFavoButton = (Button)findViewById(R.id.viewFavoButton);
        viewFavoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent viewFavo = new Intent(MainActivity.this, ViewRecipeActivity.class);
                viewFavo.putExtra(Messages.MESSAGE_VIEW, new String[]{"", "", "", "", Messages.FAVO});
                startActivity(viewFavo);
            }
        });
    }

    public void createActivityInit()
    {
        createButton = (Button)findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent create = new Intent(MainActivity.this, CreateBasicActivity.class);
                startActivity(create);
            }
        });
    }

    public void viewAllRecipeInit(){
        viewAllButton = (Button)findViewById(R.id.viewAllButton);
        viewAllButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent viewAll = new Intent(MainActivity.this, ViewRecipeActivity.class);
                viewAll.putExtra(Messages.MESSAGE_VIEW, new String[]{"", "", "", "", Messages.DATABASE});
                startActivity(viewAll);
            }
        });
    }



    public void searchActivityInit()
    {
        searchButton = (Button)findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent search = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(search);
            }
        });
    }

    private void copyDatabaseToDevice() {
        final String DB_PATH = "db";

        String[] assetNames;
        Context context = getApplicationContext();
        File dataDirectory = context.getDir(DB_PATH, Context.MODE_PRIVATE);
        AssetManager assetManager = getAssets();

        try {

            assetNames = assetManager.list(DB_PATH);
            for (int i = 0; i < assetNames.length; i++) {
                assetNames[i] = DB_PATH + "/" + assetNames[i];
            }

            copyAssetsToDirectory(assetNames, dataDirectory);

            Main.setDBPathName(dataDirectory.toString() + "/" + Main.dbName);

        } catch (IOException ioe) {
            Messages.warning(this, "Unable to access application data: " + ioe.getMessage());
        }
    }

    public void copyAssetsToDirectory(String[] assets, File directory) throws IOException {
        AssetManager assetManager = getAssets();

        for (String asset : assets) {
            String[] components = asset.split("/");
            String copyPath = directory.toString() + "/" + components[components.length - 1];
            char[] buffer = new char[1024];
            int count;

            File outFile = new File(copyPath);

            if (!outFile.exists()) {
                InputStreamReader in = new InputStreamReader(assetManager.open(asset));
                FileWriter out = new FileWriter(outFile);

                count = in.read(buffer);
                while (count != -1) {
                    out.write(buffer, 0, count);
                    count = in.read(buffer);
                }

                out.close();
                in.close();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Main.shutDown();
    }
}
