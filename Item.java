//Cada habitacion puede tener o no un objeto

public class Item{
    private String description;
    private int itemWeigth;
    
    public Item(String description, int weigth)
    {
        this.description = description;
        this.itemWeigth  = itemWeigth;
    }
    
    public String getItem()
    {
        return description + " " + itemWeigth + " gramos";
    }
}
