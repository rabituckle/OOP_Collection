package utilities.card;

import java.awt.Color;

public class CardBaCay extends Card implements Comparable<CardBaCay> {
    private static String[] suit = {"S", "C", "D", "H"}; // ♠, ♣, ♦, ♥
    private static String[] val = {"3", "4", "5", "6", "7", "8", "9", "A", "2"};
    private String type, rank;
    private int cost;
    private String value;
    private Color color;

    public CardBaCay(String suit, String rank) {
        this.type = suit;
        this.rank = rank;
        if(suit.equals("S") || suit.equals("C")) {
        	color = Color.BLACK;
        }
        else {
        	color = Color.RED;
        }
        if(rank.equals("A")) {
        	this.cost = 1;
        }
        else {
        	this.cost = Integer.parseInt(rank);
        }
        value = rank + suit;
    }

    // Override toString to show value and suit correctly
    @Override
    public String toString() {
        return rank + type;
    }
    
    public int getCost() {
    	return cost;
    }
    
    @Override
    public String getValue() {
    	return value;
    }

    // Implement compareTo to compare cards by rank
    @Override
    public int compare(Card card) {
        // Define rank order: A -> 2 -> 3 -> ... -> K
        return compareTo((CardBaCay)card);
    }

	@Override
	public int compareTo(CardBaCay o) {
		int thisSuit = -1;
        int otherSuit = -1;
        String[] suitOrder = {"S", "C", "H", "D"};
        for(int i = 0; i < suitOrder.length; i++) {
        	if(suitOrder[i].equals(this.type)) {
        		thisSuit = i;
        	}
        	if(suitOrder[i].equals(o.type)) {
        		otherSuit = i;
        	}
        }
        if(thisSuit != otherSuit) {
        	return thisSuit - otherSuit;
        }
		// Define rank order: A -> 2 -> 3 -> ... -> K
        String[] rankOrder = {"2", "3", "4", "5", "6", "7", "8", "9", "A"};
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
        return thisRankIndex - otherRankIndex;
    }

	@Override
	public String getRank() {
		// TODO Auto-generated method stub
		return rank;
	}

	@Override
	public String getSuit() {
		// TODO Auto-generated method stub
		return type;
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return color;
	}
}