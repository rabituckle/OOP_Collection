package utilities.card;

import java.util.ArrayList;
import java.util.Random;

public class CardPlayingCard extends Card implements Comparable<CardPlayingCard> {
    private static String[] suit = {"S", "C", "D", "H"}; // ♠, ♣, ♦, ♥
    private static String[] val = {"3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A", "2"};
    private String type, rank;
    private int cost;
    private String value;

    public CardPlayingCard(String suit, String rank) {
        this.type = suit;
        this.rank = rank;
        for(int i = 0; i < 13; i++) {
        	if(this.val[i].equals(rank)) {
        		this.cost = i + 3;
        		break;
        	}
        }
        value = rank + suit;
    }

    // Override toString to show value and suit correctly
    @Override
    public String toString() {
        return rank + type;
    }
    
    @Override
    public String getValue() {
        return value;
    }
    
    public int getCost() {
    	return cost;
    }
    
    public String getSuit() {
    	return type;
    }
    
    public boolean checkColorSuit(CardPlayingCard c) {
    	int thisType = -1, otherType = -1;
    	for(int i = 0; i < 4; i++) {
    		if(suit[i].equals(this.type)) {
    			thisType = i;
    		}
    		else if(suit[i].equals(c.type)) {
    			otherType = i;
    		}
    	}
    	if(thisType <= 1 && otherType <= 1) {
    		return true;
    	}
    	if(thisType >= 2 && otherType >= 2) {
    		return true;
    	}
    	return false;
    }

    // Implement compareTo to compare cards by rank
    @Override
    public int compare(Card card) {
        // Define rank order: A -> 2 -> 3 -> ... -> K
        return compareTo((CardPlayingCard)card);
    }

	@Override
	public int compareTo(CardPlayingCard o) {
		// Define rank order: A -> 2 -> 3 -> ... -> K
        String[] rankOrder = {"3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A", "2"};
        int thisRankIndex = -1;
        int otherRankIndex = -1;
        
        for (int i = 0; i < rankOrder.length; i++) {
            if (rankOrder[i].equals(this.rank)) {
                thisRankIndex = i;
            }
            if (rankOrder[i].equals(o.rank)) {
                otherRankIndex = i;
            }
        }

        // Compare ranks (ascending order)
        if(thisRankIndex != otherRankIndex) {
        	return thisRankIndex - otherRankIndex;
        }
        int thisSuit = -1;
        int otherSuit = -1;
        String[] suitOrder = {"S", "C", "D", "H"};
        for(int i = 0; i < suitOrder.length; i++) {
        	if(suitOrder[i].equals(this.type)) {
        		thisSuit = i;
        	}
        	if(suitOrder[i].equals(o.type)) {
        		otherSuit = i;
        	}
        }
        return thisSuit - otherSuit;
    }

	@Override
	public String getRank() {
		// TODO Auto-generated method stub
		return rank;
	}
}