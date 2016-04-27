import java.util.*;

/**
 *  This class is the main class of the "Hogwarts School of Witchcraft and Wizardry" application.
 *  
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Hannah Dela Cruz
 * @version April 20, 2016
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private int steps = 0;
    private Item currentItem;
    //List<String> inInventory = new ArrayList<String>();
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        //createItems();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room greatHall, kitchen, roomRequirement, charms, potions,herbology, transfiguration, gryffindorCommon, 
        slytherinCommon, ravenclawCommon, hufflepuffCommon, forbiddenForest, diagonAlley, quidditchPitch, blackLake, courtYard;
        
        Item mandrake;
      
        // create the rooms            
        greatHall = new Room("in the Great Hall where students and teachers comes to eat");
        kitchen = new Room("in the kitchen where elves prepare and cook the food ");
        roomRequirement = new Room("in a secret hidden room where things you want the most appears");
        courtYard = new Room("in the courtyard where students hangout");
        charms = new Room("in charms class ");
        potions = new Room("in potions class where students where you learn the correct way to brew potions.");
        transfiguration = new Room("in transfiguration class where you learn the art of changing the form and appearance of an object.");
        herbology = new Room("in herbology class study of magical and mundane plants and fungi.");
        forbiddenForest = new Room("outside the forbidden forest scared to go inside of all the dangerous and unknown creatures in there");
        diagonAlley = new Room("in Diagon Alley where you buy your school materials and other things you want to have");
        quidditchPitch = new Room("in quidditch pitch where the beloved game of quidditch for wizards and witches come to play");
        blackLake = new Room("standing on the edge of the black lake where the black squid resides"); 
        gryffindorCommon = new Room("in the dorm of gryffindor students ");
        slytherinCommon = new Room("in the dorm of slytherin students ");
        hufflepuffCommon = new Room("in the dorm of hufflepuff students ");
        ravenclawCommon = new Room("in the dorm of ravenclaw students");
    
       // initialise room exits
        currentRoom = greatHall;  // start game Great Hall
        
        greatHall.setExit("north", kitchen);
        greatHall.setExit("south", potions);
        greatHall.setExit("east", courtYard);
        greatHall.setExit("west", roomRequirement);
    

        kitchen.setExit("south", greatHall);
        roomRequirement.setExit("north", herbology);
        roomRequirement.setExit("east", greatHall);

        courtYard.setExit("north", diagonAlley);
        courtYard.setExit("south", quidditchPitch);
        courtYard.setExit("east", forbiddenForest);
        courtYard.setExit("west", greatHall);

        diagonAlley.setExit("south", courtYard);
        forbiddenForest.setExit("west", courtYard);
        blackLake.setExit("west", quidditchPitch);
        

        quidditchPitch.setExit("north", courtYard);
        quidditchPitch.setExit("east", blackLake);

        herbology.setExit("north", hufflepuffCommon);
        herbology.setExit("south", roomRequirement);
        herbology.setExit("west", charms);
        //mandrake.setItem("Mandrake plants with roots shape similar to humans.\nWARNING WHEN MATURE CRY CAN BE FATAL!!!", herbolo);

        charms.setExit("south", ravenclawCommon);
        charms.setExit("east", herbology);
        

        potions.setExit("north", greatHall);
        potions.setExit("south", slytherinCommon);
        potions.setExit("west", transfiguration);


        transfiguration.setExit("south", gryffindorCommon);
        transfiguration.setExit("east", potions);


        slytherinCommon.setExit("north", potions);
        gryffindorCommon.setExit("north", transfiguration);
        ravenclawCommon.setExit("north", charms);
        hufflepuffCommon.setExit("south", herbology);      
    }
   
    /**
    public void createItems()
    {
        Item mandrake;
        mandrake = new Item("dsf");
        mandrake.setItem("Mandrake plants with roots shape similar to humans.\nWARNING WHEN MATURE CRY CAN BE FATAL!!!", mandrake);
    }
    */
   
    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) 
        {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
        System.out.println("Number of steps:" + steps);
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to Hogwarts");
        System.out.println("Hogwarts School of Witchcraft and Wizardry");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) 
        {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;
                
            case BACK:
                back(command);
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
                
            case LOOK:
                look(command);
                break;
               
            case EAT:
                eat(command);
                break;
          
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
        System.out.println("around at Hogwarts.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord())
        {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) 
        {
            System.out.println("There is no door!");
        }
        else 
        {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
            steps++;
        }
    }

    
    private void back(Command command)
    {        
        String direction;
        Room prevRoom = currentRoom;
        
        if(prevRoom == null) 
         { 
             System.out.println("Sorry, cannot go back..."); 
             return; 
        }
        else
        {
            currentRoom = prevRoom;
            System.out.println(currentRoom.getLongDescription());
            steps++;
        }
    }
     
    
     private void look(Command command)
     { 
         System.out.println(currentRoom.getLongDescription());
         System.out.println(currentItem.getLongItemDescription());
       
     }
     
     private void eat(Command command)
     {
        // String direction;
         //Room eatRoom = currentRoom.setExit(kitchen);
         System.out.println("You are hungry so you stopped by the kitchen to get food.");
         System.out.println("You have eaten a grilled cheese sandwhich with a baked potato soup with butterbeer.");
     }
  
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) 
        {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
