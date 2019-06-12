
//Cada habitacion puede tener o no un objeto

public class Item{
    private String description;
    private int itemWeigth;
    private String id;
    private boolean canBePickedUp;
    public Item(String description, int weigth, String id, boolean canBePickedUp)
    {
        this.description = description;
        this.itemWeigth = weigth;
        this.id = id;
        this.canBePickedUp = canBePickedUp;
    }

    public String getItem()
    {
        return description + " " + itemWeigth + " gramos" + " // ID: " + id;    }

    public String getItemId()
    {
        return id;
    }

    public String getdescription()
    {
        return description;
    }

    public int getWeigth()
    {
        return itemWeigth;
    }
    
    
}