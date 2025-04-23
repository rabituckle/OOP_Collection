package utilities;

import java.util.ArrayList;
import java.util.Random;

import utilities.player.Player;

public class Deck {
	
	private static String[] suit = {"S", "C", "D", "H"}; // ♠, ♣, ♦, ♥
    private static String[] val = {"3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A", "2"};
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
    public ArrayList<Player> dealer(ArrayList<Card> deck, int players, int maxCards) {
        ArrayList<Player> player = new ArrayList<Player>();
        int idx = 0;
        for (int j = 0; j < players; j++) {
            player.add(new Player());
        }
        for (int i = 0; i < maxCards; i++) {
            for (int j = 0; j < players; j++) {
                player.get(i).add(deck.get(idx));
                idx++;
            }
        }
        for (int i = 0; i < players; i++) {
            player.get(i).getCards().sort(null);  // Sorts based on compareTo method
        }
        return player;
    }
}
