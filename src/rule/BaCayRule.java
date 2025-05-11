package rule;

import java.util.ArrayList;

import utilities.card.Card;
import utilities.player.Player;

public class BaCayRule {
	 public int calculateValue(Player p) {
	     int score = 0;
	     for (Card c : p.getCards()) {
	    	 score += c.getCost();
	     }
	     score = score % 10;
	     if(score == 0) {
	    	 score = 10;
	     }
	     
	     return score;
	 }
	 
	 public int getWinner(ArrayList<Player> p) {
		 int bestPlayer = 0;
	     int highestScore = -1;

	     for (int i = 0; i < p.size(); i++) {
	    	 int score = calculateValue(p.get(i));
	    	 if (score > highestScore) {
	    		 highestScore = score;
	             bestPlayer = i;
	         }
	    	 else if(score == highestScore) {
	    		 int len = p.get(bestPlayer).getNumCards();
	    		 if(p.get(i).getCard(len - 1).compare(p.get(bestPlayer).getCard(len - 1)) > 0) {
	    			 bestPlayer = i;
	    		 }
	    	 }
	     }
	     return bestPlayer;
	 }
}
