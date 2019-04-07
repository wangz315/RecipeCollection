package comp3350.recipecollection.presentation;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

import comp3350.recipecollection.business.AccessRecipes;
import comp3350.recipecollection.objects.Recipe;

public class CLI {
    public static BufferedReader console;
    public static String inputLine;
    public static String[] inputTokens;

    public static Recipe currentRecipe;

    public static void run()
    {
        try
        {
            console = new BufferedReader(new InputStreamReader(System.in));
            process();
            console.close();
        }
        catch (IOException ioe)
        {
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }
    }

    public static void process()
    {
        readLine();
        while ((inputLine != null) && (!inputLine.equalsIgnoreCase("exit"))
                && (!inputLine.equalsIgnoreCase("quit"))
                && (!inputLine.equalsIgnoreCase("q"))
                && (!inputLine.equalsIgnoreCase("bye")))
        {
            inputTokens = inputLine.split("\\s+");
            parse();
            readLine();
        }
    }


    public static void readLine()
    {
        try
        {
            System.out.print(">");
            inputLine = console.readLine();
        }
        catch (IOException ioe)
        {
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }
    }

    public static void parse()
    {
        if (inputTokens[0].equalsIgnoreCase("get"))
        {
            processGet();
        }
        else
        {
            System.out.println("Invalid command.");
        }
    }

    public static void processGet()
    {
        if (inputTokens[1].equalsIgnoreCase("Recipe"))
        {
            processGetRecipe();
        }
        else
        {
            System.out.println("Invalid data type");
        }
    }

    public static void processGetRecipe()
    {
        AccessRecipes accessRecipes;

        accessRecipes = new AccessRecipes();

        currentRecipe = accessRecipes.getSequential();
        while (currentRecipe != null)
        {
            System.out.println(currentRecipe);
            currentRecipe = accessRecipes.getSequential();
        }

    }
}
