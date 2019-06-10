import java.util.ArrayList;
import java.util.Stack;
public class Player
{
    // instance variables - replace the example below with your own
    private Room currentRoom;
    private Stack<Room> roomStack;
    private ArrayList<Item> bag;
    private int bagWeigth;
    private static final int MAXWEIGTH = 6000;
    /**
     * Constructor for objects of class Player
     */
    public Player()
    {
        // initialise instance variables
        currentRoom = null;
        roomStack = new Stack<Room>();
        bag = new ArrayList<>();
        bagWeigth = 0;
    }

    public void setCurrentRoom(Room room){
        currentRoom = room;
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    public void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("No hay ninguna puerta!");
        }
        else {
            roomStack.push(currentRoom);
            // Try to leave current room.
            currentRoom = currentRoom.getExit(direction);
            look();
        }
    }

    /**
     * Print out a look of the actual location.
     */
    public void look() 
    {
        System.out.println(currentRoom.getLongDescription());
        if (currentRoom.getDescription().equals("La terraza exterior, esta lloviendo")){
            System.out.println("Has encontrado y asesinado a tu victima. Felicidades!!");
        }
    }

    /** 
     * Intenta ir a la anterior habitacion. El jugador no debe cambiar de localización si este comando
     * se invoca al inicio o si se invoca dos o más veces seguidas sin haber ejecutado el comando go entre ellas.
     */
    public void back() 
    {
        if (!roomStack.empty()){
            currentRoom = roomStack.pop();
            look();
        }
        else{
            System.out.println("No puedes volver atras, estas en la posicion inicial");
        }
    }

    /**
     * Eat some food
     */
    public void eat() 
    {
        System.out.println("You have eaten now and you are not hungry any more");
    }
    
    public void take(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know the item to take...
            System.out.println("No has indicado el ID del objeto a coger");
            return;
        }
        String positionItem = command.getSecondWord();
        Item itemToTake = currentRoom.getItem(positionItem);

        if (itemToTake != null && bagWeigth + itemToTake.getWeigth() < MAXWEIGTH){
            System.out.println("Has cogido el siguiente objeto:" + "\n");
            System.out.println(itemToTake.getItem());
            bagWeigth += itemToTake.getWeigth();
            bag.add(itemToTake);
            currentRoom.removeItem(itemToTake);
        }

        else{
            if (itemToTake == null){
                System.out.println("No hay objetos en la habitacion");
            }
            else{
                System.out.println("Te has pasado del peso de la mochila");
            }
        }
    }
    
    public void drop(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know the item to take...
            System.out.println("No has indicado el ID del objeto a dejar");
        }
        String itemID = command.getSecondWord();
        Item itemToDrop = null;
        int cont = 0;
        while (itemToDrop == null && bag.size() > cont){
            if (bag.get(cont).getItemId().equals(itemID)){
                itemToDrop = bag.get(cont);
            }
            cont++;
        }

        if (bag.size() > 0 && itemToDrop != null){
            System.out.println("Has dejado el siguiente objeto:" + "\n");
            System.out.println(itemToDrop.getItem());
            bagWeigth -= itemToDrop.getWeigth();
            bag.remove(itemToDrop);
            currentRoom.addItem(itemToDrop);
        }
        else{
            System.out.println("No hay objetos en la mochila para dejar");
        }
    }
    
    public void items(){
        if (bag.size() > 0){
            System.out.println("Llevas: ");
            for (int i = 0; i < bag.size(); i++){
                System.out.println(bag.get(i).getItem());
            }
        }
        else{
            System.out.println("Tu mochila esta vacia");
        }
    }
}