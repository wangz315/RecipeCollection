package comp3350.recipecollection.business;


import android.widget.EditText;

import comp3350.recipecollection.objects.Ingredient;
import comp3350.recipecollection.objects.Recipe;
import comp3350.recipecollection.presentation.Messages;

public class BuildString {

    public String buildIngreString(String name, String amt, String unit){
        String message = null;


        if(!(new ParseRecipe()).isParsableDouble(amt)){
            amt = "0";
        }

        if(!name.contains("_") && !amt.contains("_") && !unit.contains("_")) {
            message = name + "_" + amt + "_" + unit + ",";
        }

        return message;
    }


    public String detailNameBuilder(Recipe recipe){
        return ("Name:\n" + recipe.getName());
    }


    public String detailAttrBuilder(Recipe recipe){
        String attrString = "Attributes:\n";

        if (recipe.getAttributes().size() > 0) {
            for (String details : recipe.getAttributes()) {
                attrString += (details + "\n");
            }
        }


        return attrString;
    }


    public String detailStepBuilder(Recipe recipe){
        String stepString = "Steps:\n";

        if (recipe.getSteps().size() > 0) {
            for (String details : recipe.getSteps()) {
                stepString += (details + "\n");
            }
        }

        return stepString;
    }


    public String detailIngreBuilder(Recipe recipe){
        String ingreString = "Ingredients:\n";

        if (recipe.getIngredients().size() > 0) {
            for (Ingredient i : recipe.getIngredients()) {
                ingreString += (i.toString() + "\n");
            }
        }

        return ingreString;
    }


    public String detailCommentBuilder(Recipe recipe){
        String commentString;

        if (recipe.getComment() != null && !recipe.getComment().trim().equals("")) {
            commentString = "Comment:\n" + recipe.getComment();
        } else {
            commentString = "Comment:\n" + "No Comment.";
        }

        return commentString;
    }


    public String detailTimeBuilder(Recipe recipe){
        String timeString;

        if (recipe.getTime() > -1) {
            timeString = "Time Cost:\n" + recipe.getTime();
        } else {
            timeString = "Time Cost:\n" + Messages.UNKNOWN;
        }

        return timeString;
    }


}
