package group17;

import java.util.ArrayList;
import java.util.Random;

public class Card implements Comparable<Card> {
    private String[] suit = {"S", "C", "D", "H"}; // ♠, ♣, ♦, ♥
    private String[] val = {"3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A", "2"};
    private String type, rank;
    private int cost;
    private String value;

    public Card() {
        // default constructor
    }

    public Card(String suit, String rank) {
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

    public String getValue() {
        return value;
    }
    
    public int getCost() {
    	return cost;
    }
    
    public String getSuit() {
    	return type;
    }
    
    public boolean checkColorSuit(Card c) {
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

    // Create deck of 52 cards
    public ArrayList<Card> createDeck() {
        ArrayList<Card> deck = new ArrayList<Card>();
        for (int i = 0; i < val.length; i++) {
            for (int j = 0; j < suit.length; j++) {
                Card card = new Card(suit[j], val[i]);
                deck.add(card);
            }
        }
        return deck;
    }

    // Shuffle the deck randomly
    public ArrayList<Card> shuffleDeck(ArrayList<Card> deck) {
        Random random = new Random();
        for (int i = 0; i < 2 * deck.size(); i++) {
            int j = random.nextInt(deck.size());
            Card currCard = deck.get(i % deck.size());
            Card randomCard = deck.get(j);
            deck.set(i % deck.size(), randomCard);
            deck.set(j, currCard);
        }
        return deck;
    }

    // Distribute cards to players
    public ArrayList<Card>[] getPlayerCard(ArrayList<Card> deck, int players, int maxCards) {
        ArrayList<Card>[] playerCard = new ArrayList[players];
        for (int i = 0; i < players; i++) {
            playerCard[i] = new ArrayList<Card>();
        }
        int idx = 0;
        for (int i = 0; i < maxCards; i++) {
            for (int j = 0; j < players; j++) {
                playerCard[j].add(deck.get(idx));
                idx++;
            }
        }
        for (int i = 0; i < players; i++) {
            playerCard[i].sort(null);  // Sorts based on compareTo method
        }
        return playerCard;
    }

    // Implement compareTo to compare cards by rank
    @Override
    public int compareTo(Card other) {
        // Define rank order: A -> 2 -> 3 -> ... -> K
        String[] rankOrder = {"3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A", "2"};
        int thisRankIndex = -1;
        int otherRankIndex = -1;
        
        for (int i = 0; i < rankOrder.length; i++) {
            if (rankOrder[i].equals(this.rank)) {
                thisRankIndex = i;
            }
            if (rankOrder[i].equals(other.rank)) {
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
        	if(suitOrder[i].equals(other.type)) {
        		otherSuit = i;
        	}
        }
        return thisSuit - otherSuit;
    }

    public static void main(String[] args) {
        Card card = new Card();
        ArrayList<Card> cards = card.shuffleDeck(card.createDeck());
        ArrayList<Card>[] player = card.getPlayerCard(cards, 4, 13);
        
        // Sort each player's hand
        for (int i = 0; i < 4; i++) {
            player[i].sort(null);  // Sorts based on compareTo method
            System.out.println("Player " + (i + 1) + ": " + player[i].get(0).cost);
            System.out.println("Player " + (i + 1) + ": " + player[i]);
        }
    }
}