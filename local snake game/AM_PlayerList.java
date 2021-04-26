/**
 * Write a description of class Player here.
 *
 * @author Abdul Majid
 * @version 24-04-2021
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;

public class AM_PlayerList
{
    // list of player objects
    private ArrayList<AM_Player> playerList;
 
    /**
     * Constructor for objects of class Player
     */
    public AM_PlayerList() {
        // initialise the player list
        playerList = new ArrayList<>();
    }

    /**
     * Method to add a Player
     * @params name, password
     * @return none
     */
    public void add(String name, String password) {
        AM_Player player = new AM_Player(name, password);
        playerList.add(player);
    }

    /**
     * Method to sort PlayerList
     * @params direction (A - Acsending order, D - Descending order)
     * @return none
     */
    public void sort(char direction) {
        //modify this method to makes it works as expected
        if (direction == 'A') {
            //sort the list in ascending order
            Collections.sort(playerList);
        } else if (direction == 'D')        
        {
            //sort the list in descending order
            Collections.sort(playerList, Collections.reverseOrder());
        }
    }

    /**
     * Method to match a player in the list with a given name and password
     * @param name, password
     * @return true if matched and false if not
     */
    public boolean matchPlayer(String name, String password) {
        boolean match;
        match = false;
        
        Iterator<AM_Player> iter = playerList.iterator();
        while (iter.hasNext()) {
            AM_Player player = iter.next();
            if ((player.name.compareTo(name)==0) && (player.password.compareTo(password)==0)) {
               match = true;
               break;
            }
        }        
        
        return match;
    }
    
    /**
     * Method to convert the PlayerList to a string
     * @params none
     * @return a string that represents all players in the list
     */
    @Override
    public String toString() {
        //modify this method to makes it works as expected
        String s = "";
        /*
        for (int i = 0; i < playerList.size(); i++) {
            Player player = playerList.get(i);
                s = s + player.toString() + "\n";
        }
        */
        Iterator<AM_Player> iter = playerList.iterator();
        while (iter.hasNext()) {
            AM_Player player = iter.next();
            s = s + player.toString() + "\n";
        }
        return s;
    }
    
    /**
     * This private class for storing player's details 
     */
    class AM_Player implements Comparable<AM_Player> {
        public String name;
        public String password;
        
        public AM_Player(String name, String password) {
            this.name = name;
            this.password = password;
        }
        
        @Override
        public String toString() {
            return name + "[pass=" + password + "]";
        }
 
        @Override
        public int compareTo(AM_Player p) {
            return this.name.compareTo(p.name);
        }
    }
}
