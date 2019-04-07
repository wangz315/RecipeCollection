package comp3350.recipecollection.business;

import android.widget.EditText;

import java.util.ArrayList;

import comp3350.recipecollection.R;
import comp3350.recipecollection.objects.Recipe;
import comp3350.recipecollection.objects.Ingredient;
import comp3350.recipecollection.presentation.Messages;

public class ParseRecipe {
    private String[] ingredients;
    private String[] attributes;

    //parse message to a recipe object for compare
    public void parseToRecipe(String[] messages, Recipe targetRecipe){


        if(messages[0].trim().length() > 0) {
            targetRecipe.setName(messages[0].trim());
        }

        if(messages[1].trim().length() > 0 && isParsableInt(messages[1].trim())){
            targetRecipe.setTime(Integer.parseInt(messages[1]));
        }

        ingredients = messages[2].split(",");

        for(int i = 0; i < ingredients.length; i++){
            if(ingredients[i].trim().length() > 0){
                targetRecipe.addIngredients(new Ingredient(ingredients[i].trim(), 0, ""));
            }
        }

        attributes = messages[3].split(",");

        for(int i = 0; i < attributes.length; i++){
            if(attributes[i].trim().length() > 0){
                targetRecipe.addAttributes(attributes[i].trim());
            }
        }
    }

    public boolean isParsableInt(String input){
        boolean parsable = true;
        try{
            Integer.parseInt(input);
        }catch(NumberFormatException e){
            parsable = false;
        }
        return parsable;
    }

    public boolean isParsableDouble(String input){
        boolean parsable = true;
        if(input != null) {

            try {
                Double.parseDouble(input);
            } catch (NumberFormatException e) {
                parsable = false;
            }

        }
        else{
            parsable = false;
        }
        return parsable;
    }

    public String createRecipe(Recipe newRecipe, String[] messages, String nextID){
        String[] ingreEntire;
        String[] ingreArray;
        Ingredient ingre;


        newRecipe.setRecipeID(nextID);

        newRecipe.setName(messages[0].trim());

        if((new ParseRecipe().isParsableInt(messages[1].trim()))) {
            newRecipe.setTime(Integer.parseInt(messages[1].trim()));
        }

        ingreEntire = messages[2].split(",");

        for(String ie: ingreEntire) {
            ingreArray = ie.split("_");
            ingre = new Ingredient();
            if(ingreArray.length > 2) {
                ingre.setName(ingreArray[0].trim());
                ingre.setUnits(ingreArray[2].trim());

                if ((new ParseRecipe()).isParsableDouble(ingreArray[1].trim())) {
                    ingre.setAmount(Double.parseDouble(ingreArray[1].trim()));
                }

                newRecipe.addIngredients(ingre);
            }
        }

        String[] attributes = messages[3].split(",");
        for(int i = 0; i < attributes.length; i++){
            if(attributes[i].trim().length() > 0){
                newRecipe.addAttributes(attributes[i].trim());
            }
        }

        newRecipe.setImageName(Messages.NOIMAGE);
        newRecipe.setRecipePosterResource(R.drawable.noimage);

        return null;
    }

    public String editBasic(Recipe recipe, String name, String time, String attr){
        String[] attrsElements;
        ArrayList<String> attrs = new ArrayList<String>();

        if(name.length() > 0){
            recipe.setName(name);
        }

        if((new ParseRecipe().isParsableInt(time))){
            recipe.setTime(Integer.parseInt(time));
        }

        attrsElements = attr.split(",");
        for(String a: attrsElements){
            attrs.add(a);
        }

        recipe.setAttributes(attrs);

        return null;
    }


    public Ingredient buildIngreObject(String name, String amt, String unit){
        Ingredient ingredient = null;


        if(!(new ParseRecipe()).isParsableDouble(amt)){
            amt = "0";
        }

        if(!name.contains("_") && !amt.contains("_") && !unit.contains("_")) {
            ingredient = new Ingredient(name, Double.parseDouble(amt), unit);
        }

        return ingredient;
    }
}
