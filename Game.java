import java.util.Stack;
import java.util.ArrayList;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class Game 
{
    private Parser parser;
    private Player player;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        player = new Player();
        player.setCurrentRoom(createRooms());
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private Room createRooms()
    {
        Room habitacionInicial, habitacionEste, habitacionDorada, habitacionSur, habitacionOeste, habitacionDelBoss, tiendaDeObjetos;

        // create the rooms
        habitacionInicial = new Room("Estas en la sala en la que te despertaste" + "\n" + 
            "despues del golpe...");
        habitacionEste = new Room("Es una sala Lugubre, llena de humedad, tambien" + "\n" + 
            "hay una puerta en el norte...");
        habitacionDorada = new Room("Es una habitacion dorada, mi abuelo me habl�" + "\n" + 
            "de ella, dijo que guarda un secreto, ser� mejor no tocar nada...");
        habitacionSur = new Room("Esta sala esta llena de telara�as, tambien veo" + "\n" + 
            "unos huesos en el suelo, aqui no hay nada, y da mucho canguele, mejor irse...");
        habitacionOeste = new Room("Esta sala parece un pasillo mas que una sala," + "\n" + 
            "hacia el norte hay una puerta roja, con manchas de sangre en el suelo y al" + "\n" + 
            "sur hay un cartel lumnoso que dice \"Las mejores compras del subsuelo," + "\n" + 
            "adelante, siempre esta abierto\", bastante extra�o...");
        habitacionDelBoss = new Room("... TENGO MIEDO, hay un monstruo enorme con" + "\n" + 
            "un hacha gigante... parece dormido, eso es bueno, sera mejor no despertarlo...");
        tiendaDeObjetos = new Room("Parece una tienda de barrio, pero de la edad media," + "\n" + 
            "hay todo tipo de objetos, una espada, un hacha, un escudo... son todo armas... y no hay nadie, ser� mejor no tocar nada...");
        //Add the items to the rooms
        habitacionDorada.addItem(new Item("Cofre dorado", 5000, "Cofre", false));
        tiendaDeObjetos.addItem(new Item("Una espada", 2000, "Espada", true));
        habitacionSur.addItem(new Item("Un escudo", 5000, "Escudo", true));
        habitacionEste.addItem(new Item("Un anillo", 500, "anillo", true));
        // initialise room exits
        // habitacionInicial
        habitacionInicial.setExit("west", habitacionOeste);
        habitacionInicial.setExit("south", habitacionSur);
        habitacionInicial.setExit("east", habitacionEste);
        // habitacionEste
        habitacionEste.setExit("north", habitacionDorada);
        habitacionEste.setExit("west", habitacionInicial);
        // habitacionDorada
        habitacionDorada.setExit("south", habitacionEste);
        // habitacionSur
        habitacionSur.setExit("north", habitacionInicial);
        habitacionSur.setExit("northWest", habitacionOeste);
        // habitacionOeste
        habitacionOeste.setExit("north", habitacionDelBoss);
        habitacionOeste.setExit("east", habitacionInicial);
        habitacionOeste.setExit("southEast", habitacionSur);
        habitacionOeste.setExit("south", tiendaDeObjetos);
        // habitacionDelBoss
        habitacionDelBoss.setExit("south", habitacionOeste);
        // tiendaDeObjetos
        tiendaDeObjetos.setExit("north", habitacionOeste);

        return habitacionInicial;
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {    

        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        player.look();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            player.goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look")) {
            player.look();
        }
        else if (commandWord.equals("eat")) {
            player.eat();
        }
        else if (commandWord.equals("back")) {
            player.back();
        }
        else if (commandWord.equals("take")) {
            player.take(command);
        }
        else if (commandWord.equals("drop")) {
            player.drop(command);
        }
        else if (commandWord.equals("items")) {
            player.items();
        }
        else if (commandWord.equals("put")){
            player.put();
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
}