import java.util.HashMap;
/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */
public class Room 
{
    private String description;
    private HashMap<String, Room> exits;
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
    }
    
    public void setExit(String direccion, Room nextRoom){
        exits.put(direccion, nextRoom);
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    public Room getExit(String direccion){
        
        return exits.get(direccion);
    }

    public String getExitString(){
        String txtADevolver = "Exits: ";
        if(exits.get("north") != null){
            txtADevolver += "north ";
        }
        if(exits.get("east") != null){
            txtADevolver += "east ";
        }
        if(exits.get("west") != null){
            txtADevolver += "west ";
        }
        if(exits.get("south") != null){
            txtADevolver += "south ";
        }
        if(exits.get("southEast") != null){
            txtADevolver += "southEast ";
        }
        if(exits.get("northWest") != null){
            txtADevolver += "northWest ";
        }

        return txtADevolver;
    }
}
