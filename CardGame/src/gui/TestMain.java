package gui;
import java.util.*;

import Player.Human;
import Player.Player;
import games.TLMB;

public class TestMain {
	public static void main(String[] args) {
        // Create Human objects
        Human p1 = new Human("1");
        Human p2 = new Human("2");
        Human p3 = new Human("3");
        Human p4 = new Human("4");

        // Create a list of Human objects
        List<Player> players = new LinkedList<>();

        // Add players to the list
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        new TLMB(players);
        
        
        System.exit(0);
	}
}
