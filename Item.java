
//Cada habitacion puede tener o no un objeto

public class Item{
    private String description;
    private int itemWeigth;
    private String id;
    public Item(String description, int itemWeigth, String id)
    {
        this.description = description;
        this.itemWeigth  = itemWeigth;
        this.id  = id;
    }
    
    public String getItem()
    {
        return id + ": " + description + " " + itemWeigth + " gramos";
    }
    
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
