package comp3350.recipecollection.objects;


public class Ingredient{

    private String name;
    private double amount;
    private String units;

    public Ingredient()
    {
        name = null;
        amount = 0;
        units = null;
    }

    public Ingredient(String newName, double newAmount, String newUnits)
    {
        name = newName;
        amount = newAmount;
        units = newUnits;
    }

    public String getName()
    {
        return name;
    }

    public double getAmount()
    {
        return amount;
    }

    public String getUnits()
    {
        return units;
    }

    public void setName(String newName)
    {
        name = newName;
    }

    public void setAmount(double newAmount)
    {
        amount = newAmount;
    }

    public void setUnits(String newUnits)
    {
        units = newUnits;
    }

    public boolean equals(Object object)
    {
        boolean result;
        Ingredient i;

        result = false;

        if(object instanceof Ingredient)
        {
            i = (Ingredient) object;

            if(i.name.equals(name))
            {
                result = true;
            }
        }

        return result;
    }

    public String toString()
    {
        String str = String.format("%-10s %4.1f %s", name, amount, units);
        return  str;
    }
}
