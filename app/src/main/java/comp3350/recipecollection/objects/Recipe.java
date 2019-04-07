package comp3350.recipecollection.objects;

import java.util.ArrayList;

public class Recipe
{
    private String recipeID;
    private String recipeName;
    private int recipePosterResource;
    private int time;


    private ArrayList<Ingredient> ingredients;
    private ArrayList<String> steps;
    private ArrayList<String> attributes;
    private String comment;
    private String imageName;

    public Recipe()
    {
        recipeID = "0";
        recipeName = null;
        time = -1;
        recipePosterResource = -1;
        ingredients = new ArrayList<Ingredient>();
        steps = new ArrayList<String>();
        attributes = new ArrayList<String>();
        comment = null;
        imageName = null;
    }

    public Recipe(String myRecipeID, String myRecipeName, int myRecipeTime,
                  ArrayList<Ingredient> myIngredients, ArrayList<String> mySteps,
                  ArrayList<String> myAttributes, String myComment, String myImage)
    {
        recipeID = myRecipeID;
        recipeName = myRecipeName;
        time = myRecipeTime;
        ingredients = myIngredients;
        steps = mySteps;
        comment = myComment;
        attributes = myAttributes ;
        imageName = myImage;
    }

    public String getRecipeID(){ return recipeID; }

    public String getImageName(){
        return imageName;
    }

    public int getRecipePosterResource()
    {
        return recipePosterResource;
    }

    public String getName()
    {
        return recipeName;
    }

    public int getTime()
    {
        return time;
    }

    public ArrayList<Ingredient> getIngredients()
    {
        return ingredients;
    }

    public ArrayList<String> getSteps()
    {
        return steps;
    }

    public ArrayList<String> getAttributes()
    {
        return attributes;
    }

    public String getComment()
    {
        return comment;
    }

    public String getIngredientNamesString()
    {
        Ingredient currentIngredient;
        String resultString = "";
        String currentIngredientName;

        for(int count=0; count<ingredients.size(); count++)
        {
            currentIngredient = ingredients.get(count);
            currentIngredientName = currentIngredient.getName().trim();

            if(count == ingredients.size()-1)
                resultString = resultString+currentIngredientName;
            else
                resultString = resultString+currentIngredientName+",";
        }
        return resultString;
    }

    public String getIngredientAmountsString()
    {
        Ingredient currentIngredient;
        String resultString = "";
        double currentIngredientAmount;

        for(int count=0; count<ingredients.size(); count++)
        {
            currentIngredient = ingredients.get(count);
            currentIngredientAmount = currentIngredient.getAmount();

            if(count == ingredients.size()-1)
                resultString = resultString+currentIngredientAmount;
            else
                resultString = resultString+currentIngredientAmount+",";
        }
        return resultString;
    }

    public String getIngredientUnitsString()
    {
        Ingredient currentIngredient;
        String resultString = "";
        String currentIngredientUnit;

        for(int count=0; count<ingredients.size(); count++)
        {
            currentIngredient = ingredients.get(count);
            currentIngredientUnit= currentIngredient.getUnits().trim();

            if(count == ingredients.size()-1)
                resultString = resultString+currentIngredientUnit;
            else
                resultString = resultString+currentIngredientUnit+",";
        }
        return resultString;
    }

    public String getStepsString()
    {
        String resultString = "";
        String currentStep;

        for(int count = 0; count<steps.size(); count++)
        {
            currentStep = steps.get(count).trim();

            if(count == steps.size()-1)
                resultString = resultString+currentStep;
            else
                resultString=resultString+currentStep+",";
        }

        return resultString;
    }

    public String getAttributesString()
    {
        String resultString = "";
        String currentAttribute;

        for(int count = 0; count<attributes.size(); count++)
        {
            currentAttribute = attributes.get(count).trim();

            if(count == attributes.size()-1)
                resultString = resultString+currentAttribute;
            else
                resultString = resultString+currentAttribute+",";
        }

        return resultString;
    }

    public void setRecipePosterResource(int newRecipePosterResource)
    {
        recipePosterResource = newRecipePosterResource;
    }

    public void setRecipeID(String newRecipeID) { recipeID = newRecipeID; }

    public void setName(String newName)
    {
        recipeName = newName;
    }

    public void setTime(int newTime)
    {
        time = newTime;
    }

    public void setIngredients(ArrayList<Ingredient> newIngredients)
    {
        ingredients = newIngredients;
    }

    public void addIngredients(Ingredient newIngredient)
    {
        ingredients.add(newIngredient);
    }

    public void setSteps(ArrayList<String> newSteps)
    {
        steps = newSteps;
    }

    public void addSteps(String newStep)
    {
        steps.add(newStep);
    }

    public void setAttributes(ArrayList<String> newAttributes)
    {
        attributes = newAttributes;
    }

    public void addAttributes(String newAttribute)
    {
        attributes.add(newAttribute);
    }

    public void setComment(String newComment)
    {
        comment = newComment;
    }

    public void setImageName(String name){
        imageName = name;
    }

    public String toString()
    {
        return "Recipe: " + recipeName + "\n";
    }

    public boolean equals(Object object)
    {
        boolean result, resultOfFor;
        Recipe r;

        result = true;

        if (object != null && object instanceof Recipe)
        {
            r = (Recipe) object;

            if (r.recipeName != null && !r.recipeName.equals(recipeName))
            {
                result = false;
            }

            if(r.time != -1 && r.time < time)
            {
                result = false;
            }

            resultOfFor = false;
            for (int i = 0; i < r.getAttributes().size(); i++) {
                if(attributes.contains(r.getAttributes().get(i)))
                {
                    resultOfFor = true;
                }
            }

            if(!resultOfFor && r.getAttributes().size() > 0)
            {
                result = false;
            }

            resultOfFor = false;
            for (int i = 0; i < r.getIngredients().size(); i++) {

                if (ingredients.contains(r.getIngredients().get(i))) {
                    resultOfFor = true;
                }

            }

            if(!resultOfFor && r.getIngredients().size() > 0)
            {
                result = false;
            }

        }
        else{
            result = false;
        }

        return result;
    }
}
